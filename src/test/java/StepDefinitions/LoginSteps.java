package StepDefinitions;

import Pages.DialogueContent;
import Pages.Parent;
import Utilities.ExcelUtility;
import Utilities.BaseDriver;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

import java.util.List;

public class LoginSteps {
    DialogueContent dialogueContent = new DialogueContent();
    List<List<String>> loginData;

    @Given("User navigates to the login page")
    public void userNavigatesToTheLoginPage() {
        // Navigate to login page URL
        BaseDriver.getDriver().get("https://opensource-demo.orangehrmlive.com");
    }

    @When("User enters username and password from Excel")
    public void userEntersUsernameAndPasswordFromExcel() {
        // Read valid credentials from Excel
        loginData = ExcelUtility.getListData("src/test/resources/testData.xls", "LoginData", 2);
        dialogueContent.findAndSend("usernameInput",loginData.get(0).get(0));
        dialogueContent.findAndSend("passwordInput",loginData.get(0).get(1));
    }

    @When("User enters invalid username and password from Excel")
    public void userEntersInvalidUsernameAndPasswordFromExcel() {
        // Read invalid credentials from Excel
        loginData = ExcelUtility.getListData("src/test/resources/testData.xls", "LoginData", 2);
//        dialogueContent.findAndSend("usernameInput", loginData.get(1).get(0));
        dialogueContent.findAndSend("usernameInput", "Admin");
        dialogueContent.findAndSend("usernameInput", "admin12");
    }

    @When("User clicks on login button")
    public void userClicksOnLoginButton() {
        dialogueContent.findAndClick("loginButton");
    }

    @Then("User should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        Parent.waitUntilVisible(dialogueContent.dashboardText);
        Assert.assertEquals(dialogueContent.dashboardText.getText(), "Dashboard");
    }

    @Then("User should see error message")
    public void userShouldSeeErrorMessage() {
        dialogueContent.verifyErrorMessage("Invalid credentials");
    }

    @After
    public void cleanup() {
        dialogueContent.cleanupDriver();
    }
} 