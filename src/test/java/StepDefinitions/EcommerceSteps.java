package StepDefinitions;

import Pages.EcommercePage;
import Utilities.BaseDriver;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EcommerceSteps {
    EcommercePage ecommercePage = new EcommercePage();

    @Given("User navigates to the e-commerce website")
    public void userNavigatesToTheECommerceWebsite() {
        BaseDriver.getDriver().get("https://your-ecommerce-website.com");
    }

    @And("User accepts cookies if present")
    public void userAcceptsCookiesIfPresent() {
        ecommercePage.acceptCookies();
    }

    @When("User browses through {string} category")
    public void userBrowsesThroughCategory(String categoryName) {
        ecommercePage.navigateToCategory(categoryName);
    }

    @And("User adds {int} random items from {string} to cart")
    public void userAddsRandomItemsFromCategoryToCart(int count, String category) {
        ecommercePage.addRandomItemsToCart(count);
    }

    @And("User notes down the prices of added {string} items")
    public void userNotesDownThePricesOfAddedItems(String category) {
        // Prices are automatically noted in the addRandomItemsToCart method
    }

    @When("User navigates to shopping cart")
    public void userNavigatesToShoppingCart() {
        ecommercePage.navigateToCart();
    }

    @Then("User should see all {int} items in the cart")
    public void userShouldSeeAllItemsInTheCart(int expectedCount) {
        ecommercePage.verifyCartItemCount(expectedCount);
    }

    @And("User should verify that the cart total matches the sum of all noted prices")
    public void userShouldVerifyThatTheCartTotalMatchesTheSumOfAllNotedPrices() {
        ecommercePage.verifyCartTotal();
    }

    @And("User should verify that each item's quantity is {int}")
    public void userShouldVerifyThatEachItemSQuantityIs(int quantity) {
        ecommercePage.verifyItemQuantities();
    }

    @And("User should verify that each item's subtotal matches its price")
    public void userShouldVerifyThatEachItemSSubtotalMatchesItsPrice() {
        ecommercePage.verifyItemSubtotals();
    }

    @And("User should verify that shipping cost is calculated correctly")
    public void userShouldVerifyThatShippingCostIsCalculatedCorrectly() {
        ecommercePage.verifyShippingCost();
    }

    @And("User should verify that tax is calculated correctly")
    public void userShouldVerifyThatTaxIsCalculatedCorrectly() {
        ecommercePage.verifyTaxAmount();
    }

    @And("User should verify that the final total includes all items, shipping, and tax")
    public void userShouldVerifyThatTheFinalTotalIncludesAllItemsShippingAndTax() {
        ecommercePage.verifyFinalTotal();
    }

    @After
    public void cleanup() {
        ecommercePage.cleanupDriver();
    }
} 