package org.example.data;

import java.util.Collection;

public class SubscribeData {
    private String symbol;
    private Collection<BidData> bids;
    private Collection<AskData> asks;
    private String checksum;

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

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
