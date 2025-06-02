package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.BaseDriver;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * Page Object Model class for login dialogue elements.
 * Contains all the web elements and methods related to the login functionality.
 */
public class DialogueContent extends Parent {
    
    public DialogueContent() {
        PageFactory.initElements(BaseDriver.getDriver(), this);
    }

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(xpath = "//h6[text()='Dashboard']")
    public WebElement dashboardText;

    @FindBy(xpath = "//p[text()='Invalid credentials']")
    private WebElement errorMessage;

    @FindBy(id = "successMessage")
    private WebElement successMessage;

    WebElement myElement;
    public void findAndSend(String elementName,String value)
    {
        switch (elementName) {
            case "username":
                myElement = usernameInput;
                break;

            case "password":
                myElement = passwordInput;
                break;

        }

        sendKeysFunction(myElement, value);
    }

    public void findAndClick(String elementName)
    {
        switch (elementName) {
            case "loginButton":
                myElement = loginButton;
                break;
        }
        clickFunction(myElement);
    }

    public void findAndContainsText(String elementName, String msg)
    {
        switch (elementName) {
            case "successMessage":
                myElement = successMessage;
                break;

            case "errorMessage":
                myElement = errorMessage;
                break;
        }

        verifyContainsText(myElement, msg);
    }


    public void findAndDelete(String deleteString)
    {
        findAndSend("searchInput", deleteString);
        findAndClick("searchButton");
       
        waitnumberOfElementsToBeLessThan(By.xpath("//ms-delete-button//button"), 5);

        findAndClick("deleteButton");
        findAndClick("deleteDialogBtn");
    }

    List<WebElement> myList;
    public void ChooseListElement(String listName, String option)
    {
        switch (listName) {
            case "userTypeAllOptions":
                break;
        }

        listSelectOption(myList, option);
    }

    public void findAndEdit(String oldWord, String newWord)
    {
        findAndSend("searchInput", oldWord);
        findAndClick("searchButton");
        
        waitnumberOfElementsToBeLessThan(By.xpath("//ms-delete-button//button"), 5);

        findAndClick("editBtn");

        findAndSend("nameInput", newWord);
        findAndClick("saveButton");
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
