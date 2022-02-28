package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.DisconnectMessage;

import java.io.IOException;

public class Controller {
    private GUI gui;
    private ServerConnection serverConnection;
    private GUIController guiController = new GUIController(this);

    public void setGui(GUI gui) {
        this.gui = gui;
        this.guiController.setGui(gui);
    }

    public GUI getGui() {
        return gui;
    }

    public void connectToServer(String address, int port) {
        try {
            this.serverConnection = new ServerConnection(address, port, this);
            gui.getMyMenuBar().getMenuConnectTo().setEnabled(false);
        } catch (IOException e) {
            this.serverConnection = null;
        }
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void setServerConnection(ServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public GUIController getGuiController() {
        return this.guiController;
    }
}
