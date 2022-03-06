package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.GameState;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.ServerToClientMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageSender {
    private final ObjectOutputStream objectOutputStream;

    public MessageSender(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public void sendMessage(ServerToClientMessage message) {
        try {
            this.objectOutputStream.writeObject(message);
            System.out.println("Message sent: " + message.getMessageType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
