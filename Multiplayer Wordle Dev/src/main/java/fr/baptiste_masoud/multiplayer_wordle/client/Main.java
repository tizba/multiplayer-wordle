package fr.baptiste_masoud.multiplayer_wordle.client;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
