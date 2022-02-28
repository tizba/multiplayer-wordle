package fr.baptiste_masoud.multiplayer_wordle.client.gui;

import fr.baptiste_masoud.multiplayer_wordle.client.controller.Controller;
import fr.baptiste_masoud.multiplayer_wordle.client.controller.GUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MyMenuBar extends JMenuBar {
    private final GUIController guiController;

    private JMenu menuConnection;
    private JMenuItem menuConnectTo;
    private JMenuItem menuDisconnect;

    public MyMenuBar(GUIController guiController) {
        this.guiController = guiController;

        // Connection JMenu
        JMenu menuConnection = new JMenu( "Connection" );
        menuConnectTo = new JMenuItem("Connect to server…");
        menuConnectTo.addActionListener(this::menuConnectTo);
        menuDisconnect = new JMenuItem("Disconnect");
        menuDisconnect.setEnabled(false);
        menuDisconnect.addActionListener(this::menuDisconnect);
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

    private void menuConnectTo(ActionEvent event)  {
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
            guiController.connectToServer(addressTextField.getText(), Integer.valueOf(portTextField.getText()));
        }
    }

    private void menuDisconnect(ActionEvent event) {
        guiController.disconnect();
    }
}
