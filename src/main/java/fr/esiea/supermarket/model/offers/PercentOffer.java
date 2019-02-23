package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;


public class PercentOffer implements Offer {

    private final Product product;
    private final double argument;

    public PercentOffer(Product product, double argument) {
        this.product = product;
        this.argument = argument;
    }

    @Override
    public Discount getDiscount(Product p, double quantity, double unitPrice) {
        return new Discount(p, argument + "% off", quantity * unitPrice * argument / 100.0);
    }
}
