import java.util.List;

/**
 * Handles player actions and interactions with the TurnManager.
 */
public class Actions {
    private JavaSwing swing;
    private TurnManager turnManager;
    private Bot bot;
    public boolean playerCalled = false;

    public Actions(JavaSwing swing, Bot bot) {
        this.swing = swing;
        this.bot = bot;
    }

    public void setTurnManager(TurnManager manager) {
        this.turnManager = manager;
    }

    public int getValue(List<Integer> hand) {
        return turnManager.getValue(hand);
    }

    public void call() {
        int toCall = turnManager.raise;
        swing.potMoney += toCall;
        swing.playerMoney -= toCall;
        playerCalled = true;
        swing.setStatusText("Player called.");
    }

    public void check() {
        playerCalled = false;
        swing.setStatusText("Player checked.");
    }

    public void raise(int raise) {
        swing.playerMoney -= raise;
        swing.potMoney += raise;
        turnManager.playerRaised();
        swing.setStatusText("Player raised: €" + raise);
    }

    public void fold() {
        playerCalled = false;
        swing.setStatusText("Player folded.");
        turnManager.playerFolded();
    }

    public void nextTurn() {
        turnManager.playerDidAction();
    }
}
