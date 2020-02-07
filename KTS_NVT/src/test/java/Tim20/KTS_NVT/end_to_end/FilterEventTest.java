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

import org.openqa.selenium.support.ui.Select;


import static org.junit.Assert.assertEquals;
import static org.openqa.grid.common.SeleniumProtocol.Selenium;

@RunWith(SpringRunner.class)
public class FilterEventTest {

    private WebDriver browser;

    private EventPage eventPage;

    private static final String URL = "http://localhost:4200/events";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        eventPage = PageFactory.initElements(browser, EventPage.class);
    }

    @Test
    public void filterEventsByShowCategory() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        Select eventCategory = new Select(browser.findElement(By.xpath("//*[@id=\"category\"]")));
        eventCategory.selectByVisibleText("SHOW");

        assertEquals(2, eventPage.getEventsInTable().size());

    }

    @Test
    public void filterEventsByMusicCategory() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        Select eventCategory = new Select(browser.findElement(By.xpath("//*[@id=\"category\"]")));
        eventCategory.selectByVisibleText("MUSIC");

        assertEquals(1, eventPage.getEventsInTable().size());

    }

    @Test
    public void filterEventsBySportCategory() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        Select eventCategory = new Select(browser.findElement(By.xpath("//*[@id=\"category\"]")));
        eventCategory.selectByVisibleText("SPORT");

        assertEquals(0, eventPage.getEventsInTable().size());

    }

    @Test
    public void filterEventsByAnyCategory() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        Select eventCategory = new Select(browser.findElement(By.xpath("//*[@id=\"category\"]")));
        eventCategory.selectByVisibleText("ANY CATEGORY");

        assertEquals(3, eventPage.getEventsInTable().size());

    }


}
