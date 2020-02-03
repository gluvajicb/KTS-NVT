package Tim20.KTS_NVT.end_to_end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddLocationPage {

    private WebDriver webDriver;

    @FindBy(id = "title")
    private WebElement locationTitleInput;

    @FindBy(id = "address")
    private WebElement locationAddressInput;

    @FindBy(id = "addLocationBtn")
    private WebElement addLocationButton;

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


    public void ensureAddLocationButtonIsClickable() {
        (new WebDriverWait(webDriver, 10)).until(ExpectedConditions.elementToBeClickable(addLocationButton));
    }

}
