package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;

public interface Offer {

    Discount getDiscount(Product p, double quantity, double unitPrice);


}
