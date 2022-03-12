package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SubmitTextField extends JTextField {
    public SubmitTextField(GUIController guiController) {
        this.setFont(new Font("Arial", Font.PLAIN, 28));
        this.setHorizontalAlignment(JTextField.HORIZONTAL);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(getText().length() >= 6)
                    e.consume();
            }
        });

        addActionListener(e -> {
            guiController.sendSubmission(getText().toUpperCase());
            setText("");

        });
    }
}
