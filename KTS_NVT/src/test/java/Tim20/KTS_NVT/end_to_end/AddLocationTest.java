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
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AddLocationTest {

    private WebDriver browser;

    private AddLocationPage addLocationPage;

    private static final String URL = "http://localhost:4200/locations/add";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        addLocationPage = PageFactory.initElements(browser, AddLocationPage.class);
    }



    @Test
    public void AddLocation() {

        String expectedURL = "http://localhost:4200/locations/add";
        assertEquals(expectedURL, browser.getCurrentUrl());


        String title = "Title Num 515";
        String address = "Address Num 515";

        addLocationPage.setLocationTitleInput(title);
        addLocationPage.setLocationAddressInput(address);

        addLocationPage.ensureAddLocationButtonIsClickable();

        assertEquals(addLocationPage.getLocationTitleInput().getAttribute("value"), title);
        assertEquals(addLocationPage.getLocationAddressInput().getAttribute("value"), address);

        addLocationPage.getAddLocationButton().click();
    }


    @Test
    public void AddLocationWithStandSector() {

        String expectedURL = "http://localhost:4200/locations/add";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String title = "Title Num 615";
        String address = "Address Num 615";
        addLocationPage.setLocationTitleInput(title);
        addLocationPage.setLocationAddressInput(address);

        String sectorTitle = "Sector Title 615";
        addLocationPage.setSectorTitleInput(sectorTitle);

        addLocationPage.getStandSectorRadioButton().click();

        String maxGuests = "50";
        addLocationPage.setMaxGuestsInput(maxGuests);

        assertEquals(addLocationPage.getLocationTitleInput().getAttribute("value"), title);
        assertEquals(addLocationPage.getLocationAddressInput().getAttribute("value"), address);
        assertEquals(addLocationPage.getSectorTitleInput().getAttribute("value"), sectorTitle);
        assertEquals(addLocationPage.getMaxGuestsInput().getAttribute("value"), maxGuests);


        addLocationPage.ensureAddSectorButtonIsClickable();
        addLocationPage.getAddSectorButton().click();

        addLocationPage.ensureAddLocationButtonIsClickable();
        addLocationPage.getAddLocationButton().click();
    }


    @Test
    public void AddLocationWithSeatsSector() {

        String expectedURL = "http://localhost:4200/locations/add";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String title = "Title Num 715";
        String address = "Address Num 715";
        addLocationPage.setLocationTitleInput(title);
        addLocationPage.setLocationAddressInput(address);

        String sectorTitle = "Sector Title 715";
        addLocationPage.setSectorTitleInput(sectorTitle);

        addLocationPage.getSeatsSectorRadioButton().click();

        String numOfRows = "5";
        String numOfCols = "3";
        addLocationPage.setNumberOfRowsInput(numOfRows);
        addLocationPage.setNumberOfColsInput(numOfCols);

        assertEquals(addLocationPage.getLocationTitleInput().getAttribute("value"), title);
        assertEquals(addLocationPage.getLocationAddressInput().getAttribute("value"), address);
        assertEquals(addLocationPage.getSectorTitleInput().getAttribute("value"), sectorTitle);
        assertEquals(addLocationPage.getNumberOfRowsInput().getAttribute("value"), numOfRows);
        assertEquals(addLocationPage.getNumberOfColsInput().getAttribute("value"), numOfCols);


        addLocationPage.ensureAddSectorButtonIsClickable();
        addLocationPage.getAddSectorButton().click();

        addLocationPage.ensureAddLocationButtonIsClickable();
        addLocationPage.getAddLocationButton().click();
    }



}
