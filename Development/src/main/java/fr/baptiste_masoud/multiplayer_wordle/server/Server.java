package fr.baptiste_masoud.multiplayer_wordle.server;

import fr.baptiste_masoud.multiplayer_wordle.messages.s_to_c.TooManyPlayersMessage;
import fr.baptiste_masoud.multiplayer_wordle.server.game.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


public class Server {
    private static final Logger serverLogger = LogManager.getLogger(Server.class);
    private final int port;
    private final List<String> words;
    private Game game;

    public Server(int port) throws IOException {
        this.port = port;
        this.words = initWords();
    }

    private List<String> initWords() throws IOException {
        List<String> localWordList = new ArrayList<>();
        InputStream inputStream = Server.class.getClassLoader().getResourceAsStream("words_list.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("words_list.txt not found in resources folder");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String line;
        while ((line = reader.readLine()) != null) {
            localWordList.add(line);
        }
        return localWordList;
    }

    /**
     * Starts the server, it is now listening on his port and waiting for new client connections to handle them
     *
     * @throws IOException if the server is unable to listen on his port
     *                     or if an I/O error occurs while waiting for clients
     */
    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverLogger.info("Listening on port {}", serverSocket.getLocalPort());
            Boolean running = true;

            new Thread(new CheckUserInputForStop(running)).start();

            //noinspection UnnecessaryUnboxing,ConstantConditions
            while (running.booleanValue()) {
                Socket clientSocket = serverSocket.accept();
                if (this.game == null) {
                    initNewGame();
                }
                handleNewClient(clientSocket);
            }
        }
    }

    /**
     * Initializes a new game
     */
    public void initNewGame() {
        serverLogger.info("New game !");
        this.game = new Game(this, words);
    }

    /**
     * Adds the client to the game if 2 players are not already connected<br/>
     * Sends a TooManyPlayersMessage to the client if 2 players are already connected
     *
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

    private static class CheckUserInputForStop implements Runnable {
        private Boolean running;

        public CheckUserInputForStop(Boolean running) {
            this.running = running;
        }

        public void run() {
            Scanner scanner = new Scanner(System.in);
            while (Boolean.TRUE.equals(running)) {
                String input = scanner.next();
                if (Objects.equals(input.toUpperCase(), "STOP")) {
                    running = false;
                    System.exit(0);
                }
            }
        }
    }
}