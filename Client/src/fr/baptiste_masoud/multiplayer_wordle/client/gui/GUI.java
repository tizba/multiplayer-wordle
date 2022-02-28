package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.Controller;
import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

import javax.swing.*;

public class GUI extends JFrame {
    private final GUIController guiController;

    private MyMenuBar menuBar;

    public GUI(GUIController guiController) {
        super("Multiplayer Wordle");
        this.guiController = guiController;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,800);

        menuBar = new MyMenuBar(guiController);
        setJMenuBar(menuBar);

        setVisible(true);
    }

    public MyMenuBar getMyMenuBar() {
        return menuBar;
    }
}
