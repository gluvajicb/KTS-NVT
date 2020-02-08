package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UserEventPage
{

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-user-event-container/div[1]/input")
    private WebElement searchEventInput;

    @FindBy(xpath = "//*[@id=\"category\"]")
    private WebElement selectEventCategory;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/li/a")
    private WebElement homeTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[1]/a")
    private WebElement eventsTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[2]/a")
    private WebElement profileTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[3]/a")
    private WebElement logoutTabButton;

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public UserEventPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getSearchEventInput() {
        return searchEventInput;
    }

    public WebElement getSelectEventCategory() {
        return selectEventCategory;
    }

    public WebElement getHomeTabButton() {
        return homeTabButton;
    }

    public WebElement getEventsTabButton() {
        return eventsTabButton;
    }

    public WebElement getProfileTabButton() {
        return profileTabButton;
    }

    public WebElement getLogoutTabButton() {
        return logoutTabButton;
    }

    public void setSearchEventInput(String value) {
        WebElement el = getSearchEventInput();
        el.clear();
        el.sendKeys(value);
    }

    public void ensureHomeTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(homeTabButton));
    }

    public void ensureEventsTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(eventsTabButton));
    }

    public void ensureProfileTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(profileTabButton));
    }

    public void ensureLogoutTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(logoutTabButton));
    }

}
