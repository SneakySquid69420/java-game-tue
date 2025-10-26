import javax.swing.*;
import java.awt.*;

public class StartGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Poker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel title = new JLabel("Select Game Mode", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(title);

        JButton singlePlayer = new JButton("Single Player");
        JButton onlineMultiplayer = new JButton("Online Multiplayer");

        frame.add(singlePlayer);
        frame.add(onlineMultiplayer);

        // Single Player Action
        singlePlayer.addActionListener(e -> {
            frame.dispose();
            runSinglePlayer();
        });

        // Online Multiplayer Action
        onlineMultiplayer.addActionListener(e -> {
            frame.dispose();
            runOnlineMultiplayer();
        });

        frame.setVisible(true);
    }

    private static void runSinglePlayer() {
        JavaGame game = new JavaGame();
        JavaSwing swing = new JavaSwing(null); // single-player, no client
        Bot bot = new Bot(swing);
        Actions actions = new Actions(null, swing, bot);
        TurnManager manager = new TurnManager(game, swing, bot, actions);
        bot.setTurnManager(manager);
        manager.startGame(); // properly starts the game
    }

    private static void runOnlineMultiplayer() {
        try {
            JavaSwing swing = new JavaSwing(null);
            MultiplayerClientGUI clientGUI = new MultiplayerClientGUI(swing);
            clientGUI.startClient(); // fetches IP automatically and connects
            JOptionPane.showMessageDialog(null, "Connected to server. Wait for your turn.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
