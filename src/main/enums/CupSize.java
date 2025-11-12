package main.enums;

public enum CupSize {
    SHORT(0), TALL(1), GRANDE(2), VENTI(3);
    public final int stepsFromShort;
    CupSize(int steps) { this.stepsFromShort = steps; }
}
