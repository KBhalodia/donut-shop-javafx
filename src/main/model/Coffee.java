package main.model;

import main.enums.CupSize;
import main.enums.AddIns;
import java.util.ArrayList;

public class Coffee extends MenuItem {
    private CupSize size;
    private ArrayList<AddIns> addIns;

    public Coffee(CupSize size, int quantity) {
        super(quantity);
        if (size == null) throw new IllegalArgumentException("size required");
        this.size = size;
        this.addIns = new ArrayList<>();
    }

    public CupSize getSize() { return size; }
    public void setSize(CupSize size) { this.size = size; }

    public ArrayList<AddIns> getAddIns() { return addIns; }
    public boolean addAddIn(AddIns a) { return a != null && !addIns.contains(a) && addIns.add(a); }
    public boolean removeAddIn(AddIns a) { return addIns.remove(a); }

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
