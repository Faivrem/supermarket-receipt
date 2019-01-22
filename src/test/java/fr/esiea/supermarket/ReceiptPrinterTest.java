package fr.esiea.supermarket;

import fr.esiea.supermarket.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReceiptPrinterTest {

    @Test
    public void ReceiptPrinterTest() {

        // Initialisation du catalogue
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        // Caddie
        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        Teller teller = new Teller(catalog);
        // Ajout de l'offre sur la brosse à dents au catalogue
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);

        Receipt receipt = teller.checksOutArticlesFrom(cart);

        ReceiptPrinter printerDefault = new ReceiptPrinter();
        ReceiptPrinter printer = new ReceiptPrinter(30);

        String expectedPrint =
        "toothbrush                          2.97\n"
        +"  0.99 * 3\n"
        +"3 for 2(toothbrush)                -0.99\n"
        +"\n"
        +"Total:                              1.98";

        String expectedPrintCol =
            "toothbrush                2.97\n"
                +"  0.99 * 3\n"
                +"3 for 2(toothbrush)      -0.99\n"
                +"\n"
                +"Total:                    1.98";

        assertEquals(expectedPrint,printerDefault.printReceipt(receipt),"Test du printer par defaut");
        assertEquals(expectedPrintCol,printer.printReceipt(receipt),"Test du printer avec 30 colonnes");

    }
}
