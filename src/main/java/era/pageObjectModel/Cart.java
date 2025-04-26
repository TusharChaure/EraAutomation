package era.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import era.abstractComponents.AbstractComponent;

public class Cart extends AbstractComponent{

	WebDriver driver;

	public Cart(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//a[@data-select='continue-shopping']")
	WebElement addNewProductButton;
	@FindBy(css=".era-cart-checkout")
	WebElement continueToTravelerDetailsButton;
	@FindBy(tagName="footer")
	WebElement footerElement;
	
	public void addAnotherProducts() throws InterruptedException {
		Thread.sleep(1000);
		scrollUptoElement(footerElement);
		elementToBeClickable(addNewProductButton);
	}
	
	public void proceedToAddTravelersDetailsPage() throws InterruptedException {
		Thread.sleep(1000);
		scrollUptoElement(footerElement);
		elementToBeClickable(continueToTravelerDetailsButton);
	}
	
}
