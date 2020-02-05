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


}
