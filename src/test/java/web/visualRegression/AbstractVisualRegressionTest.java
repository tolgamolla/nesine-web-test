package web.visualRegression;

import base.BaseClass;
import com.galenframework.api.Galen;
import com.galenframework.config.GalenConfig;
import com.galenframework.config.GalenProperty;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractVisualRegressionTest extends BaseClass {

    private static final String SPEC_FOLDER = "specs/";

    private final static Logger logger = LoggerFactory.getLogger(BaseClass.class);

    public void checkLayoutDesign(String specFile, List<String> includedTags) throws IOException {
        GalenConfig.getConfig().setProperty(GalenProperty.SCREENSHOT_FULLPAGE, "true");

        specFile = SPEC_FOLDER + specFile;

        String reportFilePath = defineTargetGalenReportDirectory();

        LayoutReport layoutReport = Galen.checkLayout(driver, specFile, includedTags);

        List<GalenTestInfo> tests = new LinkedList<>();

        GalenTestInfo test = GalenTestInfo.fromString(String.valueOf(Thread.currentThread().hashCode()));

        test.getReport().layout(layoutReport, "check layout on desktop");
        tests.add(test);

        new HtmlReportBuilder().build(tests, reportFilePath);

        if (layoutReport.errors() > 0) {
            fail("Incorrect layout: " + specFile);
        }
    }

    private String defineTargetGalenReportDirectory() {
        AtomicReference<File> dir = new AtomicReference<>();

        dir.set(new File(System.getProperty("user.home") + "/RunningLocalTest/1/GalenReports/" + Thread.currentThread().hashCode()));

        if (dir.get().exists()) {
            logger.info("A folder with name 'GalenReports' is already exist in the path ");
        } else {
            dir.get().mkdirs();
        }

        String reportFilePath = dir.toString() + System.getProperty("file.separator");
        logger.info("Galen report file : " + reportFilePath);
        return reportFilePath;
    }
}
