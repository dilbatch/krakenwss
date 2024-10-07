package org.example.service.impl;

import org.example.data.UpdateData;
import org.example.json.Data;
import org.example.json.Json;
import org.example.json.JsonParser;
import org.example.populator.UpdatePopulator;
import org.example.service.MessagetService;

public class DefaultMessagetService implements MessagetService {
    private JsonParser jsonParser = new JsonParser();
    private UpdatePopulator updatePopulator = new UpdatePopulator();
    @Override
    public void read(String message) {
        Json result = jsonParser.parse(message);
        if (result != null) {
//            System.out.print("responce type: " + result.getType());
            if ("update".equals(result.getType()) && "book".equals(result.getChannel())) {

                result.getData().stream().forEach(data -> {
                    UpdateData updateData = new UpdateData();
                    populateUpdate(data,updateData);
                });

            } else {
//                System.out.println("---->  omitted ");
            }
        } else {
//            System.out.print("ommited responce: " + message);
        }
    }

    private void populateUpdate(Data data, UpdateData updateData) {
        updatePopulator.populate(data, updateData);
        System.out.println("<---------------------------> ");
        printUpdate(updateData);
        System.out.println("<---------------------------> ");
    }

    private void printUpdate(UpdateData updateData) {
        System.out.println("asks:"+updateData.getAsks());
        System.out.println("best bid:"+updateData.getBestBid());
        System.out.println("best ask:"+updateData.getBestAsk());
        System.out.println("bids:"+ updateData.getBids());
        System.out.println(updateData.getTimestamp());
        System.out.println(updateData.getSymbol());
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public void setJsonParser(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public UpdatePopulator getUpdatePopulator() {
        return updatePopulator;
    }

    public void setUpdatePopulator(UpdatePopulator updatePopulator) {
        this.updatePopulator = updatePopulator;
    }
}
