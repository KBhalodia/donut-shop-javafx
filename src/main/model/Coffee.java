package main.model;

import main.enums.CupSize;
import main.enums.AddIns;
import java.util.ArrayList;
/**
 * Represents a brewed coffee order with a specific cup size, quantity,
 * and optional add-ins such as milk or syrup.
 */
public class Coffee extends MenuItem {
    private CupSize size;
    private ArrayList<AddIns> addIns;
    /**
     * Creates a coffee object with the given size and quantity.
     *
     * @param size     the cup size (SHORT, TALL, GRANDE, VENTI)
     * @param quantity number of cups ordered
     * @throws IllegalArgumentException if size is null
     */
    public Coffee(CupSize size, int quantity) {
        super(quantity);
        if (size == null) throw new IllegalArgumentException("size required");
        this.size = size;
        this.addIns = new ArrayList<>();
    }
    /**
     * Returns the cup size of this coffee.
     *
     * @return the cup size
     */
    public CupSize getSize() { return size; }
    /**
     * Sets the cup size of this coffee.
     *
     * @param size new cup size
     */
    public void setSize(CupSize size) { this.size = size; }
    /**
     * Returns the list of add-ins for this coffee.
     *
     * @return list of add-ins
     */
    public ArrayList<AddIns> getAddIns() { return addIns; }
    /**
     * Adds an add-in to this coffee.
     *
     * @param a the add-in to add
     * @return true if added; false if already present
     */
    public boolean addAddIn(AddIns a) { return a != null && !addIns.contains(a) && addIns.add(a); }
    /**
     * Removes an add-in from this coffee.
     *
     * @param a the add-in to remove
     * @return true if removed
     */
    public boolean removeAddIn(AddIns a) { return addIns.remove(a); }
    /**
     * Computes the total price of this coffee order.
     * Base price: Short = 2.39; each size step +0.60.
     * Each add-in is +0.25.
     *
     * @return total price
     */
    @Override
    public double price() {
        // Base: Short black = 2.39; each size step +0.60; each add-in +0.25
        double base = 2.39;
        int steps = size.ordinal(); // assume SHORT=0, TALL=1, GRANDE=2, VENTI=3
        double cupPrice = base + 0.60 * steps;
        double addInsCost = 0.25 * addIns.size();
        return (cupPrice + addInsCost) * quantity;
    }
}
