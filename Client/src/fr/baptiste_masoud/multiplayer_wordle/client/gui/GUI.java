package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel.WordlePanel;
import fr.baptiste_masoud.multiplayer_wordle.messages.game_state.GameStateData;

import javax.swing.*;

public class GUI extends JFrame {
    private final MyMenuBar menuBar;
    private WordlePanel wordlePanel;
    private final GUIController guiController;
    private GameStateData previousGameStateData;

    public GUI(GUIController guiController) {
        super("Multiplayer Wordle");
        this.guiController = guiController;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);

        this.menuBar = new MyMenuBar(guiController);
        setJMenuBar(menuBar);

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
        if (gameStateData.running() && (previousGameStateData == null || !previousGameStateData.running())) {
            this.wordlePanel = new WordlePanel(guiController);
            setContentPane(wordlePanel);
        }

        // when the game stops
        if (previousGameStateData != null && !gameStateData.running() && previousGameStateData.running()) {
            this.getContentPane().setVisible(false);
        }

        // update wordlePanel if game is running
        if (gameStateData.running())
            this.wordlePanel.updateWithGameState(gameStateData);

        this.previousGameStateData = gameStateData;
        this.revalidate();
    }
}
