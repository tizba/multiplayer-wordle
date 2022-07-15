package fr.baptiste_masoud.multiplayer_wordle.client.connection_controller;

import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ClientToServerMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;

public record MessageSender(ObjectOutputStream objectOutputStream) {
    private static final Logger messageSenderLogger = LogManager.getLogger(MessageSender.class);

    public void sendMessage(ClientToServerMessage message) {
        try {
            messageSenderLogger.debug("Message sent: {}", message.getMessageType());
            this.objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}