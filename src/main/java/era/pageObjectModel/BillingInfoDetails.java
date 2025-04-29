package era.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import era.abstractComponents.AbstractComponent;

public class BillingInfoDetails extends AbstractComponent{

	WebDriver driver;

	public BillingInfoDetails(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//span[@class='era-pricesSummaryFeePrice-label']")
	WebElement wavingFeesField;
	@FindBy(xpath="//button[@name='title']")
	WebElement title;
	@FindBy(xpath="//button[@value='MR']")
	WebElement mrTitle;
	@FindBy(xpath="//input[@name='firstName']")
	WebElement firstNameField;
	@FindBy(xpath="//input[@name='lastName']")
	WebElement lastNameField;
	@FindBy(xpath="//input[@name='emailAddress']")
	WebElement emailField;
	@FindBy(xpath="//input[@name='phoneNumber']")
	WebElement phoneNumberField;
	@FindBy(xpath="//input[@name='address1']")
	WebElement address1Field;
	@FindBy(xpath="//input[@name='address2']")
	WebElement address2Field;
	@FindBy(xpath="//button[@name='countryCode']")
	WebElement countryCodeButton;
	@FindBy(xpath="//button[@value='AF']")
	WebElement countryoption;
	@FindBy(xpath="//input[@name='zipCode']")
	WebElement zipCodeField;
	@FindBy(xpath="//input[@name='city']")
	WebElement cityField;
	@FindBy(xpath="//input[@name='state']")
	WebElement stateField;
	@FindBy(tagName="footer")
	WebElement footerField;
	@FindBy(xpath="//button[@type='submit']")
	WebElement continueToPaymentButton;
	
	public void fillBilingInfoAndClickOnContinueToPay(String firstName, String lastName, String email, String phoneNumber, String firstAddress, String secondAddress, String zipCode, String city, String state) {
		fillCardHolderDetailsInfo(firstName, lastName, email, phoneNumber);
		fillAgenecyDetailsInfo(firstAddress, secondAddress, zipCode, city, state);
		scrollUptoElement(footerField);
		continueToPaymentButton.click();
	}
	
	public void fillCardHolderDetailsInfo(String firstName, String lastName, String email, String phoneNumber) {
		scrollUptoElement(wavingFeesField);
		title.click();
		mrTitle.click();
		firstNameField.sendKeys(firstName);
		wavingFeesField.click();
		lastNameField.sendKeys(lastName);
		emailField.sendKeys(email);
		phoneNumberField.sendKeys(phoneNumber);
	}
	
	public void fillAgenecyDetailsInfo(String firstAddress, String secondAddress, String zipCode, String city, String state) {
		scrollUptoElement(address1Field);
		address1Field.sendKeys(firstAddress);
		address2Field.sendKeys(secondAddress);
		countryCodeButton.click();
		countryoption.click();
		zipCodeField.sendKeys(zipCode);
		cityField.sendKeys(city);
		stateField.sendKeys(state);
	}
	
	
}
