import java.util.Scanner;

public class Start {

    public static void main(String[] args) {
        // Launch the GUI selection screen
        javax.swing.SwingUtilities.invokeLater(StartGUI::new);
    }

    public static void runSinglePlayer() {
        JavaGame game = new JavaGame();
        JavaSwing swing = new JavaSwing(null); // single-player, no client
        Bot bot = new Bot(swing);
        Actions actions = new Actions(null, bot, swing);
        TurnManager manager = new TurnManager(game, swing, bot, actions);
        bot.setTurnManager(manager);
        manager.startGame();
    }

    public static void runMultiplayerLAN() {
        try {
            JavaSwing swing = new JavaSwing(null);
            MultiplayerClientGUI clientGUI = new MultiplayerClientGUI(swing);
            clientGUI.startClient(); // uses auto IP for local LAN
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runOnlineMultiplayer() {
        // Currently same as LAN (auto IP detection)
        runMultiplayerLAN();
    }
}
