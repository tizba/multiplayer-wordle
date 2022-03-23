package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.TooManyPlayersMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    private Game game;
    private boolean running;
    private final int port;

    public Server(int port) {
        this.port = port;
    }

    /**
     * Starts the server, it is now listening on his port and waiting for new client connections to handle them
     * @throws IOException if the server is unable to listen on his port
     * or if an I/O error occurs while waiting for clients
     */
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Listening on port " + serverSocket.getLocalPort());
        running = true;

        Thread userInputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (running) {
                String input = scanner.next();
                if (Objects.equals(input.toUpperCase(), "STOP")) {
                    running = false;
                    System.exit(0);
                }
            }
        });

        userInputThread.start();

        while (running) {
            Socket clientSocket = serverSocket.accept();
            if (this.game == null) {
                initNewGame();
            }
            handleNewClient(clientSocket);
        }
    }

    /**
     * Initializes a new game
     */
    public void initNewGame() {
        System.out.println("New game !");
        this.game = new Game(this);
    }

    /**
     * Adds the client to the game if 2 players are not already connected<br/>
     * Sends a TooManyPlayersMessage to the client if 2 players are already connected
     * @param clientSocket the socket of the new client
     */
    private void handleNewClient(Socket clientSocket) {
        try {
            Player player = new Player(game, clientSocket);
            if (game.isFullOfPlayers()) {
                player.getMessageSender().sendMessage(new TooManyPlayersMessage());
            } else {
                game.addPlayer(player);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}