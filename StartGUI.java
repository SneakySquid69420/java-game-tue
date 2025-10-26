import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StartGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartGUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Poker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Select Game Mode", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiplayerButton = new JButton("Multiplayer");

        frame.add(singlePlayerButton);
        frame.add(multiplayerButton);

        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Start.runSinglePlayer();
            }
        });

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Start.runMultiplayer();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
