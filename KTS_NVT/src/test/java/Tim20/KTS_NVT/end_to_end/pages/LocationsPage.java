package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class LocationsPage {

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-location-container/div[2]/input")
    private WebElement searchLocationInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-location-container/div[1]/div/div/button")
    private WebElement addLocationButton;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-location-container/div[3]/div/app-location-list/div[2]/app-table-location/table/tbody/tr[1]/td[3]/button[1]")
    private WebElement detailsButton;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-location-container/div[3]/div/app-location-list/div[2]/app-table-location/table/tbody/tr")
    private List<WebElement> locationsInTable;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div/div/app-location-container/div[3]/div/app-location-list/div[2]/app-table-location/table/tbody/tr[3]/td[3]/button[3]")
    private WebElement deletableLocationButton;

    public WebDriver getWebDriver() {
        return webDriver;
    }


    public LocationsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getSearchLocationInput() { return searchLocationInput; }

    public WebElement getAddLocationButton() { return  addLocationButton; }

    public WebElement getDetailsButton() { return detailsButton; }

    public List<WebElement> getLocationsInTable() { return locationsInTable; }

    public WebElement getDeletableLocationButton() { return deletableLocationButton; }


    public void setSearchLocationInput(String value) {
        WebElement el = getSearchLocationInput();
        el.clear();
        el.sendKeys(value);
    }

    public void ensureAddLocationButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(addLocationButton));
    }

    public void ensureDetailsButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(detailsButton));
    }

    public void ensureDeleteButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(deletableLocationButton));
    }

}
