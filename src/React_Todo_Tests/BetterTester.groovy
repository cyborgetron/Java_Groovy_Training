package React_Todo_Tests

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class BetterTester {

    private static final String pathToDriver = "/Users/chrisborgert/Callaway/Github/Training/Java_Groovy_Training/src/chromedriver";
    private WebDriver chrome

    // * below strings must be distinct *
    private static final testTaskString = "test task"
    private static final testTaskString1 = "another test task"

    // ---------- Tests ----------- //

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        chrome = new ChromeDriver();
    }

    @After
    public void closeDownBrowser() {
        chrome.quit();
    }

    @Test
    public void getPageTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
    }

    @Test
    public void checkItemTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.checkToDoListForItem(testTaskString);
    }

    @Test
    public void completeTodoTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.completeItem();
        pageModel.clickCompletedAndAssert();
        pageModel.assertItemOnList(testTaskString);
        pageModel.assertItemNotActive(testTaskString);
    }

    //sometimes this doesn't run... doesn't fail, but gives a yellow 'circle x'
    @Test
    public void deleteActiveTodoTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.deleteItem();
        pageModel.deletedItemNotOnAll(testTaskString);
    }

    // occasionally this will run and give me a yellow 'x'; not sure what that's about
    @Test
    public void deleteCompletedTodoTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.completeItem();
        pageModel.deleteItem();
        pageModel.deletedItemNotOnAll(testTaskString);
    }

    @Test
    public void clearCompletedTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.completeItem();
        pageModel.addItem(testTaskString1)
        pageModel.clickCompletedAndAssert();
        pageModel.assertItemOnList(testTaskString);
        pageModel.clearCompleted();
        pageModel.deletedItemNotOnAll(testTaskString);
        pageModel.assertItemOnList(testTaskString1);
    }
}
