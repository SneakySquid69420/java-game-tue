import java.util.List;

/**
 * Handles player actions and interactions with the TurnManager.
 */
public class Actions {
    private JavaSwing swing;
    private TurnManager turnManager;
    private Bot bot;
    public boolean playerCalled = false;
    public boolean botRaised = false;

    /**
     * Constructor for Actions.
     * @param swing The JavaSwing instance.
     * @param bot The Bot instance.
     */
    public Actions(JavaSwing swing, Bot bot) {
        this.swing = swing;
        this.bot = bot;
    }

    /**
     * Sets the TurnManager for Actions.
     * @param manager The TurnManager instance.
     */
    public void setTurnManager(TurnManager manager) {
        this.turnManager = manager;
    }

    public void updateBotRaised() {
        if (turnManager.raise > 0) {
            botRaised = true;
        }
    }

    /**
     * Calculates the value of a given hand.
     * @param hand The list of card indices representing the hand and table cards.
     * @return The calculated value of the hand.
     */
    public int getValue(List<Integer> hand) {
        int value = 0;
        value = turnManager.getValue(hand);
        return value;
    }

    /**
     * Handles player calling action.
     */
    public void call() {
        turnManager.updateRaise();
        int toCall = turnManager.raise;
        System.out.println("Player called.");
        swing.potMoney += toCall;
        swing.playerMoney -= toCall;
        playerCalled = true;
    }

    /**
     * Handles player checking action.
     */
    public void check() {
        playerCalled = false;
        System.out.println("Player checked.");
    }

    /**
     * Handles player raising action.
     * @param raise The amount the player wants to raise.
     */
    public void raise(int raise) {
        System.out.println("Player raised: " + raise);
        playerCalled = false;
        swing.playerMoney -= raise;
        swing.potMoney += raise;
        bot.raise(raise);
        turnManager.playerRaised();
    }

    /**
     * Handles player folding action.
     */
    public void fold() {
        playerCalled = false;
        swing.setStatusText("Player folded.");
        turnManager.playerFolded();
    }

    /**
     * Advances the game to the next turn.
     */
    public void nextTurn() {
        if (turnManager != null) {
            turnManager.playerDidAction();
        }
    }
}
