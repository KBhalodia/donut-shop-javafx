package main.model;

import main.enums.DonutCategory;

public class Donut extends MenuItem {
    private final DonutCategory type;
    private final String flavor;

    public Donut(DonutCategory type, String flavor, int quantity) {
        this.type = type;
        this.flavor = flavor;
        this.quantity = quantity;
    }

    @Override
    public double price() {
        double unit =
                switch (type) {
                    case YEAST -> 1.99;
                    case CAKE -> 2.19;
                    case HOLE -> 0.39;
                    case SEASONAL -> 2.49;
                };
        return unit * quantity;
    }

    public String getFlavor() { return flavor; }
    public DonutCategory getType() { return type; }

    @Override
    public String toString() {
        return String.format("%s Donut - %s x%d ($%.2f)",
                type.name(), flavor, quantity, price());
    }
}
