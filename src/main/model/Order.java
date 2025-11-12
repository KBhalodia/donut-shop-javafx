package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    public static final double NJ_TAX = 0.06625;

    private static int NEXT_ID = 10001; // simple auto-increment
    private final int orderNumber;
    private final List<MenuItem> items;

    public Order() {
        this.orderNumber = NEXT_ID++;
        this.items = new ArrayList<>();
    }

    public int getOrderNumber() { return orderNumber; }
    public List<MenuItem> getItems() { return Collections.unmodifiableList(items); }

    public void addItem(MenuItem item) {
        if (item == null) throw new IllegalArgumentException("null item");
        items.add(item);
    }

    public boolean removeItem(MenuItem item) {
        return items.remove(item);
    }

    public void clear() { items.clear(); }

    public double getSubtotal() {
        double sum = 0.0;
        for (MenuItem m : items) sum += m.price();
        return sum;
    }

    public double getTax() {
        return round2(getSubtotal() * NJ_TAX);
    }

    public double getTotal() {
        return round2(getSubtotal() + getTax());
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Order #" + orderNumber + " Subtotal $" + String.format("%.2f", getSubtotal());
    }

    // inside main.model.Order
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
