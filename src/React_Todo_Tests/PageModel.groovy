package React_Todo_Tests

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import static org.junit.Assert.assertTrue // why is this 'import static' ??


class PageModel {


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

    public void assertItemOnList(String task) {
        // Make sure the item is on the list
        List<WebElement> Items = driver.findElements(By.cssSelector(toDoItemLabel));
        assert Items[0].getText() == task;
    }

    public void assertItemNotActive(String task) {
        // Make sure it is NOT on the 'Active' List - this code works b/c the last added item stays at the top of the list
        // active button
        driver.findElement(By.cssSelector((activeButton))).click();
        // to do item label
        List<WebElement> activeItems = driver.findElements(By.cssSelector(toDoItemLabel));
        assert activeItems[0].getText() != task;
    }

    public void checkToDoListForItem(String task) {
        // to do item label
        List<WebElement> todoItems = driver.findElements(By.cssSelector(toDoItemLabel));
        assertTrue(todoItems[0].getText() == task);
    }

    public void completeItem() {
        List<WebElement> checkboxEls = driver.findElements(By.cssSelector(toDoCheckboxSelector));
        checkboxEls[0].click();
    }

    public void clickCompletedAndAssert() {
        // click 'Completed', make sure item is marked 'Completed'
        //completed button
        driver.findElement(By.cssSelector((completedButton))).click();
        //classEqualsCompleted
        assert driver.findElement(By.cssSelector(classEqualsCompleted)).getAttribute("class") == "completed";

    }

    public void deleteItem() {
        // Performs a 'hover' to make hidden element visible, then a 'click' on said element. Taken from a SO post and modified. Make notes on this later.
        Actions action = new Actions(driver);
        //to do item label
        WebElement we = driver.findElement(By.cssSelector(toDoItemLabel));
        action.moveToElement(we).build().perform();
        //delete button
        action.moveToElement(driver.findElement(By.cssSelector(deleteButton))).click().build().perform();
    }

    public void deletedItemNotOnAll(String task) {
        // Make sure it is NOT on the 'All' List - this code works b/c the last added item stays at the top of the list
        // all button
        driver.findElement(By.cssSelector((allButton))).click();
        // to do item label
        List<WebElement> allItems = driver.findElements(By.cssSelector(toDoItemLabel));
        assert allItems[0].getText() != task;
    }


    public void clearCompleted() {
        // clear completed button
        driver.findElement(By.cssSelector((clearCompletedButton))).click();
    }
}
