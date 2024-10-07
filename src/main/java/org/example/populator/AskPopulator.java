package org.example.populator;

import org.example.data.AskData;
import org.example.json.PriceQty;

public class AskPopulator extends JsonDataPopulator{
    @Override
    public <S, T> void populate(S source, T target) {
        if (source instanceof PriceQty && target instanceof AskData){
            ((AskData) target).setPrice(((PriceQty) source).getPrice());
            ((AskData) target).setQuantity(((PriceQty) source).getQty());
        } else {
            System.out.println("AskPopulator error :  target should be PriceQty && source should be AskData");
        }
    }
}
