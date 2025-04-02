Feature: E-commerce Shopping Cart Functionality
  As a customer
  I want to browse products, add items to cart, and verify the total amount
  So that I can ensure the shopping cart calculations are correct

  @Ecommerce
  Scenario: Verify shopping cart total with multiple items from different categories
    Given User navigates to the e-commerce website
    And User accepts cookies if present
    When User browses through "Electronics" category
    And User adds 5 random items from Electronics to cart
    And User notes down the prices of added Electronics items
    When User browses through "Clothing" category
    And User adds 5 random items from Clothing to cart
    And User notes down the prices of added Clothing items
    When User browses through "Home & Kitchen" category
    And User adds 5 random items from Home & Kitchen to cart
    And User notes down the prices of added Home & Kitchen items
    When User browses through "Sports & Outdoors" category
    And User adds 5 random items from Sports & Outdoors to cart
    And User notes down the prices of added Sports & Outdoors items
    When User browses through "Books" category
    And User adds 5 random items from Books to cart
    And User notes down the prices of added Books items
    When User navigates to shopping cart
    Then User should see all 25 items in the cart
    And User should verify that the cart total matches the sum of all noted prices
    And User should verify that each item's quantity is 1
    And User should verify that each item's subtotal matches its price
    And User should verify that shipping cost is calculated correctly
    And User should verify that tax is calculated correctly
    And User should verify that the final total includes all items, shipping, and tax 