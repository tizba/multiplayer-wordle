package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel.WordlePanel;
import fr.baptiste_masoud.multiplayer_wordle.messages.GameState;
import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.GameStateMessage;

import javax.swing.*;

public class GUI extends JFrame {
    private final MyMenuBar menuBar;
    private WordlePanel wordlePanel;
    private final GUIController guiController;
    private GameState previousGameState;

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

    public void updateWithGameState(GameState gameState) {
        if (gameState.isRunning()) {
            this.wordlePanel = new WordlePanel(guiController, gameState);
            setContentPane(wordlePanel);
        }
        if (!gameState.isRunning()) {
            this.getContentPane().setVisible(false);
        }

        this.previousGameState = gameState;
        this.revalidate();
    }
}
