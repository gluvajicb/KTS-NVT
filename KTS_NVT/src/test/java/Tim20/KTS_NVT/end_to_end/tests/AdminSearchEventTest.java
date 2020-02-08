package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.AdminEventPage;
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
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
public class AdminSearchEventTest {

    private WebDriver browser;

    private AdminEventPage adminEventPage;
    private LoginPage loginPage;

    private static final String URL = "http://localhost:4200/login";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        adminEventPage = PageFactory.initElements(browser, AdminEventPage.class);
        loginPage = PageFactory.initElements(browser, LoginPage.class);

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
    public void SearchOneEvent() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        adminEventPage.ensureEventsTabButtonIsClickable();
        adminEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/events"));


        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Event1";

        adminEventPage.setSearchEventInputInput(searchValue);

        assertEquals(1, adminEventPage.getEventsInTable().size());

     }

    // Test prolazi pod uslovom da se za Eve nalaze samo 2 eventa u testnim podacima //
    @Test
    public void SearchTwoEvents() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        adminEventPage.ensureEventsTabButtonIsClickable();
        adminEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/events"));

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Eve";

        adminEventPage.setSearchEventInputInput(searchValue);

        assertEquals(2, adminEventPage.getEventsInTable().size());

    }

    @Test
    public void SearchUnexistingEvent() {

        login();

        new WebDriverWait(browser, 20).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        adminEventPage.ensureEventsTabButtonIsClickable();
        adminEventPage.getEventsTabButton().click();

        (new WebDriverWait(browser, 20)).until(ExpectedConditions.urlToBe("http://localhost:4200/events"));

        String expectedURL = "http://localhost:4200/events";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String searchValue = "Aasijfsdogin";

        adminEventPage.setSearchEventInputInput(searchValue);

        assertEquals(0, adminEventPage.getEventsInTable().size());

    }



}
