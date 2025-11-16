package main.model;
/**
 * Abstract base class for all menu items in the RU Cafe system.
 * Each menu item has a quantity and must implement a price() method
 * that returns the total cost based on quantity.
 */
public abstract class MenuItem {
    protected int quantity;
    /**
     * Creates a menu item with the specified quantity.
     *
     * @param quantity number of units ordered; must be >= 1
     * @throws IllegalArgumentException if quantity < 1
     */
    protected MenuItem(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity must be >= 1");
        this.quantity = quantity;
    }
    /**
     * Default constructor for subclasses that will set quantity later.
     */
    protected MenuItem() {
    }
    /**
     * Returns the quantity of this menu item.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }
    /**
     * Sets the quantity of this menu item.
     *
     * @param quantity number of units ordered; must be >= 1
     * @throws IllegalArgumentException if quantity < 1
     */
    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity must be >= 1");
        this.quantity = quantity;
    }

    /**
     * Computes the total price for this menu item,
     * including quantity and any additional customizations.
     *
     * @return the total price
     */
    public abstract double price();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " x" + quantity + " $" + String.format("%.2f", price());
    }
}
