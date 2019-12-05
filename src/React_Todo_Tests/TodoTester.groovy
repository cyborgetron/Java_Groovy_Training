// Code for Challenge #4 from Charlie, 11/25/19

package React_Todo_Tests

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import static org.junit.Assert.assertTrue;

public class TodoTester {
    private WebDriver chrome;
    private static final String URL = "https://react-redux-todomvc.stackblitz.io/"



    @Before
    public void navigateToTestPage() {
        System.setProperty("webdriver.chrome.driver", "/Users/chrisborgert/Callaway/Training/StoneRiverJavaTutorials/IntermediateTutorials/Tutorials/src/chromedriver");
        chrome = new ChromeDriver();
        chrome.get(URL);
    }

    @After
    public void closeDownBrowser() {
        chrome.quit();
    }

    // "Functional" objects

    public void openPage() {
        WebDriverWait wait = new WebDriverWait(chrome, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PageModel.waitOnThis)));
    }

    public void addItem(String task) {
        WebElement testInput = chrome.findElement(By.xpath("//input[@class ='new-todo']"));
        testInput.sendKeys(task);
        testInput.sendKeys(Keys.ENTER);
    }

    public void completeItem() {
        List<WebElement> chekcboxEls = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//input"));
        chekcboxEls[0].click();
    }

    public void deleteItem() {
        // Performs a 'hover' to make hidden element visible, then a 'click' on said element. Taken from a SO post and modified. Make notes on this later.
        Actions action = new Actions(chrome);
        WebElement we = chrome.findElement(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        action.moveToElement(we).build().perform();
        action.moveToElement(chrome.findElement(By.xpath("//ul[@class='todo-list']//div[@class='view']//button"))).click().build().perform();
    }

    public void clearCompleted() {
        chrome.findElement(By.xpath(("//footer/button"))).click();
    }

    @Test
    public void getPageTest() {
        openPage();
    }

    @Test
    public void checkItemTest() {
        openPage();
        addItem("test task");
        List<WebElement> todoItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assertTrue(todoItems[0].getText() == "test task");
    }

    @Test
    public void completeTodoTest() {
        openPage();
        addItem("test task");
        completeItem();

        // click 'Completed', make sure item is marked 'Completed'
        chrome.findElement(By.xpath(("//footer/ul/li[3]/a"))).click();
        assert chrome.findElement(By.xpath("//ul[@class='todo-list']//li")).getAttribute("class") == "completed";

        // Make sure the item is on the list
        List<WebElement> completedItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert completedItems[0].getText() == "test task";


        // Make sure it is NOT on the 'Active' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[2]/a"))).click();
        List<WebElement> activeItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert activeItems[0].getText() != "test task";
    }

    @Test
    public void deleteActiveTodoTest() {
        openPage();
        addItem("test task");
        deleteItem();

        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != "test task";
    }

    @Test
    public void deleteCompletedTodoTest() {
        openPage();
        addItem("test task");
        completeItem();
        deleteItem();

        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != "test task";
    }

    @Test
    public void clearCompletedTest() {
        openPage();
        addItem("test task");
        completeItem();
        addItem("test task 2")

        // click 'Completed', make sure item is marked 'Completed'
        chrome.findElement(By.xpath(("//footer/ul/li[3]/a"))).click();
        assert chrome.findElement(By.xpath("//ul[@class='todo-list']//li")).getAttribute("class") == "completed";

        // Make sure the item is on the list
        List<WebElement> completedItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert completedItems[0].getText() == "test task";

        clearCompleted();

        // ensure "test task" is not on the 'all' list after being completed and cleared
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != "test task";
        // ensure "test task 2" is still on the 'all' list
        assert allItems[0].getText() == "test task 2";
    }
}
