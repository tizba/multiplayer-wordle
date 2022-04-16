package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.connection_controller.ConnectionController;
import fr.baptiste_masoud.multiplayer_wordle.messages.c_to_s.DisconnectMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

public class MyMenuBar extends JMenuBar {
    private final ConnectionController connectionController;

    private final JMenuItem menuConnectTo;
    private final JMenuItem menuDisconnect;

    public MyMenuBar(ConnectionController connectionController) {
        this.connectionController = connectionController;

        // Connection JMenu
        JMenu menuConnection = new JMenu("Connection");
        menuConnection.setMnemonic('E');
        menuConnectTo = new JMenuItem("Connect to server…");
        menuConnectTo.addActionListener(event1 -> menuConnectTo());
        menuConnectTo.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.ALT_DOWN_MASK));
        menuDisconnect = new JMenuItem("Disconnect");
        menuDisconnect.setEnabled(false);
        menuDisconnect.addActionListener(event -> menuDisconnect());
        menuDisconnect.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK));
        menuConnection.add(menuConnectTo);
        menuConnection.add(menuDisconnect);
        add(menuConnection);
        setVisible(true);
    }

    public JMenuItem getMenuConnectTo() {
        return menuConnectTo;
    }

    public JMenuItem getMenuDisconnect() {
        return menuDisconnect;
    }

    private void menuConnectTo()  {
        JTextField addressTextField = new JTextField("127.0.0.1");
        JTextField portTextField = new JTextField("5000");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("IP address:"));
        panel.add(addressTextField);
        panel.add(new JLabel("Port number:"));
        panel.add(portTextField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Connect to server",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                connectionController.connect(addressTextField.getText(), Integer.parseInt(portTextField.getText()));
                this.menuConnectTo.setEnabled(false);
                this.menuDisconnect.setEnabled(true);
            } catch (IOException e) {
                panel = new JPanel();
                panel.add(new JLabel("Connection to server failed, verify address and port…"));
                JOptionPane.showConfirmDialog(null, panel, "Connection to server failed…",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                this.menuDisconnect.setEnabled(false);
                this.menuConnectTo.setEnabled(true);
            }
        }
    }

    private void menuDisconnect() {
        connectionController.sendMessage(new DisconnectMessage());
    }
}
