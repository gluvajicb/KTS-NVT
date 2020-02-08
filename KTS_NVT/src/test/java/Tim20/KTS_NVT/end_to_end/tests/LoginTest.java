package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.LoginPage;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class LoginTest {

    private WebDriver browser;

    private LoginPage loginPage;

    private static final String URL = "http://localhost:4200/login";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        loginPage = PageFactory.initElements(browser, LoginPage.class);
    }


    @Test
    public void loginAdminValidTest() {

        String expectedURL = "http://localhost:4200/login";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String username = "username123";
        loginPage.setUsernameInput(username);

        String password = "password123";
        loginPage.setPasswordInput(password);

        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")));

        assertEquals("Logged in as ADMIN.", browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")).getText());

    }

    @Test
    public void loginUserValidTest() {

        String expectedURL = "http://localhost:4200/login";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String username = "username124";
        loginPage.setUsernameInput(username);

        String password = "password123";
        loginPage.setPasswordInput(password);

        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")));

        assertEquals("Logged in as USER.", browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/div")).getText());

    }

    @Test
    public void loginWithNoParametersTest() {

        String expectedURL = "http://localhost:4200/login";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String username = "";
        loginPage.setUsernameInput(username);

        String password = "";
        loginPage.setPasswordInput(password);

        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        assertEquals("Username is required!", browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[1]/div")).getText());
        assertEquals("Password is required", browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[2]/div/div")).getText());
    }

    @Test
    public void loginWithFalseParametersTest() {

        String expectedURL = "http://localhost:4200/login";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String username = "testtesttest";
        loginPage.setUsernameInput(username);

        String password = "testtestfalse";
        loginPage.setPasswordInput(password);

        loginPage.ensureLoginButtonIsClickable();
        loginPage.getLoginButton().click();

        WebDriverWait wait = new WebDriverWait(browser, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[4]/div")));

        assertEquals("Login failed: Provided credentials don't match with any account.", browser.findElement(By.xpath("/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[4]/div")).getText());
    }
}
