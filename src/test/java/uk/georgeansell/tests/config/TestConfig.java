package uk.georgeansell.tests.config;

public final class TestConfig {

    private static final String DEFAULT_BASE_URL = "https://georgeansell.co.uk";
    private static final String DEFAULT_API_BASE_URL = DEFAULT_BASE_URL;

    private TestConfig() {
    }

    public static String baseUrl() {
        return getValue("webdriver.base.url", "WEBDRIVER_BASE_URL", DEFAULT_BASE_URL);
    }

    public static String apiBaseUrl() {
        return getValue("api.base.url", "API_BASE_URL", DEFAULT_API_BASE_URL);
    }

    public static String loginUrl() {
        return baseUrl() + "/login.html";
    }

    public static String contactUrl() {
        return baseUrl() + "/contact.html";
    }

    public static String datePageUrl() {
        return baseUrl() + "/day-date-time.html";
    }

    public static String testUserEmail() {
        return getValue("test.user.email", "TEST_USER_EMAIL", "");
    }

    public static String testUserPassword() {
        return getValue("test.user.password", "TEST_USER_PASSWORD", "");
    }

    private static String getValue(String systemProperty, String envVar, String defaultValue) {
        String value = System.getProperty(systemProperty);
        if (value == null || value.isBlank()) {
            value = System.getenv(envVar);
        }
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
