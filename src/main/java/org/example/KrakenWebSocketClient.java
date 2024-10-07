package org.example;


/**
 * An Implementation of Kraken WebSocket client application.
 */
public class KrakenWebSocketClient {

    public static void main(String[] args) {
        WebSocketExchangeClient client = new WebSocketExchangeClient();

        // Connect to a WebSocket server (replace with actual exchange URI)
        client.connect("wss://ws.kraken.com/v2");

        // Send a subscription message (example for subscribing to a market ticker)
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
        client.sendMessage(subscribeMessage);

        // Wait for a while to receive messages
        try {
            Thread.sleep(10000); // Keep the application running for 10 seconds to receive messages
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the WebSocket connection
        client.close();
    }
}

