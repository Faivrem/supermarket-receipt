package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.ProductQuantity;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.List;
import java.util.Map;

public interface BundleOffer {

    List<Discount> getDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog);
    Map<Product, Double> getProductQuantities();

}
