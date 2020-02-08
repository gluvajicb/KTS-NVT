package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[1]/input")
    private WebElement usernameInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[2]/input")
    private WebElement passwordInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-login/div/div/form/div[3]/button")
    private WebElement loginButton;


    public WebDriver getWebDriver() {
        return webDriver;
    }

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getUsernameInput() { return usernameInput; }
    public WebElement getPasswordInput() { return passwordInput; }
    public WebElement getLoginButton() { return loginButton; }

    public void setUsernameInput(String value) {
        WebElement el = getUsernameInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setPasswordInput(String value) {
        WebElement el = getPasswordInput();
        el.clear();
        el.sendKeys(value);
    }

    public void ensureLoginButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(loginButton));
    }

}
