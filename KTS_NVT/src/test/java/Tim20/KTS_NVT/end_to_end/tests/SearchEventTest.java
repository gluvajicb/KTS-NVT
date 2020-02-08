package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.EventPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class SearchEventTest {

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
    public void SearchOneEvent() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Event 1";

        eventPage.setSearchEventInputInput(searchValue);

        assertEquals(1, eventPage.getEventsInTable().size());
    }


    // Test prolazi pod uslovom da se za Eve nalaze samo 2 eventa u testnim podacima //
    @Test
    public void SearchTwoEvents() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Eve";

        eventPage.setSearchEventInputInput(searchValue);

        assertEquals(2, eventPage.getEventsInTable().size());

    }

    @Test
    public void SearchUnexistingEvent() {

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Aasijfsdogin";

        eventPage.setSearchEventInputInput(searchValue);

        assertEquals(0, eventPage.getEventsInTable().size());

    }



}
