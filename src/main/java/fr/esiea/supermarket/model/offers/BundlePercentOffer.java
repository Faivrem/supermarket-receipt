package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

import java.util.List;
import java.util.Map;

public class BundlePercentOffer implements BundleOffer{


    private List<Product> products;
    private final double argument;

    public BundlePercentOffer(List<Product> products, double argument) {
        this.products = products;
        this.argument = argument;
    }

    @Override
    public Discount getDiscount(Map<Product, Double> productQuantities, Map<Product, Double> productUnitPrices) {

        double discountAmount = 0.0;
        for (Product p : productQuantities.keySet()) {
            discountAmount += productUnitPrices.get(p) * productQuantities.get(p);
        }
        discountAmount = discountAmount * argument / 100.0;
        System.out.println(discountAmount);
        return new Discount(products, argument + "% off", discountAmount);
    }
}
