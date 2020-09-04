package pages.web.myaccount;

import browser.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageObject;

import java.util.List;

public class MyBetsPage extends PageObject {

    public MyBetsPage(Browser browser)
    {
        super(browser.getDriver());
    }

    @FindBy(css = "#page-nav > nav > ul > li")
    private List<WebElement> myBetsList;

    // methods

    public int getSizeMyBetsList()
    {
        return myBetsList.size();
    }
}
