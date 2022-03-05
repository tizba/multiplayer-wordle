package fr.baptiste_masoud.multiplayer_wordle.client.gui.wordle_panel;

import javax.swing.*;
import java.awt.*;

public class SubmissionsPanel extends JPanel {

    public SubmissionsPanel() {
        setLayout(new GridLayout(6,1, 0, 10));
        for (int i = 0; i < 6; i++) {
            add(new SubmissionRow("Bonjou"));
        }
        setBackground(Color.black);
    }
}
