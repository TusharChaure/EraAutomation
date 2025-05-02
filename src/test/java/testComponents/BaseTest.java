package testComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import era.pageObjectModel.AddTravelerDetails;
import era.pageObjectModel.BillingInfoDetails;
import era.pageObjectModel.Cart;
import era.pageObjectModel.HomePage;
import era.pageObjectModel.LandingPage;
import era.pageObjectModel.PaymentPage;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public static WebDriver driver;
	public static LandingPage landingPage;
	public static HomePage homePage;
	public static Cart cart;
	public static AddTravelerDetails addTravelerDetails;
	public static BillingInfoDetails billingInfoDetails;
	public static PaymentPage paymentPage;
	public static Scenario scenario;
	
	public WebDriver initializeDriver() throws IOException

	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");
		String mode = System.getProperty("mode");
		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if(mode.contentEquals("headless"))
				options.addArguments("headless");
			driver = new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "firefox.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "edge.exe");
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		String jsonContent = 	FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);	
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
      });
	  return data;
	}
	
	public String getScreenshot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";	
	}
	
	@BeforeMethod(alwaysRun=true)
	public void launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		homePage = new HomePage(driver);
		cart = new Cart(driver);
		addTravelerDetails = new AddTravelerDetails(driver);
		billingInfoDetails = new BillingInfoDetails(driver);
		paymentPage = new PaymentPage(driver);
		landingPage.goTo();
	}
	
	public static String takeScreenshot(String scenarioName) {
	    try {
	        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
	        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");
	        String filename = safeName + "_" + timestamp + ".png";
	        String screenshotDir = "screenshots";
	        File dest = new File(screenshotDir + File.separator + filename);
	        dest.getParentFile().mkdirs();
	        java.nio.file.Files.copy(src.toPath(), dest.toPath());
	        return System.getProperty("user.dir") + File.separator  + screenshotDir + File.separator + filename;	        
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	
	@AfterMethod(alwaysRun=true)	
	public void tearDown()
	{
		driver.close();
	}

}
