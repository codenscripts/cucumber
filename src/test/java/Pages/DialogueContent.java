package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.BaseDriver;
import org.openqa.selenium.WebDriver;

/**
 * Page Object Model class for login dialogue elements.
 * Contains all the web elements and methods related to the login functionality.
 */
public class DialogueContent extends Parent {
    
    public DialogueContent() {
        PageFactory.initElements(BaseDriver.getDriver(), this);
    }

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "errorMessage")
    private WebElement errorMessage;

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    /**
     * Enters username into the username input field
     * @param username The username to enter
     */
    public void enterUsername(String username) {
        waitUntilVisible(usernameInput);
        scrollToElement(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    /**
     * Enters password into the password input field
     * @param password The password to enter
     */
    public void enterPassword(String password) {
        waitUntilVisible(passwordInput);
        scrollToElement(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Clicks the login button
     */
    public void clickLoginButton() {
        waitUntilClickable(loginButton);
        scrollToElement(loginButton);
        loginButton.click();
    }

    /**
     * Verifies if error message is displayed
     * @param expectedMessage The expected error message
     */
    public void verifyErrorMessage(String expectedMessage) {
        waitUntilVisible(errorMessage);
        verifyContainsText(errorMessage, expectedMessage);
    }

    /**
     * Verifies if login was successful
     * @param expectedMessage The expected success message
     */
    public void verifyLoginSuccess(String expectedMessage) {
        waitUntilVisible(successMessage);
        verifyContainsText(successMessage, expectedMessage);
    }

    /**
     * Cleans up the WebDriver instance
     */
    public void cleanupDriver() {
        Parent.delay(5);
        BaseDriver.quitDriver();
    }
} 