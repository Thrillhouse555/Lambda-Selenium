package uk.georgeansell.tests.pages;

import net.serenitybdd.core.pages.PageObject;
import uk.georgeansell.tests.config.TestConfig;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.annotations.findby.FindBy;

public class DatePage extends PageObject {

    @FindBy(id = "day")
    private WebElementFacade dayElement;

    @FindBy(id = "period")
    private WebElementFacade periodElement;

    public void openPage() {
        getDriver().get(TestConfig.datePageUrl());
    }

    public void verifyCurrentDayOfWeek(String day) {
        dayElement.shouldContainText(day);
    }

    public void verifyCurrentPeriod(String period) {
        periodElement.shouldContainText(period);
    }
    
}
