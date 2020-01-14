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

        assert pageModel.getHeader().getText() == "todos"

        // insures "items left" is working
        assert pageModel.getItemsLeft().getText() == "1"

        // This seems useful and I wanted to try it
        pageModel.completeItem(0)
        // gets the text of the task
        assert pageModel.getOneItem(0).getText() == "Use Redux"
        // gets the class - "view" is active, "completed" is complete
        assert pageModel.getOneItem(0).getAttribute("class") == "completed"
    }

    @Test
    public void checkItemTest() {
        PageModel pageModel = new PageModel()
        pageModel.openPage(chrome);
        assert pageModel.getAllItems().size() == 1
        pageModel.addItem(testTaskString);
        assert pageModel.getAllItems().size() == 2
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

        assert pageModel.getAllItems().size() == 1
        pageModel.addItem(testTaskString);
        assert pageModel.getAllItems().size() == 2

        pageModel.deleteItem(0);
        pageModel.clickAllButton()
        pageModel.assertItemNotOnList(testTaskString);
        assert pageModel.getAllItems().size() == 1
    }


    @Test
    public void deleteCompletedTodoTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);

        assert pageModel.getAllItems().size() == 1
        pageModel.addItem(testTaskString);
        assert pageModel.getAllItems().size() == 2

        pageModel.completeItem(0);
        pageModel.deleteItem(0);
        pageModel.clickAllButton()
        pageModel.assertItemNotOnList(testTaskString);
    }

    @Test
    public void clearCompletedTest() {
        PageModel pageModel = new PageModel();
        pageModel.openPage(chrome);

        assert pageModel.getAllItems().size == 1
        pageModel.addItem(testTaskString);
        pageModel.addItem(testTaskString1)
        assert pageModel.getAllItems().size() == 3

        // complete "testTaskString" todo
        pageModel.completeItem(1);
        pageModel.clickCompletedButton()
        assert pageModel.getAllItems().size() == 1

        pageModel.assertItemOnList(testTaskString);
        pageModel.clearCompleted();
        pageModel.clickAllButton()

        pageModel.assertItemNotOnList(testTaskString);
        pageModel.assertItemOnList(testTaskString1);
    }
}
