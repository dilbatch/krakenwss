package org.example.populator;

import org.example.data.AskData;
import org.example.json.PriceQty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AskPopulatorTest {

    private AskPopulator askPopulator;

    @BeforeEach
    void setUp() {
        askPopulator = new AskPopulator();
    }

    @Test
    void testPopulateWithValidData() {
        // Setup source PriceQty object
        PriceQty source = new PriceQty();
        source.setPrice(200.75);
        source.setQty(50);

        // Setup target AskData object
        AskData target = new AskData();

        // Execute the populate method
        askPopulator.populate(source, target);

        // Assertions to verify the target is populated correctly
        assertEquals(200.75, target.getPrice());
        assertEquals(50, target.getQuantity());
    }

    @Test
    void testPopulateWithInvalidSource() {
        // Setup invalid source and valid target
        Object invalidSource = new Object();  // Source is not a PriceQty object
        AskData target = new AskData();

        // Execute the populate method
        askPopulator.populate(invalidSource, target);

        // Ensure target is not modified
        assertNull(target.getPrice());
        assertNull(target.getQuantity());
    }

    @Test
    void testPopulateWithInvalidTarget() {
        // Setup valid source but invalid target
        PriceQty source = new PriceQty();
        source.setPrice(200.75);
        source.setQty(50);
        Object invalidTarget = new Object();  // Target is not an AskData object

        // Execute the populate method
        askPopulator.populate(source, invalidTarget);

        // No exception should be thrown, but the invalid target should not be modified
    }

    @Test
    void testPopulateWithNullSource() {
        // Setup null source and valid target
        PriceQty source = null;
        AskData target = new AskData();

        // Execute the populate method
        askPopulator.populate(source, target);

        // Ensure target is not modified
        assertNull(target.getPrice());
        assertNull(target.getQuantity());
    }

    @Test
    void testPopulateWithNullTarget() {
        // Setup valid source and null target
        PriceQty source = new PriceQty();
        source.setPrice(200.75);
        source.setQty(50);
        AskData target = null;

        // Execute the populate method
        askPopulator.populate(source, target);

        // No exception should be thrown, but nothing happens since the target is null
    }
}
