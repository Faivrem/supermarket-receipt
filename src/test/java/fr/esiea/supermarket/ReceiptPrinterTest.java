package fr.esiea.supermarket;

import fr.esiea.supermarket.model.*;
import fr.esiea.supermarket.model.offers.SpecialOfferType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReceiptPrinterTest {

    @Test
    public void DefaultReceiptPrinterTest() {


        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(toothbrush,1);
        cart.addItemQuantity(apples, 2.5);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        ReceiptPrinter printerDefault = new ReceiptPrinter();

        String expectedPrintDefault =
        "toothbrush                          1.98\n"
        +"  0.99 * 2\n"
        +"toothbrush                          0.99\n"
        +"apples                              4.98\n"
        +"  1.99 * 2.500\n"
        +"3 for 2(toothbrush)                -0.99\n"
        +"\n"
        +"Total:                              6.96";


        Assertions.assertThat(expectedPrintDefault).isEqualTo(printerDefault.printReceipt(receipt)).as("Test du Receipt Printer par defaut");

    }

    @Test
    public void WithColsReceiptPrinterTest() {

        SupermarketCatalog catalog = new FakeCatalog();

        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 2);
        cart.addItemQuantity(toothbrush,1);
        cart.addItemQuantity(apples, 2.5);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        ReceiptPrinter printerDefault = new ReceiptPrinter();
        ReceiptPrinter printerWithCols = new ReceiptPrinter(30);

        String expectedPrintWithCol =
            "toothbrush                1.98\n"
                +"  0.99 * 2\n"
                +"toothbrush                0.99\n"
                +"apples                    4.98\n"
                +"  1.99 * 2.500\n"
                +"3 for 2(toothbrush)      -0.99\n"
                +"\n"
                +"Total:                    6.96";

        Assertions.assertThat(expectedPrintWithCol).isEqualTo(printerWithCols.printReceipt(receipt)).as("Test du Receipt printer avec 30 colonnes");

    }
}
