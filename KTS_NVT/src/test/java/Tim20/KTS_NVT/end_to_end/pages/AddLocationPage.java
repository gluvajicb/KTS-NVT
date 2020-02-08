package Tim20.KTS_NVT.end_to_end.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AddLocationPage {

    private WebDriver webDriver;

    @FindBy(id = "title")
    private WebElement locationTitleInput;

    @FindBy(id = "address")
    private WebElement locationAddressInput;

    @FindBy(id = "addLocationBtn")
    private WebElement addLocationButton;

    @FindBy(id = "optionsRadios1")
    private WebElement seatsSectorRadioButton;

    @FindBy(id = "optionsRadios2")
    private WebElement standSectorRadioButton;

    @FindBy(id = "inputRow")
    private WebElement numberOfRowsInput;

    @FindBy(id = "inputCol")
    private WebElement numberOfColsInput;

    @FindBy(id = "sectorTitle")
    private WebElement sectorTitleInput;

    @FindBy(id = "inputMaxGuests")
    private WebElement maxGuestsInput;

    @FindBy(xpath = "//*[@id=\"container\"]/form/div[3]/div/button")
    private WebElement addSectorButton;

    @FindBy(xpath = "/html/body/app-root/div/div[2]/div[2]/div/app-location-container/div[3]/div/app-location-list/div[2]/app-table-location/table/tbody/tr")
    private List<WebElement> locationsInTable;

    public AddLocationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebElement getLocationTitleInput() {
        return locationTitleInput;
    }

    public WebElement getLocationAddressInput() {
        return locationAddressInput;
    }

    public WebElement getAddLocationButton() {
        return addLocationButton;
    }

    public WebElement getSeatsSectorRadioButton() { return seatsSectorRadioButton; }

    public WebElement getStandSectorRadioButton() { return standSectorRadioButton; }

    public WebElement getNumberOfRowsInput() { return numberOfRowsInput; }

    public WebElement getNumberOfColsInput() { return numberOfColsInput; }

    public WebElement getSectorTitleInput() { return sectorTitleInput; }

    public WebElement getMaxGuestsInput() { return maxGuestsInput; }

    public WebElement getAddSectorButton() { return addSectorButton; }

    public List<WebElement> getLocationsInTable() { return locationsInTable; }

    public void setLocationTitleInput(String value) {
        WebElement el = getLocationTitleInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setLocationAddressInput(String value) {
        WebElement el = getLocationAddressInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setNumberOfRowsInput(String value) {
        WebElement el = getNumberOfRowsInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setNumberOfColsInput(String value) {
        WebElement el = getNumberOfColsInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setSectorTitleInput(String value) {
        WebElement el = getSectorTitleInput();
        el.clear();
        el.sendKeys(value);
    }

    public void setMaxGuestsInput(String value) {
        WebElement el = getMaxGuestsInput();
        el.clear();
        el.sendKeys(value);
    }


    public void ensureAddLocationButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(addLocationButton));
    }

    public void ensureAddSectorButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(addSectorButton));
    }

}
