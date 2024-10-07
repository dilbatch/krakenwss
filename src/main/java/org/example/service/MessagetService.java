package org.example.service;

/**
 * Message service. Wrap all websocket response message methods.
 * Put all related websocket response message in here
 */
public interface MessagetService {
    /**
     * Read message service
     * @param message any string message
     */
    void read(String message);
}
