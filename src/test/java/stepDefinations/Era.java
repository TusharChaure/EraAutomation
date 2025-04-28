package stepDefinations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import testComponents.BaseTest;

public class Era extends BaseTest {
	
	@Before
	public void beforeStepImp(Scenario scenario) {
		BaseTest.scenario = scenario;
	}
	
	@Given("Open era homepage and accept terms and conditions")
	public void openEraHomePage() throws IOException {
		launchApplication();
	}
	
	@When("Enter {string} {string} and click on submit button")
	public void eneterUsernamePasswordAndClickOnSubmitButton(String username, String password) throws IOException {
		landingPage.acceptTermsAndConditions();
		landingPage.performLoginOperation(username, password);
	}
	
	@Then("Verify title of ERA homepage")
	public void verifyTitleOfERAHomepage() throws IOException, InterruptedException {
		Assert.assertEquals(homePage.getTitleOfCurrentPage(), "Home", "You are not on home screen");
	}
	
	@Given("^Search journey from (.+) (.+) for (.+)$")
	public void selectJourneyForParticularCarrier(String origin, String departure, String carrier) throws InterruptedException {
		List<String> stations = null;
		if(carrier.contentEquals("eurostar"))
			stations = Arrays.asList("London(All stations), UK", "Paris(All stations), France");
		else if(carrier.contentEquals("obb"))
			stations = Arrays.asList("Wien Hbf, Austria", "Munich, Germany");
		else if(carrier.contentEquals("dbahn_intercity_express"))
			stations = Arrays.asList("Berlin, Germany", "Munich, Germany");
		else if(carrier.contentEquals("rdg_avanti_west_coast"))
			stations = Arrays.asList("Edinburgh(Waverley, city centre), UK", "London(All stations), UK");
		else if(carrier.contentEquals("sncb"))
			stations = Arrays.asList("Amsterdam, Netherlands", "Rotterdam, Netherlands");
		else if(carrier.contentEquals("renfe"))
			stations = Arrays.asList("Barcelona(All stations), Spain", "Madrid(All stations), Spain");
		else if(carrier.contentEquals("sbb_regioexpress"))
			stations = Arrays.asList("Zermatt, Switzerland", "Chur, Switzerland");
		else if(carrier.contentEquals("ter"))
			stations = Arrays.asList("Nantes, France", "Clisson, France");
		else if(carrier.contentEquals("regiojet"))
			stations = Arrays.asList("Wien Hbf, Austria", "Gyor, Hungary");
		else if(carrier.contentEquals("italo"))
			stations = Arrays.asList("Rome, Italy", "Milan, Italy");
		homePage.enterAndSelectLocation(origin, departure, stations);
		homePage.selectDepartureDateAndTravelerNumbers("ptp");
		homePage.searchPTPJouney();
		homePage.selectPtPJourney(carrier);
		cart.addAnotherProducts();
	}
	
	@Given("^Search (.+), (.+) pass journey$")
	public void searchPassJourney(String pass, String passName) throws InterruptedException {
		homePage.selectRespectivePass(pass);
		homePage.selectDepartureDateAndTravelerNumbers("pass");
		homePage.searchPassJouney();
		homePage.selectPass(passName);
		if(!pass.contentEquals("CH"))
			cart.addAnotherProducts();
	}
	
	@Given("Click on proceed to add travelers details button")
	public void clickOnProceedToAddTravelersDetailsButton() throws InterruptedException {
		cart.proceedToAddTravelersDetailsPage();
	}
	
	@When("Enter traveler details as {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
	public void addTravelesDetailsInfoAndProceedToBillingPage(String title, String firstName, String lastName, String dob, String email, String phoneNumber, String identityDocNumber, String docExpirationDate) throws InterruptedException {
		addTravelerDetails.addTravelerInfo(title, firstName, lastName, dob, email, phoneNumber, identityDocNumber, docExpirationDate);
	}
	
	@And("Enter card holder and agency details as {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
	public void enterCardHolder(String title, String firstName, String lastName, String email, String phoneNumber, String firstAddress, String secondAddress, String zipCode, String city, String state){
		billingInfoDetails.fillBilingInfoAndClickOnContinueToPay(firstName, lastName, email, phoneNumber, firstAddress, secondAddress, zipCode, city, state);
	}
	
	@Then("Collect generated booking reference number")
	public void collectGeneratedBookingReferenceNumber() {
		System.out.println("Succesfully recahed till payment Page.");
		System.out.println("Booking Reference Number: " + paymentPage.getBookingRefernceNumber());
	}
	
}
