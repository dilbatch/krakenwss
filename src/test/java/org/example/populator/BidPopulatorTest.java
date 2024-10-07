package org.example.populator;

import org.example.data.BidData;
import org.example.json.PriceQty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BidPopulatorTest {

    private BidPopulator bidPopulator;

    @BeforeEach
    void setUp() {
        bidPopulator = new BidPopulator();
    }

    @Test
    void testPopulateWithValidData() {
        // Setup source PriceQty object
        PriceQty source = new PriceQty();
        source.setPrice(100.50);
        source.setQty(10);

        // Setup target BidData object
        BidData target = new BidData();

        // Execute the populate method
        bidPopulator.populate(source, target);

        // Assertions to check that the target is populated correctly
        assertEquals(100.50, target.getPrice());
        assertEquals(10, target.getQuantity());
    }

    @Test
    void testPopulateWithInvalidSource() {
        // Setup invalid source and valid target
        Object invalidSource = new Object(); // Source is not a PriceQty object
        BidData target = new BidData();

        // Execute the populate method
        bidPopulator.populate(invalidSource, target);

        // Ensure target is not modified
        assertNull(target.getPrice());
        assertNull(target.getQuantity());
    }

    @Test
    void testPopulateWithInvalidTarget() {
        // Setup valid source but invalid target
        PriceQty source = new PriceQty();
        source.setPrice(100.50);
        source.setQty(10);
        Object invalidTarget = new Object(); // Target is not a BidData object

        // Execute the populate method (no exception should be thrown)
        bidPopulator.populate(source, invalidTarget);

        // No assertions as the invalid target should remain unaffected
    }

    @Test
    void testPopulateWithNullSource() {
        // Setup a null source and valid target
        PriceQty source = null;
        BidData target = new BidData();

        // Execute the populate method
        bidPopulator.populate(source, target);

        // Ensure target is not modified
        assertNull(target.getPrice());
        assertNull(target.getQuantity());
    }

    @Test
    void testPopulateWithNullTarget() {
        // Setup valid source and null target
        PriceQty source = new PriceQty();
        source.setPrice(100.50);
        source.setQty(10);
        BidData target = null;

        // Execute the populate method (no exception should be thrown)
        bidPopulator.populate(source, target);

        // No assertions as the target is null
    }
}
