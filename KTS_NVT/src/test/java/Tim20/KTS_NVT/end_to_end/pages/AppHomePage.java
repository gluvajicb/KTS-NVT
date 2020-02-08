package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppHomePage {

    private WebDriver webDriver;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/li/a")
    private WebElement homeTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[1]/a")
    private WebElement loginTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[2]/a")
    private WebElement registerTabButton;


    public WebDriver getWebDriver() {
        return webDriver;
    }

    public AppHomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getHomeTabButton() { return homeTabButton; }
    public WebElement getLoginTabButton() { return loginTabButton; }
    public WebElement getRegisterTabButton() { return registerTabButton; }

    public void ensureHomeTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(homeTabButton));
    }

    public void ensureLoginTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(loginTabButton));
    }

    public void ensureRegisterTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(registerTabButton));
    }
}
