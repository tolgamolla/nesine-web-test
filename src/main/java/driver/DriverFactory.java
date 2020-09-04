package driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {

    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    private static final String DOCKER = "docker";
    private static final String DOCKER_URL = "http://localhost:4444/wd/hub";

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);


    public WebDriver createInstance() throws MalformedURLException {
        if (CHROME.equals(System.getProperty("browser"))) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/chromedriver");
            logger.info(System.getProperty("user.dir"));
            return new ChromeDriver();

        } else if (FIREFOX.equals(System.getProperty("browser"))) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/resources/drivers/geckodriver");
            //System.setProperty("webdriver.firefox.bin", System.getProperty("user.dir") + "/src/main/resources/drivers/geckodriver");

            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            firefoxOptions.setBinary("/usr/bin/firefox");
            return new FirefoxDriver();

        } else if (DOCKER.equals(System.getProperty("browser"))) {
            Capabilities chromeCapabilities = DesiredCapabilities.chrome();
            RemoteWebDriver dockerChrome = new RemoteWebDriver(new URL(DOCKER_URL), chromeCapabilities);

            //Capabilities firefoxCapabilities = DesiredCapabilities.firefox();
            //RemoteWebDriver dockerFirefox = new RemoteWebDriver(new URL(DOCKER_URL), firefoxCapabilities);
            return dockerChrome;
        }

        throw new IllegalArgumentException("Browser does not support: ");
    }
}