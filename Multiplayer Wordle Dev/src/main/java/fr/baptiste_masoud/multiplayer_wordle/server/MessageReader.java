package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.MessageType;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ClientToServerMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.SetNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.SubmissionMessage;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReader extends Thread {
    private final ObjectInputStream objectInputStream;
    private final Player player;

    public MessageReader(ObjectInputStream objectInputStream, Player player) {
        this.objectInputStream = objectInputStream;
        this.player = player;
    }

    @Override
    public void run() {
        while (player.isConnected()) {
            try {
                ClientToServerMessage message = (ClientToServerMessage) objectInputStream.readObject();
                handleMessage(message);

                if (message.getMessageType() != MessageType.SET_NAME)
                    player.getGame().sendGameStateMessage();
            } catch (IOException e) {
                player.disconnect();
                player.getGame().end();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(ClientToServerMessage message) {
        switch (message.getMessageType()) {
            case DISCONNECT ->
                player.getGame().end();

            case SET_NAME -> {
                SetNameMessage setNameMessage = (SetNameMessage) message;
                player.setName(setNameMessage.getName());
                player.getGame().sendNameToOpponent(player);
            }

            case SUBMISSION -> {
                SubmissionMessage submissionMessage = (SubmissionMessage) message;
                player.getGame().addSubmission(player, submissionMessage.getSubmittedWord());
            }
        }
    }
}
