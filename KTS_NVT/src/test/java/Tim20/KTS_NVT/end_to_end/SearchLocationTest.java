package Tim20.KTS_NVT.end_to_end;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.model.Location;


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
    public void SearchLocation() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);
    }

    @Test
    public void SeeLocationDetails() {

        String expectedURL = "http://localhost:4200/locations";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Location 1";

        locationsPage.setSearchLocationInput(searchValue);

        locationsPage.ensureDetailsButtonIsClickable();

        locationsPage.getDetailsButton().click();
    }
}
