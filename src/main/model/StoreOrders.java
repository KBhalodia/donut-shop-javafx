package main.model;

import java.util.ArrayList;

/**
 * The StoreOrders class keeps track of all orders placed in the store.
 * It allows adding, removing, viewing, and exporting orders.
 */
public class StoreOrders {

    private ArrayList<Order> orders;
    /**
     * Creates an empty list of store orders.
     */
    public StoreOrders() {
        orders = new ArrayList<>();
    }

    /**
     * Adds an order to the store order list.
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        if (order != null && !orders.contains(order)) {
            orders.add(order);
        }
    }

    /**
     * Removes an order from the store.
     *
     * @param order the order to remove
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /**
     * Returns all store orders.
     *
     * @return list of orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Returns a formatted string listing all orders.
     *
     * @return summary of all orders
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Order order : orders) {
            sb.append(order.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Creates an export-friendly multi-line string for all orders.
     * Includes order number, items, subtotal, tax, and total.
     *
     * @return formatted text for exporting
     */
    public String export() {
        StringBuilder sb = new StringBuilder();
        for (Order order : orders) {
            sb.append("Order #").append(order.getOrderNumber()).append("\n");
            sb.append("Items:\n");
            
            // List all menu items in the order
            for (var item : order.getItems()) {
                sb.append("  • ").append(item.toString()).append("\n");
            }
            
            sb.append("Subtotal: $").append(String.format("%.2f", order.getSubtotal())).append("\n");
            sb.append("Tax: $").append(String.format("%.2f", order.getTax())).append("\n");
            sb.append("Total: $").append(String.format("%.2f", order.getTotal())).append("\n");
            sb.append("\n");
        }
        return sb.toString();
    }
}
