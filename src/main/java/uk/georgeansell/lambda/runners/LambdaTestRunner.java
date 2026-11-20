package uk.georgeansell.lambda.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "uk.georgeansell.tests.steps",
        plugin = {"pretty", "html:/tmp/cucumber-reports.html", "json:/tmp/cucumber.json"},
        tags = "not @wip and not @false-positive"
)
public class LambdaTestRunner {
}
