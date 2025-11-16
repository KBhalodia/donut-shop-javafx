package main.enums;
/**
 * Represents the available cup sizes for brewed coffee.
 */
public enum CupSize {
    SHORT(0), TALL(1), GRANDE(2), VENTI(3);
    public final int stepsFromShort;
    CupSize(int steps) { this.stepsFromShort = steps; }
}
