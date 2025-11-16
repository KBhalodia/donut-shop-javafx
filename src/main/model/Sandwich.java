package main.model;

import main.enums.Bread;
import main.enums.Protein;
import main.enums.AddOns;
import java.util.ArrayList;

public class Sandwich extends MenuItem {
    protected Bread bread;
    protected Protein protein;
    protected ArrayList<AddOns> addOns;

    public Sandwich(Bread bread, Protein protein, int quantity) {
        super(quantity);
        if (bread == null || protein == null) throw new IllegalArgumentException("bread/protein required");
        this.bread = bread;
        this.protein = protein;
        this.addOns = new ArrayList<>();
    }

    public Bread getBread() { return bread; }
    public void setBread(Bread bread) { this.bread = bread; }
    public Protein getProtein() { return protein; }
    public void setProtein(Protein protein) { this.protein = protein; }
    public ArrayList<AddOns> getAddOns() { return addOns; }

    public boolean addAddOn(AddOns a) { return a != null && !addOns.contains(a) && addOns.add(a); }
    public boolean removeAddOn(AddOns a) { return addOns.remove(a); }

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
