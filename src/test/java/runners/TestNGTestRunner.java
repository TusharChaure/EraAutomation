package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features="src/test/resources/featureFiles",
		glue="stepDefinations",
		monochrome=true, 
		plugin= {"extentReport.ExtentCucumberAdapter", "html:target/cucumber.html"}
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

	
}
