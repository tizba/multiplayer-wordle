package fr.baptiste_masoud.multiplayer_wordle.client;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.Controller;
import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        GUI gui = new GUI(controller.getGuiController());
        controller.setGui(gui);
    }
}
