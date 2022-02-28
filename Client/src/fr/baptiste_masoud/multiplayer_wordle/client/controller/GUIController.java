package fr.baptiste_masoud.multiplayer_wordle.client.controller;

import fr.baptiste_masoud.multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.DisconnectMessage;

import java.io.IOException;

public class GUIController {
    private final Controller controller;
    private GUI gui;


    public GUIController(Controller controller) {
        this.controller = controller;
    }

    public void connectToServer(String address, int port) {
        try {
            controller.setServerConnection(new ServerConnection(address, port, controller));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        controller.getServerConnection().getMessageSender().sendMessage(new DisconnectMessage());
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }
}
