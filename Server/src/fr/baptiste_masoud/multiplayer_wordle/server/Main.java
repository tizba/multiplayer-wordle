package fr.baptiste_masoud.multiplayer_wordle.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.launch(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
