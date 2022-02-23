package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.TooManyPlayersMessage;

import java.io.*;
import java.net.*;

// Server class
public class Server {
    private PlayerClient playerClient1 = null;
    private PlayerClient playerClient2 = null;
    private Game game;

    public Server() throws IOException {
        // server is listening on port 5000
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        System.out.println("Waiting for new client connections...");
        // running infinite loop for getting clients
        while (true) {
            Socket clientSocket = null;
            // socket object to receive incoming client requests
            clientSocket = serverSocket.accept();
            handleNewClient(clientSocket);
        }
    }

    private void handleNewClient(Socket clientSocket) throws IOException {
        System.out.println("A new client is connected: " + clientSocket.toString());

        if (playerClient1 == null) {
            playerClient1 = new PlayerClient(clientSocket);
            playerClient1.start();
        } else if (playerClient2 == null) {
            playerClient2 = new PlayerClient(clientSocket);
            playerClient2.start();
        } else {
            System.out.println("2 Players are already connected, sending TooManyPlayersMessage to:" + clientSocket.toString());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(new TooManyPlayersMessage());
            return;
        }


        if (playerClient1 != null && playerClient2 != null) {
            game = new Game(playerClient1, playerClient2);
            playerClient1.setGame(game);
            playerClient2.setGame(game);
        }
    }
}