package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.TooManyPlayersMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

// Server class
public class Server {
    private Game game;
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void launch(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        System.out.println("Waiting for new client connections...");
        running = true;
        while (running) {
            Socket clientSocket = serverSocket.accept();
            if (this.game == null) {
                newGame();
            }
            handleNewClient(clientSocket);
        }
    }

    /**
     * Creates a new game
     * If a game is already on, it disconnects all Players before creating a new one
     */
    public void newGame() {
        if (this.game != null) {
            game.disconnectPlayerClients();
        }
        System.out.println("New game !");
        this.game = new Game(this);
    }

    /**
     * Add the client to the game if 2 players are not already connected
     * Send a TooManyPlayersMessage to the client if 2 players are already connected
     * @param clientSocket to socket of a client
     */
    private void handleNewClient(Socket clientSocket) {
        try {
            if (game.getPlayerClient1() == null || game.getPlayerClient2() == null) {
                PlayerClient playerClient = new PlayerClient(clientSocket);
                game.addPlayerClient(playerClient);
                playerClient.start();
            } else {
                MessageSender messageSender = new MessageSender(new ObjectOutputStream(clientSocket.getOutputStream()));
                messageSender.sendMessage(new TooManyPlayersMessage());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}