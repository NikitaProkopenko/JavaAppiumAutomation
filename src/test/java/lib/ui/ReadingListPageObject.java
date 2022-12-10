package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

public class ReadingListPageObject extends MainPageObject {
    private static final String
        MY_LIST_BUTTON = "//*[@content-desc='My lists']",
        READING_LIST = "//*[@text='My reading list']";

    public ReadingListPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openReadingList() {
        this.clickOnElement(
                By.xpath(MY_LIST_BUTTON),
                "Can't open Reading list page",
                5
        );
        this.clickOnElement(
                By.xpath(READING_LIST),
                "Can't open saved Reading list",
                5
        );
    }


    public void deleteFromReadingListBySwipe(String articleTitle) {
        WebElement element = waitForElementPresented(
                By.xpath(String.format("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text=\'%s\']", articleTitle)),
                "Can't find article in Reading list",
                5);

        int y = element.getLocation().getY();
        swipeLeft(3000, y);
    }

    public void swipeLeft(int timeOfSwipe, int y) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int startX = (int ) (size.width * 0.8);
        int endX = (int) (size.width * 0.2);
        action.press(startX, y).waitAction(timeOfSwipe).moveTo(endX, y).release().perform();
    }

    public void checkArticleExistInReadingList(String articleTitle) {
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
