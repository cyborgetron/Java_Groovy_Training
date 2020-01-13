package React_Todo_Tests

import org.hamcrest.*
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import static org.junit.Assert.assertTrue // why is this 'import static' ??
import static org.junit.matchers.JUnitMatchers.*;



class PageModel {


    // page model... sorta?
    private static final String URL = "https://react-redux-todomvc.stackblitz.io/";
    private static final String rootSelector = "#root"; // or is "div.todoapp" better??
    private static final String toDoInputSelector = ".header > .new-todo";
    private static final String fullList = '#root > div > section > ul > li'
    private static final String toDoCheckboxSelector = "#root > div > section > ul > li:nth-child(item+1) > div > input";
    private static final String toDoItemLabel = "#root > div > section > ul > li > div > label";
    private static final String allButton = "#root > div > section > footer > ul > li:nth-child(1) > a";
    private static final String activeButton = "#root > div > section > footer > ul > li:nth-child(2) > a";
    private static final String completedButton =  "#root > div > section > footer > ul > li:nth-child(3) > a"
    private static final String deleteButton = ".destroy";
    private static final String clearCompletedButton = ".clear-completed";
    private static final String classEqualsCompleted = "#root > div > section > ul > li";

    // to add
    private static final String header = "div > header > h1" // check header equals todo
    private static final String itemsLeft = ".todo-count > strong"


    private static WebDriver driver

    // "Functional" objects

    public openPage(WebDriver driver) {
        driver.get(URL)
        this.driver = driver
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(rootSelector)));
    }

    public void addItem(String task) {
        WebElement testInput = driver.findElement(By.cssSelector(toDoInputSelector));
        testInput.sendKeys(task);
        testInput.sendKeys(Keys.ENTER);
    }

    public void assertClassCompleted() {
        //classEqualsCompleted
        assert driver.findElement(By.cssSelector(classEqualsCompleted)).getAttribute("class") == "completed";

    }

    // I know this is filthy...
    // need to find something that works like Hamcrest 'assert (has item())'
    public void assertItemOnList(String task) {
        // this code works b/c the last added item stays at the top of the list
        // to do item label
        int ticker = 0
        List<WebElement> allItems = driver.findElements(By.cssSelector(toDoItemLabel));
        for (WebElement i in allItems)
        { if (i.getText() == task)
            {ticker += 1}
        }
        assert ticker > 0
    }

    // Deprecated - clickActive, assert not on list
    /*
    public void assertItemNotActive(String task) {
        // Make sure it is NOT on the 'Active' List - this code works b/c the last added item stays at the top of the list
        // active button
        driver.findElement(By.cssSelector((activeButton))).click();
        // to do item label
        List<WebElement> activeItems = driver.findElements(By.cssSelector(toDoItemLabel));
        assert activeItems[0].getText() != task;
    }
*/

    // refactored this function to work on any list, of any size, instead of being focused on a single item
    public void assertItemNotOnList(String task) {
        // this code works b/c the last added item stays at the top of the list
        // to do item label
        List<WebElement> allItems = driver.findElements(By.cssSelector(fullList));
        for (WebElement i in allItems)
        assert i.getText() != task;
    }

// deprecated - use 'assertItemOnList()'
    /*
    public void checkToDoListForItem(String task) {
        // to do item label
        List<WebElement> todoItems = driver.findElements(By.cssSelector(toDoItemLabel));
        assertTrue(todoItems[0].getText() == task);
    }
*/
    public void clickActiveButton() {
        // all button
        driver.findElement(By.cssSelector((activeButton))).click();
    }

    public void clickAllButton() {
        // all button
        driver.findElement(By.cssSelector((allButton))).click();
    }

    public void clickCompletedButton() {
        //completed button
        driver.findElement(By.cssSelector(completedButton)).click();

    }

    // deprecated
    /*
    public void clickCompletedAndAssert() {
        // click 'Completed', make sure item is marked 'Completed'
        //completed button
        driver.findElement(By.cssSelector(completedButton)).click();
        //classEqualsCompleted
        assert driver.findElement(By.cssSelector(classEqualsCompleted)).getAttribute("class") == "completed";

    }
*/

    public void completeItem(int taskNumber) {
        driver.findElement(By.cssSelector("#root > div > section > ul > li:nth-child(" + (taskNumber + 1) + ")> div > input")).click();
    }



    public void deleteItem(int taskNumber) {
        // Performs a 'hover' to make hidden element visible, then a 'click' on said element. Taken from a SO post and modified. Make notes on this later.
        Actions action = new Actions(driver);
        //to do item label
        WebElement we = driver.findElement(By.cssSelector("#root > div > section > ul > li:nth-child(" + (taskNumber + 1) + ")"));
        action.moveToElement(we).build().perform();
        //delete button
        action.moveToElement(driver.findElement(By.cssSelector(".destroy"))).click().build().perform();
    }


    public void clearCompleted() {
        // clear completed button
        driver.findElement(By.cssSelector((clearCompletedButton))).click();
    }

    public List<WebElement> getAllItems(){
        List<WebElement> allItems = driver.findElements(By.cssSelector(fullList))
        return allItems
    }
}
