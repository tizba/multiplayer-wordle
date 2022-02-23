package fr.baptiste_masoud.multiplayer_wordle.client;

import fr.baptiste_masoud.multiplayer_wordle.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReader extends Thread {
    private final ObjectInputStream objectInputStream;
    private final Client client;

    public MessageReader(Client client, ObjectInputStream objectInputStream) {
        this.client = client;
        this.objectInputStream = objectInputStream;
    }

    @Override
    public void run() {
        while (this.isAlive()) {
            try {
                Message message = (Message) objectInputStream.readObject();
                handleMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(Message message) {
        System.out.println("Handling new message");
        switch (message.getMessageType()) {
            case TOO_MANY_PLAYERS -> {
                System.out.println("Two players are already playing… try later");
                System.exit(0);
            }
        }
    }
}
