package org.example.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    public String symbol;
    public List<PriceQty> bids;
    public List<PriceQty> asks;
    public long checksum;

    public Date timestamp;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<PriceQty> getBids() {
        return bids;
    }

    public void setBids(List<PriceQty> bids) {
        this.bids = bids;
    }

    public List<PriceQty> getAsks() {
        return asks;
    }

    public void setAsks(List<PriceQty> asks) {
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
}
