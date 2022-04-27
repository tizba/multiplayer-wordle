package fr.baptiste_masoud.multiplayer_wordle.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) {
        try {
            Server server = new Server(5000);
            server.start();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
