public class Actions {
    private Client client;
    private JavaSwing swing;
    private TurnManager turnManager;
    private Bot bot;

    public Actions(Client client, JavaSwing swing, Bot bot) {
        this.client = client;
        this.swing = swing;
        this.bot = bot;
    }

    public void setTurnManager(TurnManager manager) {
        this.turnManager = manager;
    }

    public void call() {
        System.out.println("Player called.");
        swing.potMoney += 50;
        swing.playerMoney -= 50;
    }

    public void check() {
        System.out.println("Player checked.");
    }

    public void raise(int raise) {
        System.out.println("Player raised: " + raise);
        swing.playerMoney -= raise;
        swing.potMoney += raise;
        bot.raise(raise);
    }

    public void fold() {
        swing.setStatusText("Player folded.");
        turnManager.playerFolded();
    }

    public void nextTurn() {
        if (turnManager != null) {
            turnManager.playerDidAction();
        }
    }
}
