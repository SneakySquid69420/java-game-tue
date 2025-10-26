
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

    public void call() {
        int toCall = turnManager.raise;
        swing.potMoney += toCall;
        swing.playerMoney -= toCall;
        playerCalled = true;
        swing.setStatusText("Player called");
    }

    public void check() {
        playerCalled = false;
        swing.setStatusText("Player checked");
    }

    public void raise(int amount) {
        swing.potMoney += amount;
        swing.playerMoney -= amount;
        bot.raise(amount);
        playerCalled = false;
        turnManager.playerDidAction();
        swing.setStatusText("Player raised: €" + amount);
    }

    public void fold() {
        playerCalled = false;
        swing.setStatusText("Player folded");
        turnManager.playerFolded();
    }

    public void nextTurn() { turnManager.playerDidAction(); }
}
