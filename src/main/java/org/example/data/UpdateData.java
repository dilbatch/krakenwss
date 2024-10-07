package org.example.data;

import java.util.Collection;
import java.util.Date;

public class UpdateData {
    private String symbol;
    private Collection<BidData> bids;
    private Collection<AskData> asks;
    private long checksum;
    private Date timestamp;
    //Keepp best Bid bid and ask as data bean attribute to be filled in the Populator.
    // The logic of how to define best bid and ask could be changed in the populator
    private BidData bestBid;
    private AskData bestAsk;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Collection<BidData> getBids() {
        return bids;
    }

    public void setBids(Collection<BidData> bids) {
        this.bids = bids;
    }

    public Collection<AskData> getAsks() {
        return asks;
    }

    public void setAsks(Collection<AskData> asks) {
        this.asks = asks;
    }

    public long getChecksum() {
        return checksum;
    }

    public void setChecksum(long checksum) {
        this.checksum = checksum;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public BidData getBestBid() {
        return bestBid;
    }

    public void setBestBid(BidData bestBid) {
        this.bestBid = bestBid;
    }

    public AskData getBestAsk() {
        return bestAsk;
    }

    public void setBestAsk(AskData bestAsk) {
        this.bestAsk = bestAsk;
    }
}
