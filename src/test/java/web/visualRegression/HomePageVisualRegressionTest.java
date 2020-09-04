package web.visualRegression;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.web.HomePage;
import util.UrlFactory;
import util.WebSpecPaths;

public class HomePageVisualRegressionTest extends AbstractVisualRegressionTest {

    private HomePage homePage;

    @BeforeEach
    public void setUp() {
        homePage = new HomePage(browser);
    }

    @Test
    public void testHomePageHeader() throws Exception {
        browser.getDriver().get(UrlFactory.BASE_URL.pageUrl);
        checkLayoutDesign(WebSpecPaths.HomePage.HEADER, null);
    }
}
