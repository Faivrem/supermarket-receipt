package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BundlePercentOffer implements BundleOffer {

    private HashMap<Product, Double> products;
    private final double argument;


    public BundlePercentOffer(HashMap<Product, Double> products, double argument) {
        this.argument = argument;
        this.products = products;
    }

    @Override
    public Discount getDiscount(Map<Product, Double> productQuantities, Map<Product, Double> productUnitPrices) {

        double discountAmount = 0;
        List<Product> productList = new ArrayList<Product>();

        for (Product p : productQuantities.keySet()) {

            double quantity = productQuantities.get(p);
            double unitPrice = productUnitPrices.get(p);

            discountAmount += quantity*unitPrice;
            productList.add(p);
        }

        discountAmount = discountAmount * 0.1 / 100;

        return new Discount(productList, toString() + "with " + argument + "% off", discountAmount);
    }

    @Override
    public String toString() {

        String returnedString = "";

        for (Product p : products.keySet()) {
            String quantity = Double.toString(products.get(p));

            returnedString += quantity + " " + p.getName() + "+ ";
        }

        return returnedString;

    }
}
