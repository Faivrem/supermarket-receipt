package fr.esiea.supermarket.model;


import fr.esiea.supermarket.model.offers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teller {

    private final SupermarketCatalog catalog;
    private Map<Product, Offer> offers = new HashMap<>();
    private Map<BundleOffer, HashMap<Product, Double>> bundleOffers = new HashMap<>();

    public Teller(SupermarketCatalog catalog) {
        this.catalog = catalog;
    }

    public void addSpecialOffer(SpecialOfferType offerType, Product product, double argument) {
        switch(offerType) {
            case FiveForAmount:
                this.offers.put(product, new FiveForAmountOffer(product, argument));
                break;
            case ThreeForTwo:
                this.offers.put(product, new ThreeForTwoOffer(product, argument));
                break;
            case TwoForAmount:
                this.offers.put(product, new TwoForAmountOffer(product, argument));
                break;
            case XPercentDiscount:
                this.offers.put(product, new PercentOffer(product, argument));
                break;
        }

    }

    public void addSpecialBundleOffer(SpecialOfferType offerType, HashMap<Product, Double> products, double argument) {

        switch (offerType) {
            case BundlePercentDiscount:
                this.bundleOffers.put(new BundlePercentOffer(products, argument), products);
                break;
        }

    }

    public Receipt checksOutArticlesFrom(ShoppingCart theCart) {
        Receipt receipt = new Receipt();
        List<ProductQuantity> productQuantities = theCart.getItems();
        for (ProductQuantity pq : productQuantities) {
            Product p = pq.getProduct();
            double quantity = pq.getQuantity();
            double unitPrice = this.catalog.getUnitPrice(p);
            double price = quantity * unitPrice;
            receipt.addProduct(p, quantity, unitPrice, price);
        }

        if (!this.bundleOffers.isEmpty()) {
            theCart.handleBundleOffers(receipt, this.bundleOffers, this.catalog);
        }
        if (!this.offers.isEmpty()) {
            theCart.handleOffers(receipt, this.offers, this.catalog);
        }

        return receipt;
    }


}


