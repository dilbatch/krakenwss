package org.example.populator;

import org.example.data.BidData;
import org.example.json.PriceQty;

public class BidPopulator extends JsonDataPopulator{
    @Override
    public <S, T> void populate(S source, T target) {
        if(source instanceof PriceQty && target instanceof BidData){
            ((BidData)target).setPrice(((PriceQty) source).getPrice());
            ((BidData) target).setQuantity(((PriceQty) source).getQty());
        } else {
            System.out.println("BidPopulator error :  target should be PriceQty && source should be BidData");
        }
    }
}
