package org.example;


import okhttp3.*;
import okio.ByteString;
import org.example.service.MessagetService;
import org.example.service.impl.DefaultMessagetService;

import java.util.concurrent.TimeUnit;

/**
 * An Implementation of Kraken WebSocket client application.
 */
public class KrakenWebSocketClient {

    private final OkHttpClient client;


    public KrakenWebSocketClient() {
        client = new OkHttpClient.Builder()
                .pingInterval(30, TimeUnit.SECONDS)  // Optional: Sending pings to keep the connection alive
                .build();
    }

    public void start() {
        Request request = new Request.Builder().url("wss://ws.kraken.com/v2").build();
        KrakenWebSocketListener listener = new KrakenWebSocketListener();
        WebSocket webSocket = client.newWebSocket(request, listener);

        // Subscribe to a market ticker or other channels
//        String subscribeMessage = "{\"event\":\"subscribe\", \"pair\":[\"XBT/USD\"], \"subscription\":{\"name\":\"ticker\"}}";
//        webSocket.send(subscribeMessage);

        // Subscribe to the order book for BTC/USD and ETH/USD
//        String subscribeMessage = "{\"event\":\"subscribe\", \"pair\":[\"BTC/USD\", \"ETH/USD\"], \"subscription\":{\"name\":\"book\"}}";
        //  Subscribe to the order book for BTC/USD and ETH/USD v2
        String subscribeMessage = "{\n" +
                "    \"method\": \"subscribe\",\n" +
                "    \"params\": {\n" +
                "        \"channel\": \"book\",\n" +
                "        \"symbol\": [\n" +
                "            \"BTC/USD\",\n" +
                "            \"ETH/USD\"\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        webSocket.send(subscribeMessage);
    }

    public static void main(String[] args) {
        KrakenWebSocketClient client = new KrakenWebSocketClient();
        client.start();
    }

    private static class KrakenWebSocketListener extends WebSocketListener {
        private MessagetService messagetService = new DefaultMessagetService();
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            System.out.println("WebSocket Opened");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            System.out.println("Received text: " + text);
            messagetService.read(text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            System.out.println("Received bytes: " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(1000, null);
            System.out.println("WebSocket Closing: " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            t.printStackTrace();
        }
    }
}

