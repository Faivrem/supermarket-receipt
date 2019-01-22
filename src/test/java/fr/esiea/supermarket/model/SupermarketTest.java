package fr.esiea.supermarket.model;

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
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush,0);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = 2.5 * 1.99;
        double totalPrice = receipt.getTotalPrice();
        assertEquals(expectedTotalPrice,totalPrice,"2.5kg de pommes à 1.99€ avec réduction sur les brosses à dents");

    }



}
