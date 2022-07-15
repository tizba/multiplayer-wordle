package fr.baptiste_masoud.online_multiplayer_wordle;

import fr.baptiste_masoud.online_multiplayer_wordle.client.gui.GUI;
import fr.baptiste_masoud.online_multiplayer_wordle.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;


public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        final String SERVER_ARG = "server";
        final String CLIENT_ARG = "client";

        // no args
        if (args.length == 0) {
            launchServer(5000);
            launchClient();
            launchClient();
        } else if (args.length == 1) {
            if (Objects.equals(args[0], SERVER_ARG))
                launchServer(5000);
            else if (Objects.equals(args[0], CLIENT_ARG))
                launchClient();
        } else if (args.length == 2) {
            if (Objects.equals(args[0], SERVER_ARG))
                launchServer(Integer.parseInt(args[1]));
            else if (Objects.equals(args[0], CLIENT_ARG) && Objects.equals(args[1], CLIENT_ARG)) {
                launchClient();
                launchClient();
            }
        }
    }

    private static void launchServer(int port) {
        Thread serverThread = new Thread(new LaunchServerRunnable(port), "Server Thread");
        serverThread.start();
    }

    private static void launchClient() {
        Thread clientThread = new Thread(new LaunchClientRunnable());
        clientThread.start();
    }

    private static class LaunchServerRunnable implements Runnable {
        int port;

        LaunchServerRunnable(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                Server server = new Server(port);
                server.start();
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static class LaunchClientRunnable implements Runnable {
        @Override
        public void run() {
            SwingUtilities.invokeLater(GUI::new);
        }
    }
}
