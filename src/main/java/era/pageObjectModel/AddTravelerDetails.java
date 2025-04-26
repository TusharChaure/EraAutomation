package era.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import era.abstractComponents.AbstractComponent;

public class AddTravelerDetails extends AbstractComponent {

	WebDriver driver;
	
	public AddTravelerDetails(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//section[@class='era-page-section']//div[@class='era-section-content']/era-sub-section")
	List<WebElement> addTrvelerInfoList;
	By openParticularTravelerInfoCardBy = By.xpath("//button[@icon='chevron-down']");
	@FindBy(xpath="//button[@icon='chevron-down']")
	WebElement openParticularTravelerInfoCard;
	By titleButton = By.xpath("//button[@aria-label='Title']");
	By mrTitleButton = By.xpath("//button[@value='MR']");
	By firstNameField = By.xpath("//input[@aria-label='First Name']");
	By lastNameField = By.xpath("//input[@aria-label='Last Name']");
	By dobField = By.xpath("//input[@aria-label='Date of birth (YYYY-MM-DD)']");
	By countryOfResidenceField = By.xpath("//button[@aria-label='Country of residence']");
	By afCountryOfResidenceOption = By.xpath("//button[@value='AF']");
	By emailField = By.xpath("//input[@aria-label='Email']");
	By phoneNumberField = By.xpath("//input[@aria-label='Phone number']");
	By documentationInfoSection = By.className("era-travelerDetailsForm-documentTravelerInfo");
	By identityDocumentNumber = By.xpath("//input[@aria-label='Identity document number']");
	By documentExpirationDate = By.xpath("//input[@aria-label='Expiration date (YYYY-MM-DD)']");
	By countryCodeButton = By.xpath("//button[@aria-label='Country code']");
	By afCountryButton = By.xpath("//button[@id='AFG-options']");
	By confirmButton = By.xpath("//button[@data-select='travelers-details-form-confirm-button']");
	@FindBy(xpath="//button[@data-select='travelers-details-form-next-button']")
	WebElement continueToHoldAndPaymentButton;
	@FindBy(xpath="//div[@class='era-travelerDetailsForm-footer']")
	WebElement travelerInfoCardFooter;
	@FindBy(xpath="//div[@class='era-travelerDetailsFormTraveler-basicFields']//div")
	List<WebElement> listOfInputElementsFirstRow;
	@FindBy(xpath="//div[@class='era-travelerDetailsForm-traveler']//p")
	List<WebElement> listOfParaElement;
	@FindBy(xpath="//div[@class='era-travelerDetailsForm-leadTraveler']")
	WebElement leadTravelerSection;
	
	public void addTravelerInfo(String title, String firstName, String lastName, String dob, String email, String phoneNumber, String identityDocNumber, String docExpirationDate) throws InterruptedException {
		for(WebElement traveler: addTrvelerInfoList) {
			Thread.sleep(1500);
			scrollUptoElement(traveler);
			scrollUptoElement(travelerInfoCardFooter);
			traveler.findElement(titleButton).click();
			traveler.findElement(mrTitleButton).click();
			traveler.findElement(firstNameField).sendKeys(firstName);
			leadTravelerSection.click();
			traveler.findElement(lastNameField).sendKeys(lastName);
			if(listOfInputElementsFirstRow.size()>3)
				traveler.findElement(dobField).sendKeys(dob);
			if(listOfInputElementsFirstRow.size()>4) {
				traveler.findElement(countryOfResidenceField).click();
				traveler.findElement(afCountryOfResidenceOption).click();
			}
			traveler.findElement(emailField).sendKeys(email);;
			traveler.findElement(phoneNumberField).sendKeys(phoneNumber);
			if(listOfParaElement.size()>1) {
				traveler.findElement(identityDocumentNumber).sendKeys(identityDocNumber);
				traveler.findElement(documentExpirationDate).sendKeys(docExpirationDate);
				traveler.findElement(countryCodeButton).click();
				traveler.findElement(afCountryButton).click();
			}
			traveler.findElement(confirmButton).click();
			Thread.sleep(6000);
		}
		scrollUptoElement(continueToHoldAndPaymentButton);
		continueToHoldAndPaymentButton.click();
	}
	
}
