
public class TurnManager {
    private JavaGame game;
    private JavaSwing swing;
    private Bot bot;
    private Actions actions;

    public int raise = 50; // default call amount
    public boolean folded = false;

    public TurnManager(JavaGame game, JavaSwing swing, Bot bot, Actions actions) {
        this.game = game;
        this.swing = swing;
        this.bot = bot;
        this.actions = actions;
        actions.setTurnManager(this);
    }

    public void startGame() {
        game.runSinglePlayer();
        bot.action(game.allCards);
        swing.run(game.allCards);
    }

    public void playerDidAction() {
        if (folded) return;
        bot.action(game.allCards);
        swing.run(game.allCards);
    }

    public void playerFolded() {
        folded = true;
        swing.setStatusText("Player folded. Bot wins!");
    }
}
