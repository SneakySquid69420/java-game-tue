import java.util.*;

/**
 * The starting point of the poker game.
 */
public class Start {

    JavaGame game = new JavaGame();
    Client client = new Client();
    JavaSwing swing = new JavaSwing(client);
    Bot bot = new Bot(swing);
    Actions actions = new Actions(client, swing, bot);

    // private void run() { DIT IS VOOR MULTIPLAYER
    //     swing.setActions(actions);
    //     Thread serverThread = new Thread(() -> {
    //         try {
    //             Server.main(new String[]{});
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }); 
    //     serverThread.start();
    //     try {
    //         Thread.sleep(200);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     Thread clientThread = new Thread(() -> {
    //         try {
    //             client.main(new String[]{});
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }); 
    //     clientThread.start();
        
    //     game.runSinglePlayer();
    //     List<Integer> hands = new ArrayList<>(game.allCards);
    //     swing.run(hands);
    //     bot.action(hands);
       
    // }

    public static void main(String[] args) {
        JavaGame game = new JavaGame();
        Client client = new Client();
        JavaSwing swing = new JavaSwing(client);
        Bot bot = new Bot(swing);
        Actions actions = new Actions(client, swing, bot);

        TurnManager manager = new TurnManager(game, swing, bot, actions);
        bot.setTurnManager(manager);
        manager.startGame();
    }
}