package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class NameTextField extends JTextField {
    private final GUIController guiController;

    public NameTextField(GUIController guiController) {
        super("Change your name");
        this.guiController = guiController;
        setHorizontalAlignment(JTextField.HORIZONTAL);
        setBorder(new EmptyBorder(0,0,0,0));
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, 16));

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                guiController.sendName(getText());
            }

        });
    }
}
