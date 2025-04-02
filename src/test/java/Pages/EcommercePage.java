package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utilities.BaseDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Page Object Model for e-commerce website functionality.
 * Contains all web elements and methods related to browsing and shopping cart operations.
 */
public class EcommercePage extends Parent {
    
    public EcommercePage() {
        PageFactory.initElements(BaseDriver.getDriver(), this);
    }

    // Cookie consent elements
    @FindBy(id = "cookieConsent")
    private WebElement cookieConsent;
    
    @FindBy(id = "acceptCookies")
    private WebElement acceptCookiesButton;

    // Category navigation elements
    @FindBy(css = ".category-menu")
    private WebElement categoryMenu;
    
    @FindBy(css = ".category-item")
    private List<WebElement> categoryItems;

    // Product listing elements
    @FindBy(css = ".product-item")
    private List<WebElement> productItems;
    
    @FindBy(css = ".product-price")
    private List<WebElement> productPrices;
    
    @FindBy(css = ".add-to-cart-button")
    private List<WebElement> addToCartButtons;

    // Shopping cart elements
    @FindBy(id = "cartIcon")
    private WebElement cartIcon;
    
    @FindBy(css = ".cart-item")
    private List<WebElement> cartItems;
    
    @FindBy(css = ".item-price")
    private List<WebElement> cartItemPrices;
    
    @FindBy(css = ".item-quantity")
    private List<WebElement> cartItemQuantities;
    
    @FindBy(css = ".item-subtotal")
    private List<WebElement> cartItemSubtotals;
    
    @FindBy(id = "cartTotal")
    private WebElement cartTotal;
    
    @FindBy(id = "shippingCost")
    private WebElement shippingCost;
    
    @FindBy(id = "taxAmount")
    private WebElement taxAmount;
    
    @FindBy(id = "finalTotal")
    private WebElement finalTotal;

    // List to store noted prices
    private List<Double> notedPrices = new ArrayList<>();

    /**
     * Accepts cookies if the consent dialog is present
     */
    public void acceptCookies() {
        try {
            if (cookieConsent.isDisplayed()) {
                clickFunction(acceptCookiesButton);
            }
        } catch (Exception e) {
            // Cookie consent not present, continue
        }
    }

    /**
     * Navigates to a specific category
     * @param categoryName Name of the category to navigate to
     */
    public void navigateToCategory(String categoryName) {
        waitUntilVisible(categoryMenu);
        for (WebElement category : categoryItems) {
            if (category.getText().equals(categoryName)) {
                clickFunction(category);
                break;
            }
        }
    }

    /**
     * Adds random items from current category to cart
     * @param count Number of items to add
     */
    public void addRandomItemsToCart(int count) {
        waitUntilVisible(productItems.get(0));
        Random random = new Random();
        
        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(productItems.size());
            WebElement item = productItems.get(randomIndex);
            WebElement price = productPrices.get(randomIndex);
            WebElement addButton = addToCartButtons.get(randomIndex);
            
            // Note the price
            double itemPrice = Double.parseDouble(price.getText().replace("$", ""));
            notedPrices.add(itemPrice);
            
            // Add to cart
            scrollToElement(addButton);
            clickFunction(addButton);
            
            // Wait for add to cart confirmation
            Parent.delay(1);
        }
    }

    /**
     * Navigates to shopping cart
     */
    public void navigateToCart() {
        clickFunction(cartIcon);
    }

    /**
     * Verifies the number of items in cart
     * @param expectedCount Expected number of items
     */
    public void verifyCartItemCount(int expectedCount) {
        waitUntilVisible(cartItems.get(0));
        Assert.assertEquals(cartItems.size(), expectedCount, 
            "Cart item count doesn't match expected value");
    }

    /**
     * Verifies that cart total matches sum of noted prices
     */
    public void verifyCartTotal() {
        double expectedTotal = notedPrices.stream().mapToDouble(Double::doubleValue).sum();
        double actualTotal = Double.parseDouble(cartTotal.getText().replace("$", ""));
        
        Assert.assertEquals(actualTotal, expectedTotal, 0.01,
            "Cart total doesn't match sum of noted prices");
    }

    /**
     * Verifies that each item's quantity is 1
     */
    public void verifyItemQuantities() {
        for (WebElement quantity : cartItemQuantities) {
            Assert.assertEquals(quantity.getText(), "1",
                "Item quantity is not 1");
        }
    }

    /**
     * Verifies that each item's subtotal matches its price
     */
    public void verifyItemSubtotals() {
        for (int i = 0; i < cartItems.size(); i++) {
            double itemPrice = Double.parseDouble(cartItemPrices.get(i).getText().replace("$", ""));
            double subtotal = Double.parseDouble(cartItemSubtotals.get(i).getText().replace("$", ""));
            
            Assert.assertEquals(subtotal, itemPrice, 0.01,
                "Item subtotal doesn't match its price");
        }
    }

    /**
     * Verifies shipping cost calculation
     */
    public void verifyShippingCost() {
        double shipping = Double.parseDouble(shippingCost.getText().replace("$", ""));
        Assert.assertTrue(shipping >= 0, "Shipping cost should be non-negative");
    }

    /**
     * Verifies tax calculation
     */
    public void verifyTaxAmount() {
        double tax = Double.parseDouble(taxAmount.getText().replace("$", ""));
        double subtotal = Double.parseDouble(cartTotal.getText().replace("$", ""));
        
        // Assuming 8% tax rate
        double expectedTax = subtotal * 0.08;
        Assert.assertEquals(tax, expectedTax, 0.01,
            "Tax amount is not calculated correctly");
    }

    /**
     * Verifies final total calculation
     */
    public void verifyFinalTotal() {
        double subtotal = Double.parseDouble(cartTotal.getText().replace("$", ""));
        double shipping = Double.parseDouble(shippingCost.getText().replace("$", ""));
        double tax = Double.parseDouble(taxAmount.getText().replace("$", ""));
        double finalTotal = Double.parseDouble(this.finalTotal.getText().replace("$", ""));
        
        double expectedTotal = subtotal + shipping + tax;
        Assert.assertEquals(finalTotal, expectedTotal, 0.01,
            "Final total doesn't include all components correctly");
    }

    /**
     * Cleans up the WebDriver instance
     */
    public void cleanupDriver() {
        Parent.delay(5);
        BaseDriver.quitDriver();
    }
} 