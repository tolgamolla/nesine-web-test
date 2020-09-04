package pages;

import browser.Browser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PageObject {

    public Browser browser;

    protected PageObject(WebDriver driver) {
        browser = new Browser(driver);

        PageFactory.initElements(driver, this);
    }

}
