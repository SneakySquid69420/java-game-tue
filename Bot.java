import java.util.ArrayList;
import java.util.List;

public class Bot {
    private Cards cards = new Cards();
    private JavaSwing swing;
    private int botValue;
    private TurnManager turnManager;

    public Bot(JavaSwing swing) { this.swing = swing; }

    public void setTurnManager(TurnManager manager) { this.turnManager = manager; }

    public void action(List<Integer> hand) {
        List<Integer> botHand = new ArrayList<>();
        List<Integer> table = new ArrayList<>();
        botHand.add(hand.get(2));
        botHand.add(hand.get(3));
        for (int i = 4; i < hand.size(); i++) table.add(hand.get(i));

        botValue = cards.getValue(botHand, table);

        if (botValue < 100) swing.setStatusText("Bot checks");
        else {
            int raise = Math.min(botValue / 10, swing.opponentMoney);
            swing.opponentMoney -= raise;
            swing.potMoney += raise;
            swing.setStatusText("Bot raised: €" + raise);
        }
    }

    public void raise(int amount) {
        if (swing.opponentMoney >= amount) {
            swing.opponentMoney -= amount;
            swing.potMoney += amount;
        } else fold();
    }

    private void fold() {
        swing.setStatusText("Bot folded!");
        turnManager.playerFolded();
    }
}
