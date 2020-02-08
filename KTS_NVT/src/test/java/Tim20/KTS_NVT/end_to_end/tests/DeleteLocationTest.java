package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.LocationsPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class DeleteLocationTest {

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


    // Mora se 2x mora kliknuti na delete da bi se obrisalo //
    @Test
    public void DeleteOneLocationTest() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        int numberOfLocations = locationsPage.getLocationsInTable().size();

        locationsPage.ensureDeleteButtonIsClickable();
        locationsPage.getDeletableLocationButton().click();
        locationsPage.getDeletableLocationButton().click();

        assertEquals(locationsPage.getLocationsInTable().size(), numberOfLocations - 1);
    }

}
