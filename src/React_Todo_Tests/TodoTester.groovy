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
    private static final String toDoCheckboxSelector = "#root > div > section > ul > li > div > input";
    private static final String toDoItemLabel = "#root > div > section > ul > li > div > label";
    private static final String allButton = "#root > div > section > footer > ul > li:nth-child(1) > a";
    private static final String activeButton = "#root > div > section > footer > ul > li:nth-child(2) > a";
    private static final String completedButton = "#root > div > section > footer > ul > li:nth-child(3) > a";
    private static final String deleteButton = "#root > div > section > ul > li > div > button";
    private static final String clearCompletedButton = "#root > div > section > footer > button";
    private static final String classEqualsCompleted = "#root > div > section > ul > li";

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

    public void assertItemOnList(String task) {
        // Make sure the item is on the list
        List<WebElement> Items = chrome.findElements(By.cssSelector(toDoItemLabel));
        assert Items[0].getText() == task;
    }

    public void assertItemNotActive() {
        // Make sure it is NOT on the 'Active' List - this code works b/c the last added item stays at the top of the list
        // active button
        chrome.findElement(By.cssSelector((activeButton))).click();
        // to do item label
        List<WebElement> activeItems = chrome.findElements(By.cssSelector(toDoItemLabel));
        assert activeItems[0].getText() != testTaskString;
    }

    public void checkToDoListForItem() {
        // to do item label
        List<WebElement> todoItems = chrome.findElements(By.cssSelector(toDoItemLabel));
        assertTrue(todoItems[0].getText() == testTaskString);
    }

    public void completeItem() {
        List<WebElement> checkboxEls = chrome.findElements(By.cssSelector(toDoCheckboxSelector));
        checkboxEls[0].click();
    }

    public void clickCompletedAndAssert() {
        // click 'Completed', make sure item is marked 'Completed'
        //completed button
        chrome.findElement(By.cssSelector((completedButton))).click();
        //classEqualsCompleted
        assert chrome.findElement(By.cssSelector(classEqualsCompleted)).getAttribute("class") == "completed";

    }

    public void deleteItem() {
        // Performs a 'hover' to make hidden element visible, then a 'click' on said element. Taken from a SO post and modified. Make notes on this later.
        Actions action = new Actions(chrome);
        //to do item label
        WebElement we = chrome.findElement(By.cssSelector(toDoItemLabel));
        action.moveToElement(we).build().perform();
        //delete button
        action.moveToElement(chrome.findElement(By.cssSelector(deleteButton))).click().build().perform();
    }

    public void deletedItemNotOnAll() {
        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        // all button
        chrome.findElement(By.cssSelector((allButton))).click();
        // to do item label
        List<WebElement> allItems = chrome.findElements(By.cssSelector(toDoItemLabel));
        assert allItems[0].getText() != testTaskString;
    }


    public void clearCompleted() {
        // clear completed button
        chrome.findElement(By.cssSelector((clearCompletedButton))).click();
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
        clickCompletedAndAssert();
        assertItemOnList(testTaskString);
        assertItemNotActive();
    }

    //sometimes this doesn't run... doesn't fail, but gives a yellow 'circle x'
    @Test
    public void deleteActiveTodoTest() {
        openPage();
        addItem(testTaskString);
        deleteItem();
        deletedItemNotOnAll();
    }

    @Test
    public void deleteCompletedTodoTest() {
        openPage();
        addItem(testTaskString);
        completeItem();
        deleteItem();
        deletedItemNotOnAll();
    }

    @Test
    public void clearCompletedTest() {
        openPage();
        addItem(testTaskString);
        completeItem();
        addItem(testTaskString1)
        clickCompletedAndAssert();
        assertItemOnList(testTaskString);
        clearCompleted();
        deletedItemNotOnAll();
        assertItemOnList(testTaskString1);
    }
}
