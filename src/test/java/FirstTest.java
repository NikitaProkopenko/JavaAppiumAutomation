import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
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
        clearSearchResult(
                By.id("org.wikipedia:id/search_close_btn"),
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

    private WebElement clearSearchResult(By by, long timeoutInSeconds) {
        WebElement element = waitForElementPresented(
                by,
                "Can't find close button",
                timeoutInSeconds
        );
        element.click();
        return element;
    }
}
