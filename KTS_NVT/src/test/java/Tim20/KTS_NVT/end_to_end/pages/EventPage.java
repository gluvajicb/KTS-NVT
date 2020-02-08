package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EventPage {

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-event-container/div[2]/input")
    private WebElement searchEventInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-event-container/div[4]/div/app-event-list/div/app-table-event/table/tbody/tr")
    private List<WebElement> eventsInTable;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-event-container/div[1]/div/div/button")
    private WebElement addEventButton;

    @FindBy(xpath = "//*[@id=\"category\"]")
    private WebElement selectEventCategory;

    public EventPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getSearchEventInput() {
        return searchEventInput;
    }

    public List<WebElement> getEventsInTable() { return eventsInTable; }

    public WebElement getAddEventButton() { return addEventButton; }

    public WebElement getSelectEventCategory() { return selectEventCategory; }

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
}
