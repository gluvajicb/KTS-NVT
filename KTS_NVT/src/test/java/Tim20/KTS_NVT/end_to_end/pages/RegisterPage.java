package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

    private WebDriver webDriver;

    /* INPUTS */

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[1]/input")
    private WebElement nameInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[2]/input")
    private WebElement surnameInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[3]/input")
    private WebElement usernameInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[4]/input")
    private WebElement emailInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[5]/input")
    private WebElement passwordInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[6]/input")
    private WebElement passwordConfirmationInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[7]/input")
    private WebElement phoneNumberInput;

    /* SIGNUP BUTTON */

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[8]/button")
    private WebElement signUpButton;

    /* ALERTS */

            /* Success Alert */
    @FindBy(id = "successAlert")
    private WebElement successfulAlertMessage;

            /* Required Input Alerts */
    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[1]/div/div")
    private WebElement requiredNameAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[2]/div/div")
    private WebElement requiredSurnameAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[3]/div/div")
    private WebElement requiredUsernameAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[4]/div/div")
    private WebElement requiredEmailAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[5]/div/div")
    private WebElement requiredPasswordAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[6]/div/div")
    private WebElement requiredPasswordConfAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[7]/div/div")
    private WebElement requiredPhoneAlert;

            /* Length Alerts */
    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[5]/div/div")
    private WebElement passwordLengthAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[7]/div/div")
    private WebElement phoneLengthAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[3]/div/div")
    private WebElement usernameLengthAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[1]/div/div")
    private WebElement nameLengthAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[2]/div/div")
    private WebElement surnameLengthAlert;

            /* Other Alerts */

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[9]")
    private WebElement usernameTakenAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[4]/div/div")
    private WebElement validEmailAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[9]")
    private WebElement emailInUseAlert;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-register/div/div/form/div[6]/div/div")
    private WebElement notSamePasswordAlert;

    /* GETTERS */

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public RegisterPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getNameInput() { return nameInput; }

    public WebElement getSurnameInput() { return surnameInput; }

    public WebElement getUsernameInput() { return usernameInput; }

    public WebElement getEmailInput() { return emailInput; }

    public WebElement getPasswordInput() { return passwordInput; }

    public WebElement getPasswordConfirmationInput() { return passwordConfirmationInput; }

    public WebElement getPhoneNumberInput() { return phoneNumberInput; }

    public WebElement getSignUpButton() { return signUpButton; }

    public WebElement getSuccessfulAlertMessage() { return successfulAlertMessage; }

    public WebElement getRequiredNameAlert() {
        return requiredNameAlert;
    }

    public WebElement getRequiredSurnameAlert() {
        return requiredSurnameAlert;
    }

    public WebElement getRequiredUsernameAlert() {
        return requiredUsernameAlert;
    }

    public WebElement getRequiredEmailAlert() {
        return requiredEmailAlert;
    }

    public WebElement getRequiredPasswordAlert() {
        return requiredPasswordAlert;
    }

    public WebElement getRequiredPasswordConfAlert() {
        return requiredPasswordConfAlert;
    }

    public WebElement getRequiredPhoneAlert() {
        return requiredPhoneAlert;
    }

    public WebElement getUsernameTakenAlert() {
        return usernameTakenAlert;
    }

    public WebElement getValidEmailAlert() {
        return validEmailAlert;
    }

    public WebElement getPasswordLengthAlert() {
        return passwordLengthAlert;
    }

    public WebElement getPhoneLengthAlert() {
        return phoneLengthAlert;
    }

    public WebElement getUsernameLengthAlert() {
        return usernameLengthAlert;
    }

    public WebElement getNameLengthAlert() {
        return nameLengthAlert;
    }

    public WebElement getSurnameLengthAlert() {
        return surnameLengthAlert;
    }

    public WebElement getEmailInUseAlert() {
        return emailInUseAlert;
    }

    public WebElement getNotSamePasswordAlert() {
        return notSamePasswordAlert;
    }

    /* SETTERS */

    public void setNameInput(String value) {
        WebElement el = getNameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setSurnameInput(String value) {
        WebElement el = getSurnameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setUsernameInput(String value) {
        WebElement el = getUsernameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setEmailInput(String value) {
        WebElement el = getEmailInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordInput(String value) {
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordConfirmationInput(String value) {
        WebElement el = getPasswordConfirmationInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setPhoneNumberInput(String value) {
        WebElement el = getPhoneNumberInput();
        el.clear();
        el.sendKeys(value);
    }

    public void ensureSignUpButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(signUpButton));
    }

}
