package fr.baptiste_masoud.server;

import java.io.*;
import java.net.Socket;


class PlayerClient extends Thread {

    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private boolean connected = true;
    private String playerName;
    private Game game;

    public PlayerClient(Socket socket) throws IOException {
        this.socket = socket;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while (connected) {
            System.out.print(this.getName() + " is running");
            if (game != null) System.out.print(" on Game: " + game.toString());
            System.out.println();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
