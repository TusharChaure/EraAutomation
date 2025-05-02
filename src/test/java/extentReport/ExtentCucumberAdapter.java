package extentReport;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.WriteEvent;
import testComponents.BaseTest;

public class ExtentCucumberAdapter<MediaModelProvider> extends BaseTest implements ConcurrentEventListener {

	private ExtentReports extent;
	ExtentTest extentTest;
	String filePath, scenarioDescription, error, str = "";
	WriteEvent we;
	private StringBuilder logs;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    Media mediaModel;

	@Override
	public void setEventPublisher(EventPublisher eventPublisher) {
		extent = ExtentManager.createInstance("sanity.html");
		eventPublisher.registerHandlerFor(WriteEvent.class, this::writeLogs);
		eventPublisher.registerHandlerFor(TestCaseFinished.class, event -> {
			try {
				handleTestCaseFinished(event);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | MalformedURLException e) {
				e.printStackTrace();
			}	
		});
	}

	private void writeLogs(WriteEvent we) {
		str = we.getText();
	}
	
	@SuppressWarnings("deprecation")
	private void testLogs(TestCaseFinished event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = BaseTest.scenario.getClass().getDeclaredField("delegate");
		f.setAccessible(true);
		io.cucumber.core.backend.TestCaseState sc = (io.cucumber.core.backend.TestCaseState) f.get(BaseTest.scenario);
		Field f1 = sc.getClass().getDeclaredField("testCase");
		f1.setAccessible(true);
		io.cucumber.plugin.event.TestCase testCase = (io.cucumber.plugin.event.TestCase) f1.get(sc);
		List<PickleStepTestStep> testSteps = testCase.getTestSteps().stream()
                .filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x)
                .collect(Collectors.toList());
		logs.append(we.getTestCase().getTags());
		logs.append("<br>");
		logs.append(we.getTestCase().getKeyword() + ": " + event.getTestCase().getName() + "<br>");
		for(int i=0;i<testSteps.size();i++) {
			logs.append(testSteps.get(i).getStepText() + "<br>");
			
		}
		logs.append("<br>" + "<br>" + "</b>");
	}
	
	private void testPassedOrSkipped() {
		extentTest.pass(scenarioDescription, mediaModel);
	}
	
	private void testFailed(TestCaseFinished event) {
		error = "<b>Error: </b><br><br>" + event.getResult().getError() + "<br>";
		extentTest.fail(error);
		extentTest.fail(scenarioDescription, mediaModel);
	}

	@SuppressWarnings({ "incomplete-switch" })
	private void handleTestCaseFinished(TestCaseFinished event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, MalformedURLException {
		we = new WriteEvent(event.getInstant(), event.getTestCase(), str);
		String[] stringArray = event.getTestCase().getUri().toString().split("/");
		extentTest = extent.createTest(event.getTestCase().getName() + scenario.getName());
		extentTest.assignCategory(Arrays.asList(stringArray).get(stringArray.length-1) + scenario.getName());
		logs = new StringBuilder();
		testLogs(event);
		scenarioDescription = "<br><b>Scenario Description: </b><br>" + logs.toString() + "<br><br>";
	    String screenshotPath = takeScreenshot(event.getTestCase().getName());
	    System.out.println(screenshotPath);
	    extentTest.addScreenCaptureFromPath(screenshotPath);
	    switch (event.getResult().getStatus()) {
		case PASSED:
			testPassedOrSkipped();
			break;
		case FAILED:
			testFailed(event);
			break;
		case SKIPPED:
			testPassedOrSkipped();
			break;
		}
		extent.flush();
	}

}
