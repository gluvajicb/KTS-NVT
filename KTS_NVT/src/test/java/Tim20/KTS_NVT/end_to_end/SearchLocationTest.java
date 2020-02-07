package Tim20.KTS_NVT.end_to_end;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.model.Location;


import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SearchLocationTest
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
    public void SearchOneLocation() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);

        assertEquals(1, locationsPage.getLocationsInTable().size());
    }


    // Test prolazi pod uslovom da se za Loc nalaze samo 2 lokacije u testnim podacima //
    @Test
    public void SearchTwoLocations() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Loc";

        locationsPage.setSearchLocationInput(searchValue);

        assertEquals(2, locationsPage.getLocationsInTable().size());

    }

    @Test
    public void SearchUnexistingLocation() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Aasijfsdogin";

        locationsPage.setSearchLocationInput(searchValue);

        assertEquals(0, locationsPage.getLocationsInTable().size());

    }

    @Test
    public void SeeDetailsForOneLocation() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);

        locationsPage.ensureDetailsButtonIsClickable();

        locationsPage.getDetailsButton().click();

        // Ovde pise Location Details na frontu //
        WebElement detailsText = browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div[2]/div/app-location-details/div[1]/h3"));

        assertEquals("Locations Details", detailsText.getText());
    }

}
