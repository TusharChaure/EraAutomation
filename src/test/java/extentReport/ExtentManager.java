package extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    public static ExtentReports createInstance(String fileName) {
    	String reportPath = System.getProperty("user.dir") + "/reports/extent.html";
    	ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);;
    	reporter.config().setDocumentTitle("Dashboard Automation");
    	reporter.config().setReportName("JCMS API Test Report");
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        return extent;
    }
}
