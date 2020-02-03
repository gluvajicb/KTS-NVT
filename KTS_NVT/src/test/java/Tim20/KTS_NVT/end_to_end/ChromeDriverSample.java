import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverSample {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.get("http://www.facebook.com");

        driver.manage().window().maximize();
    }



}