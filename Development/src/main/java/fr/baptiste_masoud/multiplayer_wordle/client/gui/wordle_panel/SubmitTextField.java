package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.SubmissionMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SubmitTextField extends JTextField {
    public SubmitTextField(ConnectionController connectionController) {
        this.setFont(new Font("Arial", Font.PLAIN, 28));
        this.setHorizontalAlignment(SwingConstants.HORIZONTAL);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (getText().length() >= 6)
                    e.consume();
            }
        });

        addActionListener(e -> {
            connectionController.sendMessage(new SubmissionMessage(getText()));
            setText("");
        });
    }
}
