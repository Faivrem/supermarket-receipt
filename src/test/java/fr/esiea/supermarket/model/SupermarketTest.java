package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.offers.SpecialOfferType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SupermarketTest {

    /*
    combien coute 2.5 kg de pommes à 1.99€
    compte tenu du fait qu'une réduction est en cours sur les brosses à dents.
     */

    @Test
    public void testSomething() {

        // Initialisation du catalogue
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        // Caddie
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);

        Teller teller = new Teller(catalog);
        // Ajout de l'offre sur la brosse à dents au catalogue
        teller.addSpecialOffer(SpecialOfferType.XPercentDiscount, toothbrush,10);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 2.5 * 1.99;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("2.5kg de pommes à 1.99€ avec réduction sur les brosses à dents");


    }

    @Test
    public void testTenPercentDiscount() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product rice = new Product("rice",ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(rice);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.XPercentDiscount, rice,10);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 2.49 - (2.49*0.1);
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test Ten Percent Discount");

    }

    @Test
    public void testThreeForTwo() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush,0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 0.99 * 2;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test Three For Two");

    }

    @Test
    public void testTwoForAmount() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product tomatoesBox = new Product("tomatoes Box", ProductUnit.Each);
        catalog.addProduct(tomatoesBox,0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(tomatoesBox, 2.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, tomatoesBox,0.99);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 0.99;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test Two For Amount");

    }

    @Test
    public void testFiveForAmount() {

        SupermarketCatalog catalog = new FakeCatalog();
        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,1.79);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 5.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste,7.49);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 7.49;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test Five For Amount");

    }

    @Test
    public void testNoDiscount() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3.0);

        Teller teller = new Teller(catalog);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 0.99 * 3;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test No discount");

    }

    @Test
    public void testDiscountsNotUsed() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,1.79);

        Product rice = new Product("rice",ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Product tomatoesBox = new Product("tomatoes Box", ProductUnit.Each);
        catalog.addProduct(tomatoesBox,0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 3.0);
        cart.addItemQuantity(toothbrush, 1.0);
        cart.addItemQuantity(tomatoesBox, 1.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.XPercentDiscount, rice,10);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush,0);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, tomatoesBox,0.99);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothpaste,7.49);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 7.3500000000000005;
        double totalPrice = receipt.getTotalPrice();
        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Create all offers but any is used");

    }

    @Test
    public void testBundleOffer() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,1.79);

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 1.0);
        cart.addItemQuantity(toothbrush, 1.0);

        // On met produits dans l'offre groupée
        Map<Product,Double> bundleProductQuantities = new HashMap<>();
        bundleProductQuantities.put(toothpaste,1.0);
        bundleProductQuantities.put(toothbrush,1.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialBundleOffer(SpecialOfferType.BundlePercentDiscount,bundleProductQuantities, 10);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = (1.79 + 0.99) - (1.79 + 0.99) * 0.1;
        //expectedTotalPrice = 2*(1.79 + 0.99) - 2*((1.79 + 0.99) * 0.1);

        double totalPrice = receipt.getTotalPrice();
        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test of bundle offers");
    }


}
