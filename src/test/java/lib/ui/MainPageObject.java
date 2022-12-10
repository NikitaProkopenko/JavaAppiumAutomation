package lib.ui;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElementPresented(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public void assertElementHasText(By by, String errorMessage, String expectedText, long timeoutInSeconds) {
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

    public WebElement clickOnElement(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresented(
                by,
                errorMessage,
                timeoutInSeconds
        );
        element.click();
        return element;
    }
}
