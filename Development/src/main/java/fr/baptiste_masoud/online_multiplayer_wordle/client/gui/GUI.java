package fr.baptiste_masoud.online_multiplayer_wordle.client.gui;

import fr.baptiste_masoud.online_multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.online_multiplayer_wordle.client.gui.wordle_panel.WordlePanel;
import fr.baptiste_masoud.online_multiplayer_wordle.messages.game_state.GameStateData;

import javax.swing.*;

public class GUI extends JFrame {
    private final MyMenuBar myMenuBar;
    private final WordlePanel wordlePanel;
    private final JLabel waitingLabel;

    public GUI() {
        super("Multiplayer Wordle");
        ConnectionController connectionController = new ConnectionController(this);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 800);

        this.myMenuBar = new MyMenuBar(connectionController);
        setJMenuBar(myMenuBar);

        this.wordlePanel = new WordlePanel(connectionController);
        this.wordlePanel.setVisible(false);

        this.waitingLabel = new JLabel("Waiting for opponent...", SwingConstants.CENTER);
        this.setContentPane(waitingLabel);
        this.waitingLabel.setVisible(false);
        setVisible(true);
    }

    public WordlePanel getWordlePanel() {
        return wordlePanel;
    }

    public MyMenuBar getMyMenuBar() {
        return myMenuBar;
    }

    public void updateWithGameStateData(GameStateData gameStateData) {
        this.waitingLabel.setVisible(gameStateData.waitingForOpponent());

        // when the game is launched
        if (gameStateData.running() && !wordlePanel.isVisible()) {
            this.setContentPane(wordlePanel);
            wordlePanel.setVisible(true);
        }

        // when the game stops
        if (!gameStateData.running()) {
            this.setContentPane(this.waitingLabel);
            wordlePanel.setVisible(false);
        }

        // update wordlePanel if game is running
        if (gameStateData.running())
            this.wordlePanel.updateWithGameState(gameStateData);

        this.revalidate();
    }
}
