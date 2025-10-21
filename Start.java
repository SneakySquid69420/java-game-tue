import java.util.*;

/**
 * The starting point of the poker game.
 */
public class Start {
    JavaGame game = new JavaGame();
    JavaSwing swing = new JavaSwing();
    JavaServer server = new JavaServer();
    Bot bot = new Bot();

    private void run() {
        game.runSinglePlayer();
        List<Integer> hands = new ArrayList<>(game.allCards);
        swing.run(hands);
        bot.action(hands);
    }
    
    public static void main(String[] args) {
        new Start().run();
    }
}
