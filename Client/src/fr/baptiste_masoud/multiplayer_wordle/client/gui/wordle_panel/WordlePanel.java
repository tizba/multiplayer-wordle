package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.messages.GameState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WordlePanel extends JPanel {
    private final GUIController guiController;

    private final PlayerPanel playerPanel;
    private final PlayerPanel opponentPanel;

    public WordlePanel(GUIController guiController, GameState gameState) {
        this.guiController = guiController;

        this.setLayout(new GridLayout(1, 2, 50, 0));
        this.setBorder(new EmptyBorder(0, 50, 0, 50));

        this.playerPanel = new PlayerPanel(guiController);
        add(this.playerPanel);

        this.opponentPanel = new OpponentPlayerPanel(guiController);
        add(this.opponentPanel);
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public PlayerPanel getOpponentPanel() {
        return opponentPanel;
    }
}
