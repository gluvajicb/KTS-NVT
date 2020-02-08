package Tim20.KTS_NVT.end_to_end.tests;

import Tim20.KTS_NVT.end_to_end.pages.RegisterPage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class RegistrationTest {

    private WebDriver browser;

    private RegisterPage registerPage;

    private static final String URL = "http://localhost:4200/register";

    @Before
    public void setUpSelenium() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();

        browser.navigate().to(URL);

        registerPage = PageFactory.initElements(browser, RegisterPage.class);
    }

    @Test
    public void registrationValidTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "Marko";
        registerPage.setNameInput(name);

        String surname = "Markovic";
        registerPage.setSurnameInput(surname);

        String username = "jovan1328";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko+.232@gmail.com";
        registerPage.setEmailInput(email);

        String password = "password123";
        registerPage.setPasswordInput(password);

        String passwordConf = "password123";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "123123123";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        /*
        assertEquals("Your registration is successful!", registerPage.getSuccessfulAlertMessage().getText());
        */
    }

    @Test
    public void registrationNoInputTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "";
        registerPage.setNameInput(name);

        String surname = "";
        registerPage.setSurnameInput(surname);

        String username = "";
        registerPage.setUsernameInput(username);

        String email = "";
        registerPage.setEmailInput(email);

        String password = "";
        registerPage.setPasswordInput(password);

        String passwordConf = "";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        assertEquals("Name is required", registerPage.getRequiredNameAlert().getText());
        assertEquals("Surname is required", registerPage.getRequiredSurnameAlert().getText());
        assertEquals("Username is required", registerPage.getRequiredUsernameAlert().getText());
        assertEquals("Email is required", registerPage.getRequiredEmailAlert().getText());
        assertEquals("Password is required", registerPage.getRequiredPasswordAlert().getText());
        assertEquals("Password confirmation is required", registerPage.getRequiredPasswordConfAlert().getText());
        assertEquals("Phone number is required", registerPage.getRequiredPhoneAlert().getText());
    }

    @Test
    public void registrationUsernameTakenTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "Marko";
        registerPage.setNameInput(name);

        String surname = "Markovic";
        registerPage.setSurnameInput(surname);

        String username = "username123";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko+555@gmail.com";
        registerPage.setEmailInput(email);

        String password = "password123";
        registerPage.setPasswordInput(password);

        String passwordConf = "password123";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "123123123";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        /*
        assertEquals("Username is taken, please choose another one.", registerPage.getUsernameTakenAlert().getText());
        */

    }

    @Test
    public void registrationValidEmailTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "Marko";
        registerPage.setNameInput(name);

        String surname = "Markovic";
        registerPage.setSurnameInput(surname);

        String username = "username201";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko+555gmail.com";
        registerPage.setEmailInput(email);

        String password = "password123";
        registerPage.setPasswordInput(password);

        String passwordConf = "password123";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "123123123";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        assertEquals("Email must be a valid email address", registerPage.getValidEmailAlert().getText());

    }

    /* ********************************************* */
    /* ********************************************* */
    /* **********Testirano je i za null************* */
    /* *************vrednosti ali se**************** */
    /* ***********Selenium buni za null************* */
    /* ***********prilikom sendKeys funkcije******** */
    /* ********************************************* */
    /* ********************************************* */
    /* ********************************************* */
    /* ********************************************* */

    @Test
    public void registrationInputsTooShortTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "M";
        registerPage.setNameInput(name);

        String surname = "Ma";
        registerPage.setSurnameInput(surname);

        String username = "us";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko+555@gmail.com";
        registerPage.setEmailInput(email);

        String password = "p";
        registerPage.setPasswordInput(password);

        String passwordConf = "p";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "1";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        assertEquals("Name must be at least 3 characters", registerPage.getNameLengthAlert().getText());
        assertEquals("Surname must be at least 3 characters", registerPage.getSurnameLengthAlert().getText());
        assertEquals("Username must be at least 3 characters", registerPage.getUsernameLengthAlert().getText());
        assertEquals("Password must be at least 6 characters", registerPage.getPasswordLengthAlert().getText());
        assertEquals("Phone number must be at least 9 characters", registerPage.getPhoneLengthAlert().getText());
    }

    @Test
    public void registrationEmailInUseTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "Marko";
        registerPage.setNameInput(name);

        String surname = "Markovic";
        registerPage.setSurnameInput(surname);

        String username = "username1923";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko@gmail.com";
        registerPage.setEmailInput(email);

        String password = "password123";
        registerPage.setPasswordInput(password);

        String passwordConf = "password123";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "123123123";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        /*
        assertEquals("Email must be a valid email address", registerPage.getEmailInUseAlert().getText());
        */

    }

    @Test
    public void registrationInvalidConfirmationPasswordTest() {

        String expectedURL = "http://localhost:4200/register";
        assertEquals(expectedURL, browser.getCurrentUrl());

        String name = "Marko";
        registerPage.setNameInput(name);

        String surname = "Markovic";
        registerPage.setSurnameInput(surname);

        String username = "username152";
        registerPage.setUsernameInput(username);

        String email = "gluvajicbranko+313@gmail.com";
        registerPage.setEmailInput(email);

        String password = "password123";
        registerPage.setPasswordInput(password);

        String passwordConf = "passwor123";
        registerPage.setPasswordConfirmationInput(passwordConf);

        String phone = "123123123";
        registerPage.setPhoneNumberInput(phone);

        assertEquals(name, registerPage.getNameInput().getAttribute("value"));
        assertEquals(surname, registerPage.getSurnameInput().getAttribute("value"));
        assertEquals(username, registerPage.getUsernameInput().getAttribute("value"));
        assertEquals(email, registerPage.getEmailInput().getAttribute("value"));
        assertEquals(password, registerPage.getPasswordInput().getAttribute("value"));
        assertEquals(passwordConf, registerPage.getPasswordConfirmationInput().getAttribute("value"));
        assertEquals(phone, registerPage.getPhoneNumberInput().getAttribute("value"));

        registerPage.ensureSignUpButtonIsClickable();

        registerPage.getSignUpButton().click();

        assertEquals("Password confirmation must be same as password", registerPage.getNotSamePasswordAlert().getText());

    }

}
