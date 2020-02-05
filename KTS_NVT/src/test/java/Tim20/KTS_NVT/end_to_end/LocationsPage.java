package Tim20.KTS_NVT.end_to_end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LocationsPage {

    private WebDriver webDriver;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-location-container/div[2]/input")
    private WebElement searchLocationInput;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-location-container/div[1]/div/div/button")
    private WebElement addLocationButton;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-location-container/div[3]/div/app-location-list/div[2]/app-table-location/table/tbody/tr/td[3]/button[1]")
    private WebElement detailsButton;

    public LocationsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getSearchLocationInput() { return searchLocationInput; }

    public WebElement getAddLocationButton() { return  addLocationButton; }

    public WebElement getDetailsButton() { return detailsButton; }

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


}
