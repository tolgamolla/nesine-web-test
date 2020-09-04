package web.visualRegression;

import base.BaseClass;
import com.galenframework.api.Galen;
import com.galenframework.config.GalenConfig;
import com.galenframework.config.GalenProperty;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractVisualRegressionTest extends BaseClass {

    private static final String SPEC_FOLDER = "specs/";
    private static final String MAIN_PATH = "/home/jenkins/.jenkins/userContent/Report/";

    public void checkLayoutDesign(String specFile, List<String> includedTags) throws IOException
    {
        GalenConfig.getConfig().setProperty(GalenProperty.SCREENSHOT_FULLPAGE, "true");

        specFile = SPEC_FOLDER + specFile;

        String reportFilePath = defineTargetGalenReportDirectory();

        LayoutReport layoutReport = Galen.checkLayout(driver, specFile, includedTags);

        List<GalenTestInfo> tests = new LinkedList<>();

        GalenTestInfo test = GalenTestInfo.fromString(String.valueOf(Thread.currentThread().hashCode()));

        test.getReport().layout(layoutReport, "check layout on desktop");
        tests.add(test);

        new HtmlReportBuilder().build(tests, reportFilePath);

        if (layoutReport.errors() > 0)
        {
            fail("Incorrect layout: " + specFile);
        }
    }

    private String defineTargetGalenReportDirectory()
    {
        AtomicReference<File> dir = new AtomicReference<>();

        Optional
                .ofNullable(System.getenv("WORKSPACE"))
                .ifPresentOrElse
                        (
                                (g) ->
                                {
                                    dir.set(new File(MAIN_PATH +
                                            System.getProperty("build.name") + "/" +
                                            System.getProperty("build.number") +
                                            "/GalenReports/" + Thread.currentThread().hashCode()));
                                },
                                () -> dir.set(new File(System.getProperty("user.home") + "/RunningLocalTest/1/GalenReports/" + Thread.currentThread().hashCode())));

        if (dir.get().exists())
        {
            //log.info("A folder with name 'GalenReports' is already exist in the path ");
        }
        else
        {
            dir.get().mkdirs();
        }

        String reportFilePath = dir.toString() + System.getProperty("file.separator");
        //log.info("Galen report file : " + reportFilePath);
        return reportFilePath;
    }
}
