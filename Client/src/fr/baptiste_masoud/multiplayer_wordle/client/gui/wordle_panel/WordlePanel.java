package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WordlePanel extends JPanel {
    private final PlayerPanel playerPanel;
    private final PlayerPanel opponentPanel;

    public WordlePanel(GUIController guiController) {
        this.setLayout(new GridLayout(1, 2, 50, 0));
        this.setBorder(new EmptyBorder(0, 50, 0, 50));

        this.playerPanel = new PlayerPanel(guiController);
        add(this.playerPanel);

        this.opponentPanel = new OpponentPlayerPanel(guiController);
        add(this.opponentPanel);
    }

    public void updateWithGameState(GameStateData gameStateData) {
        assert gameStateData.currentRound() != null;
        this.playerPanel.updateWithGameState(gameStateData.currentRound());
        this.opponentPanel.updateWithGameState(gameStateData.currentRound());
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public PlayerPanel getOpponentPanel() {
        return opponentPanel;
    }
}
