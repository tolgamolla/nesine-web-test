package pages.web;

import browser.Browser;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.PageObject;
import pages.web.myaccount.MyBetsPage;

public class HomePage extends PageObject {

    public HomePage(Browser browser) {
        super(browser.getDriver());
    }

    @FindBy(xpath = "//a[@tracking-label = 'Header-Giris']")
    private WebElement loginBtn;

    @FindBy(xpath = "//a[@tracking-label = 'Header-GüvenliCikis']")
    private WebElement logOutBtn;

    @FindBy(xpath = "//a[@tracking-label = 'Header-Hesabım-Kuponlarim']")
    private WebElement myBets;

    @FindBy(xpath = "//a[text() = 'Hesabım ']")
    private WebElement myAccount;

    @FindBy(css = "#txtUsername")
    private WebElement txtUsername;

    @FindBy(css = "#realpass")
    private WebElement txtPassword;

    @FindBy(css = "li.popular-bets")
    private WebElement popularBets;

    @FindBy(css = ".btn-register")
    private WebElement registerBtn;

    // -- click methods

    public PopularBetsPage clickPopularBets() throws Exception {
        browser.click(popularBets);
        return new PopularBetsPage(browser);
    }

    public void clickLoginBtn() throws Exception {
        browser.click(loginBtn);
    }

    public void clickLogOutBtn() throws Exception {
        browser.click(logOutBtn);
        browser.sleep(1);
    }

    public MyBetsPage clickMyBets() throws Exception {
        browser.click(myBets);
        return new MyBetsPage(browser);
    }

    public HomePage moveToMyAccount() {
        browser.moveToElement(myAccount);
        return this;
    }

    public boolean isDisplayedRegisterBtn() {
        return registerBtn.isDisplayed();
    }

    public HomePage sendKeysTxtUsername(String username) throws Exception {
        browser.sendKeys(txtUsername, username);
        return this;
    }

    public HomePage sendKeysTxtPassword(String password) throws Exception {
        browser.sendKeys(txtPassword, password);
        browser.sleep(1);
        return this;
    }
}
