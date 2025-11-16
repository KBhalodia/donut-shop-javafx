package main.model;

import main.enums.CupSize;
import main.enums.AddIns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Unit tests for the Coffee price calculation.
 */
public class CoffeeTest {

    @Test
    public void testShortBlackNoAddIns() {
        // Short black = 2.39
        Coffee c = new Coffee(CupSize.SHORT, 1);

        double expected = 2.39;
        double actual = c.price();

        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testGrandeWithTwoAddIns() {
        // Base short = 2.39
        // Grande is 2 size steps above Short: +0.60 * 2 = +1.20 → 3.59
        // 2 add-ins = 2 * 0.25 = 0.50
        // Total = 3.59 + 0.50 = 4.09
        Coffee c = new Coffee(CupSize.GRANDE, 1);
        c.addAddIn(AddIns.VANILLA);
        c.addAddIn(AddIns.CARAMEL);

        double expected = 4.09;
        double actual = c.price();

        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testVentiWithThreeAddInsQuantityTwo() {
        // Short = 2.39
        // Venti is 3 steps above Short: 2.39 + 0.60*3 = 4.19
        // 3 add-ins = 3 * 0.25 = 0.75 → per cup = 4.19 + 0.75 = 4.94
        // Quantity 2 → 4.94 * 2 = 9.88
        Coffee c = new Coffee(CupSize.VENTI, 2);
        c.addAddIn(AddIns.WHIPPED_CREAM);
        c.addAddIn(AddIns.VANILLA);
        c.addAddIn(AddIns.MOCHA);

        double expected = 9.88;
        double actual = c.price();

        assertEquals(expected, actual, 0.01);
    }
}
