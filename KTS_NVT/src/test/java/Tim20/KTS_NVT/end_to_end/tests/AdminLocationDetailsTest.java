package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.AddLocationPage;
import Tim20.KTS_NVT.end_to_end.pages.AdminEventPage;
import Tim20.KTS_NVT.end_to_end.pages.LocationsPage;
import Tim20.KTS_NVT.end_to_end.pages.LoginPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AdminLocationDetailsTest
{

    private WebDriver browser;

    private AddLocationPage addLocationPage;
    private AdminEventPage adminEventPage;
    private LoginPage loginPage;
    private LocationsPage locationsPage;

    private static final String URL = "http://localhost:4200/login";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        addLocationPage = PageFactory.initElements(browser, AddLocationPage.class);
        adminEventPage = PageFactory.initElements(browser, AdminEventPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);
        locationsPage = PageFactory.initElements(browser, LocationsPage.class);
    }

    public void login() {
        loginPage.setUsernameInput("username123");
        loginPage.setPasswordInput("password123");
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")));
    }

    @Test
    public void SeeDetailsForOneLocation() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        adminEventPage.ensureLocationsTabButtonIsClickable();
        adminEventPage.getLocationsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/locations"));

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);

        locationsPage.ensureDetailsButtonIsClickable();

        locationsPage.getDetailsButton().click();

        expectedURL = "http://localhost:4200/locations/details/1";
        assertEquals(expectedURL, browser.getCurrentUrl());

        // Ovde pise Location Details na frontu //
        WebElement detailsText = browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-location-details/div[1]/h3"));

        assertEquals("Locations Details", detailsText.getText());
    }

}
