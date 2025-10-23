import java.util.*;

public class TurnManager {
    private JavaGame game;
    private JavaSwing swing;
    private Bot bot;
    private Actions actions;

    private List<Integer> hand; // full hand with player, bot, and table cards
    private int round = 0; // 0 = pre-flop, 1 = flop, 2 = turn, 3 = river, 4 = end
    private boolean playerTurn = true;
    public boolean folded = false; // flag to track if someone folded

    public TurnManager(JavaGame game, JavaSwing swing, Bot bot, Actions actions) {
        this.game = game;
        this.swing = swing;
        this.bot = bot;
        this.actions = actions;

        this.swing.setActions(actions); // let GUI talk to Actions
        this.actions.setTurnManager(this); // allow Actions to notify TurnManager
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
        if (folded) {
            return; // no further actions if folded
        }
        playerTurn = false;
        nextTurn();
    }

    /**
     * Advances the game to the next turn or round.
     */
    public void nextTurn() {
        if (folded) {
            System.out.println("Game ended due to fold.");
            swing.disableActions();
            return;
        }

        if (round < 3) {
            round++;
            swing.round = round;
        }
        
        if (!playerTurn) {
            botTurn();
        } else {
            updateGUI();
        }
    }

    /**
     * Handles the bot's turn.
     */
    private void botTurn() {
        if (folded) {
            return;
        }

        bot.action(hand);
        playerTurn = true;
        updateGUI();
    }

    /**
     * Updates the GUI to reflect the current game state.
     */
    private void updateGUI() {
        swing.run(hand); 
    }

    /**
     * Called by Actions when player folds.
     */
    public void playerFolded() {
        folded = true;
        System.out.println("Player folded! Bot wins.");
        // update GUI to show fold status or disable buttons
        updateGUI();
    }

    /**
     * Called by Bot when bot folds.
     */
    public void botFolded() {
        folded = true;
        System.out.println("Bot folded! Player wins.");
        // update GUI to show fold status or disable buttons
        updateGUI();
    }
}


// Bot class with fold support
class Bot {
    Cards cards = new Cards();
    JavaSwing swing;
    private Actions actions;
    private int botValue;
    private TurnManager turnManager;

    /**
     * Constructs a Bot with access to client, GUI, and turn manager.
     * @param client The client for network communication.
     * @param swing The JavaSwing GUI instance.
     * @param turnManager The TurnManager controlling game flow.
     */
    public Bot(Client client, JavaSwing swing, TurnManager turnManager) {
        this.actions = new Actions(client, swing, this);
        this.swing = swing;
        this.turnManager = turnManager;
    }

    /**
     * Determines the bot's action based on its hand.
     * @param botHand The list of card indices representing the bot's hand and table cards.
     */
    void action(List<Integer> botHand) {
        if (turnManager.folded) {
            return;
        }

        List<Integer> hand = new ArrayList<>();
        List<Integer> table = new ArrayList<>();
        hand.add(botHand.get(2));
        hand.add(botHand.get(3));

        for (int i = 4; i < botHand.size(); i++) {
            table.add(botHand.get(i));
        }
        botValue = cards.getValue(hand, table);
        int money = swing.opponentMoney;

        if (botValue < 20 && money >= 20) {
            swing.opponentMoney -= 20;
            swing.potMoney += 20;
            swing.setStatusText("Bot raised by €20");
        } else if (botValue < 50 && money >= 40) {
            swing.opponentMoney -= 40;
            swing.potMoney += 40;
            swing.setStatusText("Bot raised by €40");
        } else if (botValue < 100 && money >= 60) {
            swing.opponentMoney -= 60;
            swing.potMoney += 60;
            swing.setStatusText("Bot raised by €60");
        } else if (botValue > 99 && money >= 80) {
            swing.opponentMoney -= 80;
            swing.potMoney += 80;
            swing.setStatusText("Bot raised by €80");
        } else {
            fold();
        }
    }

    /**
     * Bot calls the raise.
     * @param raised The amount to call.
     */
    public void raise(int raised) {
        if (botValue != 0 && swing.opponentMoney >= raised) {
            swing.opponentMoney -= raised;
            swing.potMoney += raised;
        } else {
            fold();
        }
    }

    /**
     * Bot folds the game.
     */
    public void fold() {
        swing.setStatusText("The bot folded");
        turnManager.botFolded();
    }
}
