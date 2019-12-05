package Selenium_2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.*;


import java.util.ArrayList;
import java.util.List;


public class Main {
    // Create 'holiday' class
    public static class Holiday {
        int ID;
        String day;
        String description;
    };


    public void main(String[] args) {
        getTodaysHoliday();
        getUpcomingHolidayInfo();
    }

    @Test
    public void getTodaysHoliday() {
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

    @Test
    public void getUpcomingHolidayInfo() {
        // URL to navigate to
        String url="https://nationaltoday.com/";

        // Path to Chrome Driver
        System.setProperty("webdriver.chrome.driver","/Users/chrisborgert/Callaway/Training/StoneRiverJavaTutorials/IntermediateTutorials/Tutorials/src/chromedriver");

        // XPath to "Upcoming Holidays" urls
        String whatHolidaysXpath="//div[@class='hp-grid-day-card hp-grid-day-card-flex']//a";


        // Open a new browser window
        ChromeDriver driver=new ChromeDriver();
        WebDriverWait wait=new WebDriverWait(driver,30);

        try{
            // Navigate to our page
            driver.navigate().to(url);

            // Find the name of url of each upcoming holiday
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(whatHolidaysXpath)));

            List<WebElement> upcomingList=driver.findElements(By.xpath(whatHolidaysXpath));

            // Initialize empty ArrayList for URLs
            List<String> urlList=new ArrayList<>();

            // Add URLs scraped from upcomingList to urlList
            for(WebElement holidayElement:upcomingList){
                urlList.add(holidayElement.getAttribute("href"));
            }

//            System.out.println(urlList);
            Object[]holidayArray=new Object[urlList.size()];

            for(String urlString:urlList){

                // incrementer for easy holiday ID
//                int i = 0;

                driver.navigate().to(urlString);

                Holiday holiday=new Holiday();

//                WebElement parentE = driver.findElement(By.xpath("//div[@class='page-content-wrap']"));

                WebElement dayE=driver.findElement(By.xpath("//*[@id='contentHeader']/div[@class='holiday-content-inner']//div[@class='holiday-title']/h1"));
                holiday.day=dayE.getText();
                System.out.println(holiday.day);

                WebElement descE=driver.findElement(By.xpath("//div[@class='page-content-wrap']//div[@class='entry-content-inner']//div"));
                holiday.description=descE.getText();
                System.out.println(holiday.description);


//                holidayArray[i] = holiday;

//                i++;

                driver.navigate().back();
            }

             /*   for (Object holiday : holidayArray){
                    System.out.println(Arrays.toString(holiday));
                }
*/

        }

        catch(Exception e){
            System.out.println(e);
        }

        // Quit browser
        finally{
            driver.quit();
        }
    }

}


