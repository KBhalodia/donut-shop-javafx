package main.model;

public abstract class MenuItem {
    protected int quantity;

    protected MenuItem(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity must be >= 1");
        this.quantity = quantity;
    }

    protected MenuItem() {
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity must be >= 1");
        this.quantity = quantity;
    }

    /** Price for THIS item considering its quantity. */
    public abstract double price();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " x" + quantity + " $" + String.format("%.2f", price());
    }
}
