package main.tests;

import main.enums.Bread;
import main.enums.Protein;
import main.enums.AddOns;
import main.model.Sandwich;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Unit tests for the Sandwich price calculation.
 */
public class SandwichTest {

    @Test
    public void testChickenWheatWithCheeseAndLettuce() {
        // Chicken base = 10.99
        // Cheese = +1.00
        // Lettuce = +0.30
        // Quantity = 1
        Sandwich s = new Sandwich(Bread.WHEAT, Protein.CHICKEN, 1);
        s.addAddOn(AddOns.CHEESE);
        s.addAddOn(AddOns.LETTUCE);

        double expected = 10.99 + 1.00 + 0.30;  // 12.29
        double actual = s.price();

        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testSalmonBagelAllAddOnsQuantityTwo() {
        // Salmon base = 14.99
        // Cheese = +1.00
        // Lettuce + tomatoes + onions = 3 * 0.30 = 0.90
        // Per sandwich = 14.99 + 1.00 + 0.90 = 16.89
        // Quantity = 2 → 33.78 total
        Sandwich s = new Sandwich(Bread.BAGEL, Protein.SALMON, 2);
        s.addAddOn(AddOns.CHEESE);
        s.addAddOn(AddOns.LETTUCE);
        s.addAddOn(AddOns.TOMATOES);
        s.addAddOn(AddOns.ONIONS);

        double expected = 16.89 * 2;  // 33.78
        double actual = s.price();

        assertEquals(expected, actual, 0.01);
    }
}
