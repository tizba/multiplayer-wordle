package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.server.game.Submission;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Submission submission = new Submission("ABBEY", "abiey");


        Server server = new Server();
        try {
            server.launch(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
