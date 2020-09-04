package pages.web;

import browser.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageObject;

import java.util.List;

public class PopularBetsPage extends PageObject {

    public PopularBetsPage(Browser browser) {
        super(browser.getDriver());
    }

    @FindBy(xpath = "//div[@id = 'webSelectTab']//a[@href = '#football']")
    private WebElement footballTab;

    @FindBy(css = ".code")
    private List<WebElement> popularBetsCodes;

    @FindBy(css = ".score")
    private List<WebElement> popularBetsPlayedCount;

    // click methods
    public void clickFootballTab() throws Exception {
        browser.click(footballTab);
    }

    // get text methods

    public String getTextPopularBetsCode(int index) {
        return popularBetsCodes.get(index).getText();
    }

    public String getTextPopularBetsPlayedCount(int index) {
        return popularBetsPlayedCount.get(index).getText();
    }
}
