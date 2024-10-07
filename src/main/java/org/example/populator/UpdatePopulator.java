package org.example.populator;

import org.example.data.AskData;
import org.example.data.BidData;
import org.example.data.UpdateData;
import org.example.json.Data;
import org.example.json.PriceQty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpdatePopulator extends JsonDataPopulator{
    private BidPopulator bidPopulator = new BidPopulator();
    private AskPopulator askPopulator = new AskPopulator();
    @Override
    public <S, T> void populate(S source, T target) {
        if (target instanceof UpdateData && source instanceof Data){
            ((UpdateData) target).setSymbol(((Data) source).getSymbol());
            ((UpdateData) target).setChecksum(((Data) source).getChecksum());
            ((UpdateData) target).setTimestamp(((Data) source).getTimestamp());
            List<BidData> bids = getBidData((Data) source);
            ((UpdateData) target).setBids(bids);
            List<AskData> asks = getAskData((Data) source);
            ((UpdateData) target).setAsks(asks);
            //Here is the dynamic logic of best bid. Judging from examples of requirements it is max bid.
            //since the bids are sorted we simply could get the first element. Just use below to demonstrate the stream
            Optional<BidData> maxBid = bids.stream()
                    .max((bid1, bid2) -> Double.compare(bid1.getPrice(), bid2.getPrice()));
            if(maxBid.isPresent()){
                ((UpdateData) target).setBestBid(maxBid.get());
            }
            //Here is the dynamic logic of best ask. Judging from examples of requirements it is min ask.
            //Since asks are sorted we simply could get the last element. Just use below to demonstrate the stream
            Optional<AskData> minAsk = asks.stream().min((bid1,bid2) -> Double.compare(bid1.getPrice(), bid2.getPrice()));
            if (minAsk.isPresent()){
                ((UpdateData) target).setBestAsk(minAsk.get());
            }
        } else {
            System.out.println("UpdatePopulator error :  target should be UpdateData && source should be Data");
        }
    }

    @NotNull
    private <S> List<AskData> getAskData(Data source) {
        List<AskData> asks = new ArrayList<>();
        if (source.getAsks() != null) {
            for (PriceQty ask : source.asks) {
                AskData askData = new AskData();
                askPopulator.populate(ask, askData);
                asks.add(askData);
            }
        }
        return asks.stream()
                .sorted(Comparator.comparingDouble(AskData::getPrice).reversed())
                .collect(Collectors.toList());
    }

    @NotNull
    private <S> List<BidData> getBidData(Data source) {
        List<BidData> bids = new ArrayList<>();
        if (source.getBids() != null) {
            for (PriceQty bid : source.bids) {
                BidData bidData = new BidData();
                bidPopulator.populate(bid, bidData);
                bids.add(bidData);
            }
        }
        return bids.stream()
                .sorted(Comparator.comparingDouble(BidData::getPrice).reversed())
                .collect(Collectors.toList());
    }
}
