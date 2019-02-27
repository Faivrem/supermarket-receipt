package fr.esiea.supermarket.model;

import java.util.Objects;

public class Product {
    private final String name;
    private final ProductUnit unit;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product(String name, ProductUnit unit) {
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }


    public ProductUnit getUnit() {
        return unit;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, unit);
    }
}
