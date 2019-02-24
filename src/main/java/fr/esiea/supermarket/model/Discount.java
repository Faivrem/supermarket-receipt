package fr.esiea.supermarket.model;

import java.util.List;

public class Discount {
    private final String description;
    private final double discountAmount;
    private Product product;
    private List<Product> products;

    public Discount(Product product, String description, double discountAmount) {
        this.product = product;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public Discount(List<Product> products, String description, double discountAmount) {
        this.products = products;
        this.description = description;
        this.discountAmount = discountAmount;
    }


    public String getDescription() {
        return description;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public Product getProduct() {
        return product;
    }

}
