package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;
import fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel.WordlePanel;

import javax.swing.*;

public class GUI extends JFrame {
    private final MyMenuBar menuBar;
    private final WordlePanel wordlePanel;

    public GUI(GUIController guiController) {
        super("Multiplayer Wordle");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);

        this.menuBar = new MyMenuBar(guiController);
        setJMenuBar(menuBar);

        this.wordlePanel = new WordlePanel(guiController);
        setContentPane(wordlePanel);

        setVisible(true);
    }

    public WordlePanel getWordlePanel() {
        return wordlePanel;
    }

    public MyMenuBar getMyMenuBar() {
        return menuBar;
    }
}
