package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.ContinueMessage;

import javax.swing.*;

public class ContinueButton extends JButton {
    public ContinueButton(ConnectionController connectionController) {
        super("Continue");

        this.addActionListener(e -> connectionController.sendMessage(new ContinueMessage()));
    }
}
