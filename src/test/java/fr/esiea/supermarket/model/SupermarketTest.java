package fr.esiea.supermarket.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush,10);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 2.5 * 1.99;
        double totalPrice = receipt.getTotalPrice();

        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("2.5kg de pommes à 1.99€ avec réduction sur les brosses à dents");


    }
    // ThreeForTwo, TenPercentDiscount, TwoForAmount, FiveForAmount


    @Test
    public void testTenPercentDiscount() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product rice = new Product("rice",ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItem(rice);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, rice,10);

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


}
