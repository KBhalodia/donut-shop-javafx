package main.model;

import main.enums.Bread;
import main.enums.Protein;
import main.enums.AddOns;
import java.util.ArrayList;
/**
 * Represents a sandwich order consisting of bread type, protein choice,
 * optional add-ons, and a quantity.
 */
public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;
    /**
     * Creates a sandwich with the selected bread, protein, and quantity.
     *
     * @param bread   chosen bread type
     * @param protein chosen protein type
     * @param quantity number of sandwiches
     * @throws IllegalArgumentException if bread or protein is null
     */
    public Sandwich(Bread bread, Protein protein, int quantity) {
        super(quantity);
        if (bread == null || protein == null) throw new IllegalArgumentException("bread/protein required");
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>();
    }

    /** @return the selected bread type */
    public Bread getBread() { return bread; }
    /** @param bread new bread type */
    public void setBread(Bread bread) { this.bread = bread; }
    /** @return the selected protein */
    public Protein getProtein() { return protein; }
    /** @param protein new protein */
    public void setProtein(Protein protein) { this.protein = protein; }
    /** @return list of add-ons */
    public ArrayList<AddOns> getAddOns() { return addOns; }
    /**
     * Adds an add-on to this sandwich.
     *
     * @param a the add-on
     * @return true if added; false if already present
     */
    public boolean addAddOn(AddOns a) { return a != null && !addOns.contains(a) && addOns.add(a); }
    /**
     * Removes an add-on from the sandwich.
     *
     * @param a the add-on to remove
     * @return true if removed
     */
    public boolean removeAddOn(AddOns a) { return addOns.remove(a); }
    /**
     * Computes the total price of this sandwich.
     * Protein determines base price:
     *  - Beef: 12.99
     *  - Chicken: 10.99
     *  - Salmon: 14.99
     * Add-ons:
     *  - Cheese: +1.00
     *  - Lettuce/Tomato/Onion: +0.30 each
     *
     * @return the total price
     */
    @Override
    public double price() {
        double base;
        switch (protein) {
            case BEEF: base = 12.99; break;
            case CHICKEN: base = 10.99; break;
            case SALMON: base = 14.99; break;
            default: throw new IllegalStateException("Unknown protein");
        }
        double extras = 0.0;
        for (AddOns a : addOns) {
            if (a == AddOns.CHEESE) extras += 1.00;
            else extras += 0.30; // lettuce, tomatoes, onions
        }
        return (base + extras) * quantity;
    }
}
