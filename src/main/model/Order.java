package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents a customer's order containing multiple menu items.
 * Supports calculating subtotal, tax, total, and exporting order details.
 */
public class Order {
    public static final double NJ_TAX = 0.06625;

    private static int NEXT_ID = 10001; // simple auto-increment
    private final int orderNumber;
    private final List<MenuItem> items;
    /**
     * Creates a new empty order with an auto-generated order number.
     */
    public Order() {
        this.orderNumber = NEXT_ID++;
        this.items = new ArrayList<>();
    }
    /**
     * @return the unique order number
     */
    public int getOrderNumber() { return orderNumber; }
    /**
     * Returns an unmodifiable list of menu items in this order.
     *
     * @return list of menu items
     */
    public List<MenuItem> getItems() { return Collections.unmodifiableList(items); }
    /**
     * Adds a menu item to this order.
     *
     * @param item menu item to add
     * @throws IllegalArgumentException if item is null
     */
    public void addItem(MenuItem item) {
        if (item == null) throw new IllegalArgumentException("null item");
        items.add(item);
    }
    /**
     * Removes a menu item from the order.
     *
     * @param item item to remove
     * @return true if removed
     */
    public boolean removeItem(MenuItem item) {
        return items.remove(item);
    }
    /** Clears all items from the order. */
    public void clear() { items.clear(); }
    /**
     * Computes the subtotal of all menu items in the order.
     *
     * @return subtotal (before tax)
     */
    public double getSubtotal() {
        double sum = 0.0;
        for (MenuItem m : items) sum += m.price();
        return sum;
    }
    /**
     * Computes the NJ tax for this order.
     *
     * @return tax amount rounded to 2 decimals
     */
    public double getTax() {
        return round2(getSubtotal() * NJ_TAX);
    }
    /**
     * Computes the total amount owed including tax.
     *
     * @return total amount
     */
    public double getTotal() {
        return round2(getSubtotal() + getTax());
    }
    /**
     * Rounds a value to two decimal places.
     *
     * @param v value to round
     * @return rounded value
     */
    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Order #" + orderNumber + " Subtotal $" + String.format("%.2f", getSubtotal());
    }

    /**
     * Creates a single-line export string for use in exporting all orders.
     *
     * @return formatted export line
     */
    public String toExportLine() {
        // Example: "Order 12345 | 3 items | Total: 12.34 | [Coffee(Tall, milk), Donut(yeast, glazed), ...]"
        StringBuilder sb = new StringBuilder();
        sb.append("Order ").append(orderNumber)
                .append(" | ").append(items.size()).append(" items")
                .append(" | Total: ").append(String.format("%.2f", getSubtotal()))
                .append(" | [");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).toString());
            if (i < items.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
