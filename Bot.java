import java.util.*;

public class Bot {
    private Cards cards = new Cards();
    private JavaSwing swing;
    private TurnManager turnManager;
    private int botValue;
    private boolean first = true;

    public Bot(JavaSwing swing) {
        this.swing = swing;
    }

    public void setTurnManager(TurnManager manager) {
        this.turnManager = manager;
    }

    public void action(List<Integer> botHand) {
        if (turnManager.folded) return;

        List<Integer> hand = new ArrayList<>();
        List<Integer> table = new ArrayList<>();
        hand.add(botHand.get(2));
        hand.add(botHand.get(3));

        for (int i = 4; i < botHand.size(); i++) {
            table.add(botHand.get(i));
        }

        botValue = cards.getValue(hand, table);
        int money = swing.opponentMoney;

        if (first) {
            first = false;
            swing.setStatusText("Game started!");
        } else if (botValue < 100 && money >= 20) {
            swing.opponentMoney -= 20;
            swing.potMoney += 20;
            swing.setStatusText("Bot raised €20");
        } else if (botValue < 250 && money >= 40) {
            swing.opponentMoney -= 40;
            swing.potMoney += 40;
            swing.setStatusText("Bot raised €40");
        } else if (botValue < 500 && money >= 60) {
            swing.opponentMoney -= 60;
            swing.potMoney += 60;
            swing.setStatusText("Bot raised €60");
        } else if (botValue >= 500 && money >= 80) {
            swing.opponentMoney -= 80;
            swing.potMoney += 80;
            swing.setStatusText("Bot raised €80");
        } else {
            fold();
        }
    }

    public void fold() {
        swing.setStatusText("Bot folded!");
        turnManager.botFolded();
    }
}
