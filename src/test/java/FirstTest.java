import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/nikitaprokopenko/repo/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        System.out.println("First test");
    }

    @Test
    public void checkSearchHasText() {
        assertElementHasText(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']"),
                "Can't find search input",
                "Search Wikipedia",
                5
        );
    }

    @Test
    public void checkSearchFindSeveralAnswers() {
        searchText(
                By.id("org.wikipedia:id/search_container"),
                "Cant't find search input",
                "Java",
                5
        );
        int countOfSearchResults = findCountOfSearchResults();
        Assert.assertTrue("Search found less than 2 results", countOfSearchResults > 2);
        clickOnElement(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find close button",
                5
        );
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id("org.wikipedia:id/page_list_item_container"))
        );
    }

    @Test
    public void checkSearchResultsHaveKeyWord() {
        String searchMessage = "Java";
        searchText(
                By.id("org.wikipedia:id/search_container"),
                "Cant't find search input",
                searchMessage,
                5
        );
        waitForElementPresented(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Search result is empty",
                5
        );
        List<WebElement> elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        for (WebElement element : elements) {
            Assert.assertTrue("Result doesn't containt search message", element.getText().contains(searchMessage));
        }

    }

    @Test
    public void checkArticleSaving() {
        String searchMessage = "Java";
        searchText(
                By.id("org.wikipedia:id/search_container"),
                "Cant't find search input",
                searchMessage,
                5
        );
        waitForElementPresented(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Search result is empty",
                5
        );
        clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
                "Can't click on article",
                5
        );
        saveToReadingList(
                By.xpath("//*[@text='Java']")
        );
        searchText(
                By.id("org.wikipedia:id/search_container"),
                "Cant't find search input",
                searchMessage,
                5
        );
        waitForElementPresented(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Search result is empty",
                5
        );
        clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='JavaScript']"),
                "Can't click on article",
                5
        );
        saveToExistingReadingList(
                By.xpath("//*[@text='JavaScript']")
        );
        openReadingList();
        deleteFromReadingListBySwipe("JavaScript");
        checkArticleExistInReadingList("Java");
    }

    private WebElement waitForElementPresented(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private void assertElementHasText(By by, String errorMessage, String expectedText, long timeoutInSeconds) {
        WebElement element = waitForElementPresented(
                by,
                errorMessage,
                timeoutInSeconds
        );
        String elementText = element.getAttribute("text");
        Assert.assertEquals(
                expectedText,
                elementText
        );
    }

    private WebElement searchText(By by, String errorMessage, String searchMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresented(
                by,
                errorMessage,
                timeoutInSeconds
        );
        element.click();
        element.sendKeys(searchMessage);
        return element;
    }

    private int findCountOfSearchResults() {
        waitForElementPresented(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Search result is empty",
                5
        );
        List<WebElement> elements = driver.findElements(By.id("org.wikipedia:id/page_list_item_container"));
        int elementsCount = elements.size();
        return elementsCount;
    }

    private WebElement clickOnElement(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresented(
                by,
                errorMessage,
                timeoutInSeconds
        );
        element.click();
        return element;
    }

    private void closeArticle() {
        clickOnElement(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Can't find close button",
                5
        );
    }

    private void saveToReadingList(By by) {
        waitForElementPresented(
                by,
                "Article isn't loaded",
                5);
        clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']//*[@content-desc='More options']"),
                "Can't find more options button",
                5
        );
        clickOnElement(
                By.xpath("//*[@instance=2][@text='Add to reading list']"),
                "Can't click to 'Add to reading list'",
                5
        );
        clickOnElement(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can't click to 'GOT IT' button",
                5
        );
        createTestReadingList();
        closeArticle();
    }

    private void saveToExistingReadingList(By by) {
        waitForElementPresented(
                by,
                "Article isn't loaded",
                5);
        clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_toolbar']//*[@content-desc='More options']"),
                "Can't find more options button",
                5
        );
        clickOnElement(
                By.xpath("//*[@instance=2][@text='Add to reading list']"),
                "Can't click to 'Add to reading list'",
                5
        );
        clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_container']"),
                "Can't click to existing reading list",
                5
        );
        closeArticle();
    }

    private void createTestReadingList() {
        waitForElementPresented(
                By.id("org.wikipedia:id/text_input"),
                "Can't find input for creating title for reading list",
                5
        );

        clickOnElement(
                By.id("android:id/button1"),
                "Can't press OK button for creating reading list",
                5
        );
    }

    private void openReadingList() {
        clickOnElement(
                By.xpath("//*[@content-desc='My lists']"),
                "Can't open Reading list page",
                5
        );
        clickOnElement(
                By.xpath("//*[@text='My reading list']"),
                "Can't open saved Reading list",
                5
        );
    }

    private void deleteFromReadingListBySwipe(String articleTitle) {
        WebElement element = waitForElementPresented(
                By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text=\'%s\']", articleTitle)),
                "Can't find article in Reading list",
                5);

        int y = element.getLocation().getY();
        swipeLeft(3000, y);
    }

    private void swipeLeft(int timeOfSwipe, int y) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int startX = (int ) (size.width * 0.8);
        int endX = (int) (size.width * 0.2);
        action.press(startX, y).waitAction(timeOfSwipe).moveTo(endX, y).release().perform();
    }

    private void checkArticleExistInReadingList(String articleTitle) {
        WebElement element = waitForElementPresented(
                By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text=\'%s\']", articleTitle)),
                "Can't find article in Reading list",
                5);
        element.click();
        waitForElementPresented(
                By.xpath(String.format("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text=\'%s\']", articleTitle)),
                "Article title doesn't equal title in Reading list",
                5);
    }
}
