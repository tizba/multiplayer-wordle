package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel.WordlePanel;
import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;

import javax.swing.*;

public class GUI extends JFrame {
    private final MyMenuBar menuBar;
    private final WordlePanel wordlePanel;

    public GUI() {
        super("Multiplayer Wordle");
        ConnectionController connectionController = new ConnectionController(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);

        this.menuBar = new MyMenuBar(connectionController);
        setJMenuBar(menuBar);

        this.wordlePanel = new WordlePanel(connectionController);
        this.setContentPane(wordlePanel);
        this.wordlePanel.setVisible(false);

        setVisible(true);
    }

    public WordlePanel getWordlePanel() {
        return wordlePanel;
    }

    public MyMenuBar getMyMenuBar() {
        return menuBar;
    }

    public void updateWithGameStateData(GameStateData gameStateData) {
        // when the game is launched
        if (gameStateData.running() && !wordlePanel.isVisible()) {
            wordlePanel.setVisible(true);
        }

        // when the game stops
        if (!gameStateData.running()) {
            wordlePanel.setVisible(false);
        }

        // update wordlePanel if game is running
        if (gameStateData.running())
            this.wordlePanel.updateWithGameState(gameStateData);

        this.revalidate();
    }
}
