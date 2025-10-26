public class Start {

    public static void runSinglePlayer() {
        JavaGame game = new JavaGame();           // creates the deck and deals cards
        JavaSwing swing = new JavaSwing(null);     // GUI
        Bot bot = new Bot(swing);                  // bot only needs GUI in single-player
        Actions actions = new Actions(swing, bot); // no client needed
        TurnManager manager = new TurnManager(game, swing, bot, actions);
        bot.setTurnManager(manager);

        // Start the game (deals cards and calls bot first turn)
        manager.startGame();
    }

    public static void runMultiplayer() {
        try {
            JavaSwing swing = new JavaSwing(null);
            MultiplayerClientGUI clientGUI = new MultiplayerClientGUI(swing);
            clientGUI.startClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
