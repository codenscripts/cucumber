package Pages;

import Utilities.BaseDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

/**
 * Parent class provides common WebDriver operations and utility methods.
 * This class serves as a base for all page objects and contains reusable methods
 * for interacting with web elements.
 */
public class Parent {
    
    /**
     * Sends keys to a web element after ensuring it's visible and scrollable.
     * Includes clearing the element before sending new keys.
     *
     * @param element WebElement to interact with
     * @param value Text to send to the element
     */
    public void sendKeysFunction(WebElement element, String value) {
        waitUntilVisible(element);
        scrollToElement(element);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Clicks on a web element after ensuring it's clickable and scrollable.
     *
     * @param element WebElement to click
     */
    public void clickFunction(WebElement element) {
        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }

    /**
     * Waits until a web element becomes visible on the page.
     * Uses WebDriverWait with a 10-second timeout.
     *
     * @param element WebElement to wait for
     */
    public static void waitUntilVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Scrolls the page until the specified element is in view.
     * Uses JavaScript executor for smooth scrolling.
     *
     * @param element WebElement to scroll to
     */
    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) BaseDriver.getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Scrolls the page to bring the element to the top of the viewport.
     * Uses JavaScript executor for precise positioning.
     *
     * @param element WebElement to scroll to top
     */
    public void scrollToUpElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) BaseDriver.getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'top:0px')", element);
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    /**
     * Waits until a web element becomes clickable.
     * Uses WebDriverWait with a 10-second timeout.
     *
     * @param element WebElement to wait for
     */
    public void waitUntilClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until all elements in a list are visible.
     * Returns the list of elements once they are all visible.
     *
     * @param elementList List of WebElements to wait for
     * @return List of visible WebElements
     */
    public List<WebElement> waitVisibleListAllElement(List<WebElement> elementList) {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
        return elementList;
    }

    /**
     * Pauses the execution for the specified number of seconds.
     * Should be used sparingly and only when necessary.
     *
     * @param second Number of seconds to pause
     */
    public static void delay(int second) {
        try {
            Thread.sleep(1000L * second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread sleep interrupted: " + e.getMessage());
        }
    }

    /**
     * Verifies that an element's text contains the expected message.
     * Case-insensitive comparison.
     *
     * @param element WebElement to check
     * @param msg Expected text to find
     */
    public void verifyContainsText(WebElement element, String msg) {
        waitUntilVisible(element);
        Assert.assertTrue(
            element.getText().toLowerCase().contains(msg.toLowerCase()),
            "Element text does not contain expected message: " + msg
        );
    }

    /**
     * Waits until the number of elements matching the selector is less than the specified number.
     * Uses WebDriverWait with a 20-second timeout.
     *
     * @param selector By selector to find elements
     * @param number Maximum number of elements to wait for
     */
    public void waitnumberOfElementsToBeLessThan(By selector, int number) {
        WebDriverWait wait = new WebDriverWait(BaseDriver.getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(selector, number));
    }

    /**
     * Selects an option from a list of elements based on the option text.
     * Clicks the first matching element found.
     *
     * @param list List of WebElements to search through
     * @param option Text to match for selection
     */
    public void listSelectOption(List<WebElement> list, String option) {
        for (WebElement element : list) {
            if (element.getText().contains(option)) {
                element.click();
                break;
            }
        }
    }

    /**
     * Simulates pressing the ESC key using Robot class.
     * Can be used to close popups or dismiss dialogs.
     */
    public void ESCClick() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
        } catch (AWTException e) {
            System.err.println("Failed to simulate ESC key press: " + e.getMessage());
            // Fallback to Actions class if Robot fails
            Actions actions = new Actions(BaseDriver.getDriver());
            actions.sendKeys(Keys.ESCAPE).perform();
        }
    }
} 