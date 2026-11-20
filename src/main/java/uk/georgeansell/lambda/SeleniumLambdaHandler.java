package uk.georgeansell.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import uk.georgeansell.lambda.runners.LambdaSmokeTestRunner;
import uk.georgeansell.lambda.runners.LambdaTestRunner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SeleniumLambdaHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
        long startedAt = System.currentTimeMillis();

        configureFromEnvironment(event);

        String suite = readString(event, "suite", "smoke");
        Class<?> runnerClass = "full".equalsIgnoreCase(suite) ? LambdaTestRunner.class : LambdaSmokeTestRunner.class;

        Result result = JUnitCore.runClasses(runnerClass);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("suite", suite.toLowerCase());
        response.put("runner", runnerClass.getName());
        response.put("successful", result.wasSuccessful());
        response.put("runCount", result.getRunCount());
        response.put("failureCount", result.getFailureCount());
        response.put("ignoreCount", result.getIgnoreCount());
        response.put("runTimeMillis", result.getRunTime());
        response.put("durationMillis", System.currentTimeMillis() - startedAt);

        if (!result.wasSuccessful()) {
            List<Map<String, String>> failures = new ArrayList<>();
            for (Failure failure : result.getFailures()) {
                Map<String, String> failureData = new LinkedHashMap<>();
                failureData.put("testHeader", failure.getTestHeader());
                failureData.put("message", failure.getMessage() == null ? "" : failure.getMessage());
                String trace = failure.getTrace();
                failureData.put("trace", truncate(trace, 6000));
                failures.add(failureData);
            }
            response.put("failures", failures);
        }

        response.put("reportPath", "/tmp/serenity");
        response.put("cucumberJson", "/tmp/cucumber.json");
        return response;
    }

    private static void configureFromEnvironment(Map<String, Object> event) {
        applyProperty("webdriver.base.url", event, "baseUrl", "WEBDRIVER_BASE_URL");
        applyProperty("api.base.url", event, "apiBaseUrl", "API_BASE_URL");
        applyProperty("test.user.email", event, "testUserEmail", "TEST_USER_EMAIL");
        applyProperty("test.user.password", event, "testUserPassword", "TEST_USER_PASSWORD");

        String remoteWebDriverUrl = firstNonBlank(
                readString(event, "remoteWebDriverUrl", null),
                System.getenv("WEBDRIVER_REMOTE_URL")
        );
        if (remoteWebDriverUrl != null) {
            System.setProperty("webdriver.remote.url", remoteWebDriverUrl);
            System.setProperty("webdriver.driver", "remote");
        }

        System.setProperty("serenity.outputDirectory", "/tmp/serenity");
    }

    private static void applyProperty(String systemProperty, Map<String, Object> event, String eventKey, String envKey) {
        String value = firstNonBlank(readString(event, eventKey, null), System.getenv(envKey));
        if (value != null) {
            System.setProperty(systemProperty, value);
        }
    }

    private static String readString(Map<String, Object> event, String key, String fallback) {
        if (event == null || !event.containsKey(key) || event.get(key) == null) {
            return fallback;
        }
        return String.valueOf(event.get(key));
    }

    private static String firstNonBlank(String... candidates) {
        if (candidates == null) {
            return null;
        }
        for (String candidate : candidates) {
            if (candidate != null && !candidate.isBlank()) {
                return candidate;
            }
        }
        return null;
    }

    private static String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength) + "...";
    }
}
