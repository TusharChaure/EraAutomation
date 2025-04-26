package era.pageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import era.abstractComponents.AbstractComponent;

public class PaymentPage extends AbstractComponent{

	WebDriver driver;

	public PaymentPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//span[@data-select='header-item-reference']")
	WebElement bookingReferenceNumber;
	
	public String getBookingRefernceNumber() {
		return bookingReferenceNumber.getText();
	}
	
	
	
	
}
