package uk.georgeansell.tests.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import uk.georgeansell.tests.pages.DatePage;

public class DatePageSteps {
    
    @Steps
    DatePage datePage;

    @Given("I am on the date page")
    public void i_am_on_the_date_page() {
        datePage.openPage();  
    }

    @Then("I should see the current day of the week is {string}")
    public void i_should_see_the_current_day_of_the_week_is(String day) {
        datePage.verifyCurrentDayOfWeek(day);
    }

    @Then("I should see the current period is {string}")
    public void i_should_see_the_current_period_is(String period) {
        datePage.verifyCurrentPeriod(period);  
    }
}
