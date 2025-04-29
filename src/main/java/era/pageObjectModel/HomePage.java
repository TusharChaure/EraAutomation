package era.pageObjectModel;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import era.abstractComponents.AbstractComponent;

public class HomePage extends AbstractComponent {

	WebDriver driver;
	String firstStationName, secondStationName;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//header[@data-select='era-header']")
	WebElement headerElement;
	@FindBy(xpath="//input[@name='from']") 
	WebElement originField;
	@FindBy(xpath="//input[@name='to']")
	WebElement destinationField;
	@FindBy(xpath="//button[@role='option']")
	List<WebElement> originList;
	@FindBy(css="button.era-dateSelector-toggleButton")
	WebElement ptpCalender;
	@FindBy(xpath="//button[@data-select='datetime-selector-dateButton-beginningDate']")
	WebElement passCalender;
	@FindBy(xpath="//button[@data-select=\"era-calendarHeader-nextMonth\"]")
	WebElement nextMonthIcon;
	@FindBy(xpath="//button[@data-select='era-calendar-days-2']")
	WebElement date;
	@FindBy(xpath="//era-point-to-point-search-form//button[@data-select='pax-selector-adults-more']")
	WebElement ptpTravelerNumber;
	@FindBy(xpath="//era-passes-search-form//button[@data-select='pax-selector-adults-more']")
	WebElement passTravelerNumber;
	@FindBy(xpath="//button[@data-select='pointToPoint-search-form-search-button']")
	WebElement searchPTPOfferButton;
	@FindBy(xpath="//era-point-to-point-results-list")
	WebElement searchPointToPointResults;
	@FindBy(xpath="//era-point-to-point-search-form//input[@data-select='pax-selector-adults-input']")
	WebElement ptpAdultTravelerNumber;
	@FindBy(xpath=".pointToPointSelectionStep-messages")
	WebElement missingSupplierMessage;
	@FindBy(xpath="//li[@data-select='era-tabs-header-li-Passes']")
	WebElement paasesLinkButton;
	@FindBy(xpath="//button[@data-select='passes-search-form-country']")
	WebElement selectDestinationField;
	@FindBy(className="era-formSelect-options")
	WebElement passOptionListCard;
	@FindBy(xpath="//button[@data-select='passes-search-form-search-button']")
	WebElement searchPassOfferButton;
	@FindBy(xpath="//section[@class='era-content era-offersSearch']//ul[@data-select='loaded-flag']")
	WebElement passResults;
	@FindBy(xpath="//era-passes-search-form//input[@data-select='pax-selector-adults-input']")
	WebElement passAdultTravelerNumber;
	By spinnerIcon = By.tagName("era-spinner");
	
	public void enterAndSelectLocation(String origin, String destination, List<String> completeStationName) {
		scrollUptoElement(headerElement);
		originField.click();
		originField.clear();
		originField.sendKeys(origin);
		filterStation(origin, completeStationName.get(0));
		destinationField.click();
		destinationField.clear();
		destinationField.sendKeys(destination);
		filterStation(destination, completeStationName.get(1));
	}
	
	public void selectRespectivePass(String pass) {
		scrollUptoElement(headerElement);
		paasesLinkButton.click();
		selectDestinationField.click();
		passOptionListCard.findElement(By.xpath("//button[@value='" + pass + "']")).click();
	}
	
	public void filterStation(String station, String compareStationName) {
		if(!originList.get(0).isDisplayed())
			waitForWebElementToAppear(originList.get(0));
		for(int i=1;i<=originList.size();i++) {
			firstStationName = driver.findElement(By.xpath("//button[@role='option']["+i+"]//span["+2+"]//span[@data-select='search-highlight'][1]")).getText();
			secondStationName = driver.findElement(By.xpath("//button[@role='option']["+i+"]//span["+2+"]//span[@data-select='search-highlight'][2]")).getText();
			System.out.println(firstStationName.concat(secondStationName));
			System.out.println(compareStationName);
			if(firstStationName.concat(secondStationName).contentEquals(compareStationName)) {
				driver.findElement(By.xpath("//button[@role='option']["+i+"]")).click();
				break;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void selectDepartureDateAndTravelerNumbers(String pass) {
		if(pass.contentEquals("pass")) {
			if(passAdultTravelerNumber.getAttribute("value").contentEquals("0")) 
				passTravelerNumber.click();
			passCalender.click();
		}
		else {
			if(ptpAdultTravelerNumber.getAttribute("value").contentEquals("0")) 
				ptpTravelerNumber.click();
			ptpCalender.click();
		}
		waitForWebElementToAppear(nextMonthIcon);
		nextMonthIcon.click();
		waitForWebElementToAppear(date);
		date.click();
	}
	
	public void searchPTPJouney() throws InterruptedException {
		Thread.sleep(500);
		scrollUptoElement(searchPTPOfferButton);
		searchPTPOfferButton.click();
		waitUntilInvisibilityOfElement(searchPTPOfferButton.findElement(spinnerIcon));
	}
	
	public void searchPassJouney() throws InterruptedException {
		Thread.sleep(500);
		scrollUptoElement(searchPassOfferButton);
		searchPassOfferButton.click();
		waitUntilInvisibilityOfElement(searchPassOfferButton.findElement(spinnerIcon));
	}
	
	@SuppressWarnings({ "deprecation" })
	public void selectPtPJourney(String carrier) throws InterruptedException {
		waitForWebElementToAppear(searchPointToPointResults);
		List<WebElement> resultList = searchPointToPointResults.findElements(By.xpath("//era-point-to-point-result-item"));
		for(int i=1;i<=resultList.size();i++) {
			List<WebElement> listOfInnerElements = new ArrayList<WebElement>(); 
			scrollUptoElement(searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//era-leg-solution-info")));
			if(searchPointToPointResults.findElements(By.xpath("//era-point-to-point-result-item["+i+"]//era-proposition-button/button//span[@class='proposition-flexibility']")).size()==1)
				listOfInnerElements.add(searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//era-proposition-button/button//span[@class='proposition-flexibility']")));
			else
				listOfInnerElements = searchPointToPointResults.findElements(By.xpath("//era-point-to-point-result-item["+i+"]//era-proposition-button/button//span[@class='proposition-flexibility']"));
			for(int j=0;j<listOfInnerElements.size();j++) {
				scrollUptoElement(searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//era-leg-solution-info")));
				if(listOfInnerElements.get(j).getText().contentEquals("Semi Flex") || listOfInnerElements.get(j).getText().contentEquals("Flexible")) {
					listOfInnerElements.get(j).click();		
					break;
				}
			}
			try {
				if(searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//img")).getAttribute("alt").contentEquals(carrier)) {
					scrollUptoElement(searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//img[@alt='"+carrier+"']//ancestor::section//era-result-footer//button")));
					Thread.sleep(2000);
					searchPointToPointResults.findElement(By.xpath("//era-point-to-point-result-item["+i+"]//img[@alt='"+carrier+"']//ancestor::section//era-result-footer//button")).click();
					break;
				}
			}
			catch(Exception e) {
				System.out.println();
			}
		}
	}
	
	public void selectPass(String passName) throws InterruptedException {
		List<WebElement> listOfPassResults = passResults.findElements(By.tagName("li"));
		for(int i=1;i<=listOfPassResults.size();i++) {
			scrollUptoElement(passResults.findElement(By.xpath("//li["+i+"]")));
			if(passResults.findElement(By.xpath("//li["+i+"]//h2")).getText().contentEquals(passName)) {
				scrollUptoElement(passResults.findElement(By.xpath("//li["+i+"]//div[@class='era-passesResult-prices--secondClass']/button")));
				passResults.findElement(By.xpath("//li["+i+"]//div[@class='era-passesResult-prices--secondClass']/button")).click();
				scrollUptoElement(passResults.findElement(By.xpath("//li["+i+"]//button[@class='era-passesResult-summaryAddToCart']")));
				passResults.findElement(By.xpath("//li["+i+"]//button[@class='era-passesResult-summaryAddToCart']")).click();
				break;
			}
		}
	}
	
	
	
}
