package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class ArticlePageObject extends MainPageObject {
    private static final String
        ARTICLE_MORE_OPTION_MENU = "//*[@resource-id='org.wikipedia:id/page_toolbar']//*[@content-desc='More options']",
        ADD_TO_READING_LIST_OPTION_IN_MORE_MENU = "//*[@instance=2][@text='Add to reading list']",
        ONBOARDING_GOT_IT_BUTTON = "org.wikipedia:id/onboarding_button",
        READING_LIST_INPUT_IN_ARTICLE_POP_UP = "org.wikipedia:id/text_input",
        READING_LIST_POP_UP_OK_BUTTON = "android:id/button1",
        ARTICLE_CLOSE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
        EXISTING_READING_LIST = "//*[@resource-id='org.wikipedia:id/item_container']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public void articleTitleIsPresented(String title) {
        this.waitForElementPresented(
                By.xpath(String.format("[@text=\'%s\']", title)),
                "Article isn't loaded",
                5);
    }

    public void openMoreOptionMenuAndAddToReadingList() {
        this.clickOnElement(
                By.xpath(ARTICLE_MORE_OPTION_MENU),
                "Can't find more options button",
                5
        );
        this.clickOnElement(
                By.xpath(ADD_TO_READING_LIST_OPTION_IN_MORE_MENU),
                "Can't click to 'Add to reading list'",
                5
        );
    }

    public void approveOnboarding() {
        this.clickOnElement(
                By.id(ONBOARDING_GOT_IT_BUTTON),
                "Can't click to 'GOT IT' button",
                5
        );
    }

    public void createTestReadingList() {
        this.waitForElementPresented(
                By.id(READING_LIST_INPUT_IN_ARTICLE_POP_UP),
                "Can't find input for creating title for reading list",
                5
        );

        clickOnElement(
                By.id(READING_LIST_POP_UP_OK_BUTTON),
                "Can't press OK button for creating reading list",
                5
        );
    }

    public void closeArticle() {
        this.clickOnElement(
                By.xpath(ARTICLE_CLOSE_BUTTON),
                "Can't find close button",
                5
        );
    }

    public void chooseExistingReadingList() {
        this.clickOnElement(
                By.xpath(EXISTING_READING_LIST),
                "Can't click to existing reading list",
                5
        );
    }



}
