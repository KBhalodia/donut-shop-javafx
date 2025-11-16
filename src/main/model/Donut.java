package main.model;

import main.enums.DonutCategory;
/**
 * Represents a single donut menu item with a specific category, flavor,
 * and quantity. The price is determined by the donut category and the
 * number of donuts ordered.
 */

public class Donut extends MenuItem {
    private final DonutCategory type;
    private final String flavor;
    /**
     * Creates a new donut with the given category, flavor, and quantity.
     *
     * @param type     the donut category (e.g., yeast, cake, hole, seasonal)
     * @param flavor   the flavor name of the donut
     * @param quantity the number of donuts ordered
     */
    public Donut(DonutCategory type, String flavor, int quantity) {
        super(quantity);
        this.type = type;
        this.flavor = flavor;
    }
    /**
     * Computes the total price of this donut order based on the category
     * and quantity. Each category has a different unit price.
     *
     * @return the total price for this donut order
     */
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
    /**
     * Returns the flavor of this donut.
     *
     * @return the flavor string
     */
    public String getFlavor() { return flavor; }

    /**
     * Returns the category of this donut.
     *
     * @return the donut category
     */
    public DonutCategory getType() { return type; }

    /**
     * Returns a string representation of this donut, including its category,
     * flavor, quantity, and computed price.
     *
     * @return a formatted string describing this donut
     */
    @Override
    public String toString() {
        return String.format("%s Donut - %s x%d ($%.2f)",
                type.name(), flavor, quantity, price());
    }
}
