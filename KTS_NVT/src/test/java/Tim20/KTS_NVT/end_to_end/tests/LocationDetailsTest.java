package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.LocationsPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class LocationDetailsTest
{

    private WebDriver browser;

    private LocationsPage locationsPage;

    private static final String URL = "http://localhost:4200/locations";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        locationsPage = PageFactory.initElements(browser, LocationsPage.class);
    }



    @Test
    public void SeeDetailsForOneLocation() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);

        locationsPage.ensureDetailsButtonIsClickable();

        locationsPage.getDetailsButton().click();

        expectedURL = "http://localhost:4200/locations/details/1";
        assertEquals(expectedURL, browser.getCurrentUrl());

        // Ovde pise Location Details na frontu //
        WebElement detailsText = browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/div/app-location-details/div[1]/h3"));

        assertEquals("Locations Details", detailsText.getText());
    }

}
