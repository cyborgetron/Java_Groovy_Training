// Code for Challenge #5 from Charlie, 11/30/19
// changes made 12/4
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
    private static final String pathToDriver = "/Users/chrisborgert/Callaway/Github/Training/Java_Groovy_Training/src/chromedriver";

    // * below strings must be distinct *
    private static final testTaskString = "test task"
    private static final testTaskString1 = "another test task"

    // page model... sorta?
    private static final String URL = "https://react-redux-todomvc.stackblitz.io/";
    private static final String rootSelector = "#root"; // or is "div.todoapp" better??
    private static final String toDoInputSelector = "html > body > div > div > header > input";
    private static final String toDoCheckboxSelector = "#root > div > section > ul > li > div > input"





    // "Functional" objects

    public void openPage() {
        WebDriverWait wait = new WebDriverWait(chrome, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rootSelector)));
    }

    public void addItem(String task) {
        WebElement testInput = chrome.findElement(By.cssSelector(toDoInputSelector));
        testInput.sendKeys(task);
        testInput.sendKeys(Keys.ENTER);
    }

    public void assertItemOnList() {
        // Make sure the item is on the list
        List<WebElement> completedItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert completedItems[0].getText() == testTaskString;
    }

    public void assertItemNotActive() {
        // Make sure it is NOT on the 'Active' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[2]/a"))).click();
        List<WebElement> activeItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert activeItems[0].getText() != testTaskString;
    }

    public void checkToDoListForItem() {
        List<WebElement> todoItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assertTrue(todoItems[0].getText() == testTaskString);
    }

    public void completeItem() {
        List<WebElement> checkboxEls = chrome.findElements(By.cssSelector(toDoCheckboxSelector));
        checkboxEls[0].click();
    }

    public void clickCheckboxAndAssert() {
        // click 'Completed', make sure item is marked 'Completed'
        chrome.findElement(By.xpath(("//footer/ul/li[3]/a"))).click();
        assert chrome.findElement(By.xpath("//ul[@class='todo-list']//li")).getAttribute("class") == "completed";

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

    // ---------- Tests ----------- //

    @Before
    public void navigateToTestPage() {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        chrome = new ChromeDriver();
        chrome.get(URL);
    }

    @After
    public void closeDownBrowser() {
        chrome.quit();
    }

    @Test
    public void getPageTest() {
        openPage();
    }

    @Test
    public void checkItemTest() {
        openPage();
        addItem(testTaskString);
        checkToDoListForItem();
    }

    @Test
    public void completeTodoTest() {
        openPage();
        addItem(testTaskString);
        completeItem();
        clickCheckboxAndAssert();
        assertItemOnList();
        assertItemNotActive();
    }

    @Test
    public void deleteActiveTodoTest() {
        openPage();
        addItem(testTaskString);
        deleteItem();

        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != testTaskString;
    }

    @Test
    public void deleteCompletedTodoTest() {
        openPage();
        addItem(testTaskString);
        completeItem();
        deleteItem();

        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != testTaskString;
    }

    @Test
    public void clearCompletedTest() {
        openPage();
        addItem(testTaskString);
        completeItem();
        addItem(testTaskString1)

        // click 'Completed', make sure item is marked 'Completed'
        chrome.findElement(By.xpath(("//footer/ul/li[3]/a"))).click();
        assert chrome.findElement(By.xpath("//ul[@class='todo-list']//li")).getAttribute("class") == "completed";

        // Make sure the item is on the list
        List<WebElement> completedItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert completedItems[0].getText() == testTaskString;

        clearCompleted();

        // ensure "test task" is not on the 'all' list after being completed and cleared
        chrome.findElement(By.xpath(("//footer/ul/li[1]/a"))).click();
        List<WebElement> allItems = chrome.findElements(By.xpath("//ul[@class='todo-list']//div[@class='view']//label"));
        assert allItems[0].getText() != testTaskString;
        // ensure "test task 2" is still on the 'all' list
        assert allItems[0].getText() == testTaskString1;
    }
}
