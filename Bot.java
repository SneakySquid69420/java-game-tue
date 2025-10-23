import java.util.*;

/**
 * The bot logic for the poker game.
 */
public class Bot {
    Cards cards = new Cards();
    JavaSwing swing;
    // private Actions actions;
    private int botValue;
    private TurnManager turnManager;
    private boolean first = true;

    public Bot(JavaSwing swing) {
        // this.actions = new Actions(client, swing);
        this.swing = swing;
    }

    public void setTurnManager(TurnManager manager) {
        this.turnManager = manager;
    }

    /**
     * Determines the bot's action based on its hand.
     * @param botHand The list of card indices representing the bot's hand and table cards.
     */
    void action(List<Integer> botHand) {
        List<Integer> hand = new ArrayList<>();
        List<Integer> table = new ArrayList<>();
        hand.add(botHand.get(2));
        hand.add(botHand.get(3));
        Random generator  = new Random();
        int random = generator.nextInt(2);


        for (int i = 4; i < botHand.size(); i++) {
            table.add(botHand.get(i));
        }
        botValue = cards.getValue(hand, table);
        int money = swing.opponentMoney;
        if (!first && random == 1) {
            if (botValue < 100 && money >= 20) {
                swing.opponentMoney -= 20;
                swing.potMoney += 20;
                swing.setStatusText("The bot raised by €20");
            } else if (botValue < 250 && money >= 40) {
                swing.opponentMoney -= 40;
                swing.potMoney += 40;
                swing.setStatusText("The bot raised by €40");
            } else if (botValue < 500 && money >= 60) {
                swing.opponentMoney -= 60;
                swing.potMoney += 60;
                swing.setStatusText("The bot raised by €60");
            } else if (botValue > 499 && money >= 80) {
                swing.opponentMoney -= 80;
                swing.potMoney += 80;
                swing.setStatusText("The bot raised by €80");
            }
        } else if (first) {
            first = false;
            swing.setStatusText("The game started");
        } else if (random == 0) {
            swing.setStatusText("The bot called");
            swing.opponentMoney -= 50;
            swing.potMoney += 50;
        }
    }

    public void raise(int raised) {
        if (botValue != 0) {
            if (swing.opponentMoney >= raised) {
                swing.opponentMoney -= raised;
                swing.potMoney += raised;
                swing.setStatusText("The bot called");
            } else {
                turnManager.botFolded();
            }
        }
    }
}

