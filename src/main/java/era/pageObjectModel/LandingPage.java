package era.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import era.abstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="onetrust-accept-btn-handler")
	WebElement acceptButton;
	@FindBy(name="username")
	WebElement usernameField;
	@FindBy(name="password")
	WebElement passwordField;
	@FindBy(xpath="//button[@type='submit']")
	WebElement submitButton;
	
	public void goTo()
	{
		driver.get("https://era.raileurope.com");
		browserZoomLevel(0.8);
	}
	
	public void acceptTermsAndConditions() {
		waitForWebElementToAppear(acceptButton);
		acceptButton.click();
	}
	
	public void performLoginOperation(String username, String password) {
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		submitButton.click();
	}
}
