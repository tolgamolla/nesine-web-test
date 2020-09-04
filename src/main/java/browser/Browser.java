package browser;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Browser {

    public WebDriver driver;

    public Browser(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void get(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void sleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException ex) {
            // sleep not working
        }
    }

    public void click(WebElement element) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.click();
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
            element.click();
        } catch (final Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void sendKeys(WebElement element, String text) throws Exception {

        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);

        } catch (StaleElementReferenceException e) {
            element.clear();
            element.sendKeys(text);
        } catch (final Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void moveToElement(WebElement element) {
        Actions markerAction = new Actions(driver);
        markerAction.moveToElement(element);
        markerAction.build().perform();
    }
}
