package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class BundleOfferTest {

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

    @Test
    public void testBundleOfferWithAproductNotIntheOffer() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothpaste = new Product("toothpaste", ProductUnit.Each);
        catalog.addProduct(toothpaste,1.79);

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product tomatoesBox = new Product("tomatoes Box", ProductUnit.Each);
        catalog.addProduct(tomatoesBox,0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothpaste, 2.0);
        cart.addItemQuantity(toothbrush, 1.0);
        cart.addItemQuantity(tomatoesBox, 1.0);

        // On met produits dans l'offre groupée
        Map<Product,Double> bundleProductQuantities = new HashMap<>();
        bundleProductQuantities.put(toothpaste,1.0);
        bundleProductQuantities.put(toothbrush,1.0);

        Teller teller = new Teller(catalog);
        teller.addSpecialBundleOffer(SpecialOfferType.BundlePercentDiscount,bundleProductQuantities, 10);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        double expectedTotalPrice = (2 * 1.79 + 0.99 + 0.99) - (1.79 + 0.99) * 0.1;
        double totalPrice = receipt.getTotalPrice();
        Assertions.assertThat(totalPrice).isEqualTo(expectedTotalPrice).as("Test of bundle offers");
    }


}
