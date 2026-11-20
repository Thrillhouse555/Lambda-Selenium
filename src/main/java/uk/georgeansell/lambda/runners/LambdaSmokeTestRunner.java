package uk.georgeansell.lambda.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "uk.georgeansell.tests.steps",
        plugin = {"pretty", "html:/tmp/cucumber-reports.html", "json:/tmp/cucumber.json"},
        tags = "@smoke and not @false-positive and not @wip"
)
public class LambdaSmokeTestRunner {
}
