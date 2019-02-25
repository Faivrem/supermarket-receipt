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

    public void handleBundleOffers(Receipt receipt, Map<BundleOffer, HashMap<Product, Double>> bundleOffers, SupermarketCatalog catalog) {

        Map<Product, Double> tempProductQuantities = new HashMap<>();
        Map<Product, Double> tempProductUnitPrices = new HashMap<>();

        // Pour chaque bundle offer
        for(BundleOffer bo : bundleOffers.keySet()) {

            // On récupère l'offre (produits et leurs quantités dans l'offre)
            Map<Product, Double> pOfferQuantities = bundleOffers.get(bo);

            // On vide les HashMap de stockage des infos produits temporaires
            tempProductQuantities.clear();
            tempProductUnitPrices.clear();

            // On parcourt notre panier
            for (Product p: productQuantities().keySet()) {

                //Si le produit est compris dans l'offre
                if (pOfferQuantities.containsKey(p)) {

                    double quantity = productQuantities.get(p);
                    double unitPrice = catalog.getUnitPrice(p);

                    // On stocke temporairement les infos sur le produits
                    tempProductQuantities.put(p,quantity);
                    tempProductUnitPrices.put(p,unitPrice);

                }
            }

            // L'offre est respecté
            if (pOfferQuantities.size() == tempProductQuantities.size() && pOfferQuantities.size() == tempProductUnitPrices.size()) {

                Discount discount = null;

                discount = bo.getDiscount(tempProductQuantities,tempProductUnitPrices);

                if (discount != null)
                    receipt.addDiscount(discount);

            }

        }

    }
}
