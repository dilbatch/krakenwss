package org.example.populator;

import org.example.data.AskData;
import org.example.data.BidData;
import org.example.data.UpdateData;
import org.example.json.Data;
import org.example.json.PriceQty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdatePopulatorTest {

    private UpdatePopulator updatePopulator;
    private BidPopulator bidPopulator;
    private AskPopulator askPopulator;

    @BeforeEach
    void setUp() {
        bidPopulator = mock(BidPopulator.class);
        askPopulator = mock(AskPopulator.class);
        updatePopulator = new UpdatePopulator();
        // Optionally inject mocked populators via setter if needed
    }

    @Test
    void testPopulateWithValidData() {
        // Setup source Data object
        Data source = new Data();
        source.setSymbol("AAPL");
        source.setChecksum(12345);
        Date today = new Date();
        source.setTimestamp(today);

        // Prepare PriceQty lists for bids and asks
        PriceQty bid1 = new PriceQty();
        bid1.setPrice(100d);
        bid1.setQty(5);
        PriceQty bid2 = new PriceQty();
        bid2.setPrice(105);
        bid2.setQty(3);
        List<PriceQty> bidList = new ArrayList<>();
        bidList.add(bid1);
        bidList.add(bid2);
        source.setBids(bidList);

        PriceQty ask1 = new PriceQty();
        ask1.setPrice(110);
        ask1.setQty(2);
        PriceQty ask2 = new PriceQty();
        ask2.setPrice(115);
        ask2.setQty(1);
        List<PriceQty> askList = new ArrayList<>();
        askList.add(ask1);
        askList.add(ask2);
        source.setAsks(askList);

        // Setup target UpdateData object
        UpdateData target = new UpdateData();

        // Mocking the behavior of bidPopulator and askPopulator
        BidData bidData1 = new BidData();
        bidData1.setPrice(100d);
        BidData bidData2 = new BidData();
        bidData2.setPrice(105d);

        AskData askData1 = new AskData();
        askData1.setPrice(110d);
        AskData askData2 = new AskData();
        askData2.setPrice(115d);

        doAnswer(invocation -> {
            BidData bidData = invocation.getArgument(1);
            bidData.setPrice(((PriceQty) invocation.getArgument(0)).getPrice());
            return null;
        }).when(bidPopulator).populate(any(PriceQty.class), any(BidData.class));

        doAnswer(invocation -> {
            AskData askData = invocation.getArgument(1);
            askData.setPrice(((PriceQty) invocation.getArgument(0)).getPrice());
            return null;
        }).when(askPopulator).populate(any(PriceQty.class), any(AskData.class));

        // Execute the method
        updatePopulator.populate(source, target);

        // Assertions
        assertEquals("AAPL", target.getSymbol());
        assertEquals(12345, target.getChecksum());
        assertEquals(today, target.getTimestamp());

        assertEquals(2, target.getBids().size());
        assertEquals(105, target.getBestBid().getPrice());

        assertEquals(2, target.getAsks().size());
        assertEquals(110, target.getBestAsk().getPrice());
    }

    @Test
    void testPopulateWithNullData() {
        UpdateData target = new UpdateData();
        updatePopulator.populate(null, target);
        // Ensure nothing is set when source is null
        assertNull(target.getSymbol());
        assertNull(target.getBestBid());
        assertNull(target.getBestAsk());
    }

    @Test
    void testPopulateWithInvalidTarget() {
        Data source = new Data();
        Object invalidTarget = new Object();
        updatePopulator.populate(source, invalidTarget);
        // No exception should be thrown, but the target should remain unmodified
    }

    @Test
    void testPopulateWithEmptyBidsAndAsks() {
        Data source = new Data();
        source.setSymbol("AAPL");
        source.setChecksum(12345);
        Date today = new Date();
        source.setTimestamp(today);
        source.setBids(new ArrayList<>());
        source.setAsks(new ArrayList<>());

        UpdateData target = new UpdateData();
        updatePopulator.populate(source, target);

        assertTrue(target.getBids().isEmpty());
        assertTrue(target.getAsks().isEmpty());
        assertNull(target.getBestBid());
        assertNull(target.getBestAsk());
    }
}
