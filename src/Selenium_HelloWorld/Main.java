package Selenium_HelloWorld;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Path to Gecko Driver
//        System.setProperty("webdriver.gecko.driver","/Users/chrisborgert/Callaway/Training/StoneRiverJavaTutorials/IntermediateTutorials/Tutorials/src/geckodriver");

        // URL to navigate to
        String url = "https://nationaltoday.com/what-is-today/";

        // Path to Chrome Driver
        System.setProperty("webdriver.chrome.driver", "/Users/chrisborgert/Callaway/Training/StoneRiverJavaTutorials/IntermediateTutorials/Tutorials/src/chromedriver");

        // XPath to "today's holiday"
        String whatHolidayXpath = "//div[@class='holiday-title']//h2[@class='entry-title']";

        // Open a new browser window
        ChromeDriver driver = new ChromeDriver();
//        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        try {
            // Navigate to our page
            driver.navigate().to(url);

            // Find the name of today's holiday
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(whatHolidayXpath)));
            WebElement todaysHoliday = driver.findElement(By.xpath(whatHolidayXpath));

            // Print Holiday name
            System.out.println(todaysHoliday.getAttribute("textContent"));
        }

        catch(Exception e){
            System.out.println(e);
        }

        // Close browser window

        finally {
            driver.quit();
        }

    }
}
