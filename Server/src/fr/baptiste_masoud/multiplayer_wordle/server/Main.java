package fr.baptiste_masoud.multiplayer_wordle.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(5000);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
