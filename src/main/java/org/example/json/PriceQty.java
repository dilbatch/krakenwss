package org.example.json;
import com.fasterxml.jackson.databind.ObjectMapper;
public class PriceQty {
    public double price;
    public double qty;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }
}
