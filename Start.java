/**
 * The starting point of the poker game.
 */
public class Start {

    public static void main(String[] args) {
        JavaGame game = new JavaGame();
        JavaSwing swing = new JavaSwing();
        Bot bot = new Bot(swing);
        Actions actions = new Actions(swing, bot);

        TurnManager manager = new TurnManager(game, swing, bot, actions);
        bot.setTurnManager(manager);
        manager.startGame();
    }
}