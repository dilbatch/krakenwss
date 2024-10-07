package org.example;

import org.example.service.MessagetService;
import org.example.service.impl.DefaultMessagetService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class WebSocketExchangeClient implements WebSocket.Listener {

    MessagetService messagetService = new DefaultMessagetService();
    private WebSocket webSocket;

    public void connect(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket.Builder builder = client.newWebSocketBuilder();
        this.webSocket = builder.buildAsync(URI.create(uri), this).join();
        System.out.println("Connected to WebSocket: " + uri);
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.sendText(message, true);
            System.out.println("Message sent: " + message);
        } else {
            System.err.println("WebSocket is not connected.");
        }
    }

    public void close() {
        if (webSocket != null) {
            webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Bye").thenRun(() -> System.out.println("WebSocket closed"));
        }
    }

    // This method is triggered when a message is received
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Received message: " + data);
        messagetService.read(data.toString());
        webSocket.request(1);  // Request the next message
        return null;
    }

    // Handle WebSocket errors
    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.err.println("WebSocket error: " + error.getMessage());
    }

    // Handle WebSocket closures
    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed. Status: " + statusCode + ", Reason: " + reason);
        return null;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket connection opened.");
        webSocket.request(1);  // Request the first message
    }
}

