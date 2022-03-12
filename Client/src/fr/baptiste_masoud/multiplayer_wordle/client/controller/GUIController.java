package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.DisconnectMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.SetNameMessage;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.SubmissionMessage;

import javax.swing.*;
import java.io.IOException;

public class GUIController {
    private final Controller controller;
    private GUI gui;

    public GUIController(Controller controller) {
        this.controller = controller;
    }

    public void connectToServer(String address, int port) {
        try {
            controller.setServerConnection(new ServerConnection(address, port, controller));
        } catch (IOException e) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Connection to server failed, verify address and port…"));
            JOptionPane.showConfirmDialog(null, panel, "Connection to server failed…",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void disconnect() {
        controller.getServerConnection().getMessageSender().sendMessage(new DisconnectMessage());
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    public void sendName(String name) {
        if (controller.getServerConnection() == null) return;
        controller.getServerConnection().getMessageSender().sendMessage(new SetNameMessage(name));
    }

    public void sendSubmission(String submittedWord) {
        controller.getGui().getWordlePanel().getPlayerPanel().getSubmissionErrorLabel().setVisible(false);
        if (controller.getServerConnection() == null) return;
        controller.getServerConnection().getMessageSender().sendMessage(new SubmissionMessage(submittedWord));

    }
}
