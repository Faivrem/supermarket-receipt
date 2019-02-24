package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

import java.util.List;
import java.util.Map;

public interface BundleOffer {


    Discount getDiscount(Map<Product, Double> productQuantities, Map<Product, Double> productUnitPrices);
}
