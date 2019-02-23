package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

public class ThreeForTwoOffer implements Offer {

    private final Product product;
    private final double argument;


    public ThreeForTwoOffer(Product product, double argument) {
        this.argument = argument;
        this.product = product;
    }

    @Override
    public Discount getDiscount(Product p, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        Discount discount = null;

        int numberOfXs = quantityAsInt / 3;

        if (quantityAsInt > 2) {
            double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
            discount = new Discount(p, "3 for 2", discountAmount);
        }

        return discount;
    }
}
