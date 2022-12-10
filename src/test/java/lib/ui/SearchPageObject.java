package lib.ui;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_container']//*[@class='android.widget.TextView']",
            SEARCH_INPUT_TEXT = "Search Wikipedia",
            SEARCH_LIST_ITEM = "org.wikipedia:id/page_list_item_container",
            SEARCH_CLOSE_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_LIST_ITEM_TITLE = "org.wikipedia:id/page_list_item_title";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.assertElementHasText(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Can't find search input",
                SEARCH_INPUT_TEXT,
                5
        );
    }

    public void searchText(String text) {
        WebElement element = this.waitForElementPresented(
                By.xpath(SEARCH_INIT_ELEMENT),
                "Cant't find search input",
                5
        );
        element.click();
        element.sendKeys(text);
    }

    public int findCountOfSearchResults() {
        this.waitForElementPresented(
                By.id(SEARCH_LIST_ITEM),
                "Search result is empty",
                5
        );
        List<WebElement> elements = driver.findElements(By.id(SEARCH_LIST_ITEM));
        int elementsCount = elements.size();
        return elementsCount;
    }

    public void clickOnSearchCloseButton() {
        this.clickOnElement(
                By.id(SEARCH_CLOSE_BUTTON),
                "Can't find close button",
                5
        );
    }

    public void checkSearchResultIsEmpty() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id(SEARCH_LIST_ITEM))
        );
    }

    public void checkSearchResultIsNotEmpty() {
        this.waitForElementPresented(
                By.id(SEARCH_LIST_ITEM_TITLE),
                "Search result is empty",
                5
        );
    }

    public List<WebElement> findAllTitlesInSearchResult() {
        checkSearchResultIsNotEmpty();
        List<WebElement> elements = driver.findElements(By.id(SEARCH_LIST_ITEM_TITLE));
        return elements;
    }

    public void openArticleWithTitle(String title) {
        this.clickOnElement(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Java']"),
                "Can't click on article",
                5
        );
    }
}

