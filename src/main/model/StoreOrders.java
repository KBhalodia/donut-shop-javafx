package main.model;

import java.util.ArrayList;

/**
 * The StoreOrders class keeps track of all orders placed in the store.
 * It allows adding, removing, viewing, and exporting orders.
 * @author Kavya
 */
public class StoreOrders {

    private ArrayList<Order> orders;

    public StoreOrders() {
        orders = new ArrayList<>();
    }

    /** Adds an order to the store’s order list */
    public void addOrder(Order order) {
        if (order != null && !orders.contains(order)) {
            orders.add(order);
        }
    }

    /** Removes an order from the store’s order list */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    /** Returns the list of all placed orders */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /** Returns a string representation of all orders */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Order order : orders) {
            sb.append(order.toString()).append("\n");
        }
        return sb.toString();
    }

    /** Exports orders to text file format with order number, items list, and total amount */
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
