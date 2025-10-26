import java.util.*;

public class TurnManager {
    private JavaGame game;
    private JavaSwing swing;
    private Bot bot;
    private Actions actions;

    public List<Integer> hand;
    public int round = 0;
    public int raise = 0;
    public boolean folded = false;
    private boolean playerTurn = true;

    public TurnManager(JavaGame game, JavaSwing swing, Bot bot, Actions actions) {
        this.game = game;
        this.swing = swing;
        this.bot = bot;
        this.actions = actions;

        swing.setActions(actions);
        actions.setTurnManager(this);
        bot.setTurnManager(this);
    }

    public void startGame() {
        game.runSinglePlayer();
        hand = new ArrayList<>(game.allCards);
        round = 0;
        playerTurn = true;
        folded = false;

        bot.action(hand);
        updateGUI();
    }

    public void playerDidAction() {
        if (folded) return;
        playerTurn = false;
        nextTurn();
    }

    public void nextTurn() {
        if (folded) return;

        if (!playerTurn) {
            botTurn();
        } else {
            updateGUI();
        }
    }

    private void botTurn() {
        if (folded) return;

        bot.action(hand);
        playerTurn = true;
        updateGUI();
    }

    private void updateGUI() {
        swing.run(hand);
    }

    public void playerFolded() {
        folded = true;
        swing.setStatusText("Player folded! Bot wins.");
    }

    public void botFolded() {
        folded = true;
        swing.setStatusText("Bot folded! Player wins.");
    }

    public void playerRaised() {
        this.raise = 0; // for simplicity
    }

    public int getValue(List<Integer> hand) {
        return new Cards().getValue(hand, hand.subList(2, hand.size()));
    }
}
