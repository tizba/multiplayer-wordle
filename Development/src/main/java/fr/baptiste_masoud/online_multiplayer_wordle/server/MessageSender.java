package fr.baptiste_masoud.online_multiplayer_wordle.server;

import fr.baptiste_masoud.online_multiplayer_wordle.messages.s_to_c.ServerToClientMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;

public record MessageSender(ObjectOutputStream objectOutputStream) {
    private static final Logger messageSenderLogger = LogManager.getLogger(MessageSender.class);

    public void sendMessage(ServerToClientMessage message) {
        try {
            this.objectOutputStream.writeObject(message);
            messageSenderLogger.debug("Message sent: {}", message.getMessageType());
        } catch (IOException ioException) {
            messageSenderLogger.error(ioException);
        }
    }
}
