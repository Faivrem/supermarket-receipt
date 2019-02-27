package fr.esiea.supermarket.model.offers;

import fr.esiea.supermarket.model.Discount;
import fr.esiea.supermarket.model.Product;
import fr.esiea.supermarket.model.ProductQuantity;
import fr.esiea.supermarket.model.SupermarketCatalog;

import java.util.*;

public class BundlePercentOffer implements BundleOffer {

    private Map<Product, Double> productQuantities;
    private double argument;

    public BundlePercentOffer(Map<Product, Double> productQuantities, double argument) {
        this.argument = argument;
        this.productQuantities = productQuantities;
    }

    @Override
    public List<Discount> getDiscount(Map<Product, Double> productQuantities, SupermarketCatalog catalog) {

        int numberOfTime = 1;
        double discountAmount = 0;
        double totalPrice = 0;
        List<Discount> discounts = new ArrayList<>();
        // Liste pour envoyer au constructeur du Discount
        List<Product> products = new ArrayList<>();

        int flag = 0;
        // Compte le nombre de fois qu'on va appliquer l'offre
        for (Product p : this.productQuantities.keySet()) {
            int offerQuantity = this.productQuantities.get(p).intValue();
            int productQuantity = productQuantities.get(p).intValue();

            int numberOfXs = productQuantity/offerQuantity;

            if (flag == 0) {
                numberOfTime = numberOfXs;
                flag++;
            }

            if (numberOfXs < numberOfTime) {
                numberOfTime = numberOfXs;
            }

            System.out.println("Number of time : " + numberOfTime);
        }

        // Calcul du prix total 1 x fois l'offre
        for (Product p : this.productQuantities.keySet()) {
            products.add(p);
            double quantity = this.productQuantities.get(p);
            double unitPrice = catalog.getUnitPrice(p);

            System.out.println(p + " -> quantity : " + quantity);
            System.out.println(p + " -> unitPrice : " + unitPrice);

            totalPrice += unitPrice * quantity;
        }
        // Application de l'offre sur le prix
        discountAmount = totalPrice * argument/100;
        System.out.println("Total Price : " + totalPrice);
        System.out.println("Total Price : " + discountAmount);


        //Nombre de fois qu'on applique cette réduction
        for (int i = 0; i < numberOfTime; i++) {
            discounts.add(new Discount(products,argument + "% off on " + toString(), discountAmount));
        }

        return discounts;
    }

    @Override
    public Map<Product, Double> getProductQuantities() {
        return this.productQuantities;
    }


    @Override
    public String toString() {

        String returnedString = "";

        for (Product p : productQuantities.keySet()) {
            String quantity = Double.toString(productQuantities.get(p));

            returnedString += quantity + " " + p.getName() + "+ ";
        }

        return returnedString;

    }
}
