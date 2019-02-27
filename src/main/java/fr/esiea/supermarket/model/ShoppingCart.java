package fr.esiea.supermarket.model;

import fr.esiea.supermarket.model.offers.BundleOffer;
import fr.esiea.supermarket.model.offers.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();

    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }

    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    public void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            double quantity = productQuantities.get(p);
            if (offers.containsKey(p)) {
                Offer offer = offers.get(p);
                double unitPrice = catalog.getUnitPrice(p);

                Discount discount = offer.getDiscount(p,quantity,unitPrice);

                if (discount != null)
                    receipt.addDiscount(discount);

            }

        }
    }

    public void handleBundleOffers(Receipt receipt, List<BundleOffer> bundleOffers, SupermarketCatalog catalog) {

        // Toutes les offres
        for (BundleOffer bo : bundleOffers) {
            // Récupère les items du cart
            Map<Product, Double> copyOfItems = productQuantities();
            // Récupère les produits dans l'offre
            Map<Product, Double> bundleProductQuantities = bo.getProductQuantities();

            for (Product p : copyOfItems.keySet()) {

                if (!bundleProductQuantities.containsKey(p)) {
                    copyOfItems.remove(p);
                }
            }

            // Il ne reste plus que les produits en promo
            if (copyOfItems.size() == bundleProductQuantities.size()) {

                List<Discount> discounts = bo.getDiscount(copyOfItems,catalog);

                if (!discounts.isEmpty()) {
                    for (Discount d : discounts) {
                        receipt.addDiscount(d);
                    }
                }

            }
        }
    }


}
