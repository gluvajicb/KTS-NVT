package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.AdminEventPage;
import Tim20.KTS_NVT.end_to_end.pages.LoginPage;
import Tim20.KTS_NVT.end_to_end.pages.UserEventPage;
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
import org.openqa.selenium.By;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class UserSearchEventTest {

    private WebDriver browser;

    private UserEventPage userEventPage;
    private LoginPage loginPage;

    private static final String URL = "http://localhost:4200/login";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        userEventPage = PageFactory.initElements(browser, UserEventPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);

    }

    public void login() {
        loginPage.setUsernameInput("username124");
        loginPage.setPasswordInput("password123");
        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")));
    }

    @Test
    public void SearchOneEventTest() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        userEventPage.ensureEventsTabButtonIsClickable();
        userEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/user/events"));


        String expectedURL = "http://localhost:4200/user/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Event1";

        userEventPage.setSearchEventInput(searchValue);

        int numberOfEvents = browser.findElements(By.xpath("/html/body/app-root/div/div[2]/div/div/app-user-event-container/div[4]/div/app-user-event-list/div/app-event-card/div")).size();

        assertEquals(1, numberOfEvents);
    }

    /*
    @Test
    public void SearchTwoEventsTest() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        userEventPage.ensureEventsTabButtonIsClickable();
        userEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/user/events"));


        String expectedURL = "http://localhost:4200/user/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Eve";

        userEventPage.setSearchEventInput(searchValue);

        int numberOfEvents = browser.findElements(By.xpath("/html/body/app-root/div/div[2]/div/div/app-user-event-container/div[4]/div/app-user-event-list/div/app-event-card/div")).size();

        List<WebElement> elements = browser.findElements(By.xpath("/html/body/app-root/div/div[2]/div/div/app-user-event-container/div[4]/div/app-user-event-list/div/app-event-card/div"));
        numberOfEvents = elements.size();


        assertEquals(2, numberOfEvents);
    }

    @Test
    public void SearchUnexistingEventTest() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        userEventPage.ensureEventsTabButtonIsClickable();
        userEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/user/events"));


        String expectedURL = "http://localhost:4200/user/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "asdagrherh";

        userEventPage.setSearchEventInput(searchValue);

        int numberOfEvents = browser.findElements(By.xpath("/html/body/app-root/div/div[2]/div/div/app-user-event-container/div[4]/div/app-user-event-list/div/app-event-card/div")).size();

        assertEquals(0, numberOfEvents);
    }
    */
}
