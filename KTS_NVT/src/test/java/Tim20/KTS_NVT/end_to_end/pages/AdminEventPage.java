package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminEventPage {

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-event-container/div[2]/input")
    private WebElement searchEventInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-event-container/div[4]/div/app-event-list/div/app-table-event/table/tbody/tr")
    private List<WebElement> eventsInTable;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-event-container/div[1]/div/div/button")
    private WebElement addEventButton;

    @FindBy(xpath = "//*[@id=\"category\"]")
    private WebElement selectEventCategory;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-event-container/div[4]/div/app-event-list/div/app-table-event/table/tbody/tr[1]/td[5]/button")
    private WebElement detailsButton;

    /* TABS */

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/li/a")
    private WebElement homeTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[1]/a")
    private WebElement eventsTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[2]/a")
    private WebElement locationsTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[3]/a")
    private WebElement reportsTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[4]/a")
    private WebElement profileTabButton;

    @FindBy(xpath = "//*[@id=\"navbarSupportedContent\"]/ul/ul/li[5]/a")
    private WebElement logoutTabButton;


    public AdminEventPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getSearchEventInput() {
        return searchEventInput;
    }

    public List<WebElement> getEventsInTable() { return eventsInTable; }

    public WebElement getAddEventButton() { return addEventButton; }

    public WebElement getSelectEventCategory() { return selectEventCategory; }

    public WebElement getHomeTabButton() {
        return homeTabButton;
    }

    public WebElement getEventsTabButton() {
        return eventsTabButton;
    }

    public WebElement getLocationsTabButton() {
        return locationsTabButton;
    }

    public WebElement getReportsTabButton() {
        return reportsTabButton;
    }

    public WebElement getProfileTabButton() {
        return profileTabButton;
    }

    public WebElement getLogoutTabButton() {
        return logoutTabButton;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public WebElement getDetailsButton() {
        return detailsButton;
    }

    public void setSearchEventInputInput(String value) {
        WebElement el = getSearchEventInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setSelectEventCategory(String value) {
        WebElement el = getSelectEventCategory();
        el.clear();
        el.sendKeys(value);
    }


    public void ensureAddEventButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(addEventButton));
    }

    public void ensureHomeTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(homeTabButton));
    }

    public void ensureEventsTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(eventsTabButton));
    }

    public void ensureLocationsTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(locationsTabButton));
    }

    public void ensureReportsTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(reportsTabButton));
    }

    public void ensureProfileTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(profileTabButton));
    }

    public void ensureLogoutTabButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(logoutTabButton));
    }

    public void ensureDetailsButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(detailsButton));
    }

}
