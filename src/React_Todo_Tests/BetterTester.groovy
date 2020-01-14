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
        pageModel.assertItemOnList(testTaskString);
    }

    @Test
    public void completeTodoTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        // size should be (1) item, before adding new item
        assert pageModel.getAllItems().size() == 1

        pageModel.addItem(testTaskString);
        // size should now be (2) items
        assert pageModel.getAllItems().size() == 2

        pageModel.completeItem(0);
        pageModel.clickCompletedButton()
        pageModel.assertItemOnList(testTaskString);
        pageModel.clickActiveButton()
        pageModel.assertItemNotOnList(testTaskString);
    }

    //sometimes this doesn't run... doesn't fail, but gives a yellow 'circle x'
    @Test
    public void deleteActiveTodoTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.deleteItem(0);
        pageModel.clickAllButton()
        pageModel.assertItemNotOnList(testTaskString);
    }

    // occasionally this will run and give me a yellow 'x'; not sure what that's about
    @Test
    public void deleteCompletedTodoTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.completeItem(0);
        pageModel.deleteItem(0);
        pageModel.clickAllButton()
        pageModel.assertItemNotOnList(testTaskString);
    }

    @Test
    public void clearCompletedTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);
        pageModel.addItem(testTaskString);
        pageModel.addItem(testTaskString1)

        // complete "testTaskString"
        pageModel.completeItem(1);
        pageModel.clickCompletedButton()

        pageModel.assertItemOnList(testTaskString);
        pageModel.clearCompleted();
        pageModel.clickAllButton()


        pageModel.assertItemNotOnList(testTaskString);
        pageModel.assertItemOnList(testTaskString1);
    }
}
