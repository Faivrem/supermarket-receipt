package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

public class FiveForAmountOffer implements Offer {

    private final Product product;
    private final double argument;


    public FiveForAmountOffer(Product product, double argument) {
        this.argument = argument;
        this.product = product;
    }

    @Override
    public Discount getDiscount(Product p, double quantity, double unitPrice) {
        int quantityAsInt = (int) quantity;
        Discount discount = null;

        int numberOfXs = quantityAsInt / 5;

        if (quantityAsInt >= 5) {
            double discountTotal = unitPrice * quantity - (argument * numberOfXs + quantityAsInt % 5 * unitPrice);
            discount = new Discount(p, 5 + " for " + argument, discountTotal);
        }

        return discount;
    }
}
