package base;

import browser.Browser;
import driver.DriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BaseClass extends DriverFactory {

    protected WebDriver driver;
    protected Browser browser;

    private final static Logger logger = LoggerFactory.getLogger(BaseClass.class);

    @BeforeEach
    public void before() throws Exception {

        driver = createInstance();

        browser = new Browser(driver);

        logger.info("--- TEST STARTED ---");

        browser.driver.get("https://www.nesine.com/");
    }

    @AfterEach
    public void after() {
        logger.info("--- TEST FINISHED ---");
        driver.quit();
    }

    protected Properties initMavenProps() throws IOException {

        Properties mavenProps = new Properties();
        InputStream in = BaseClass.class.getResourceAsStream("/maven.properties");
        mavenProps.load(in);

        return mavenProps;
    }
}
