import java.util.*;

/**
 * The bot logic for the poker game.
 */
public class Bot {
    Cards cards = new Cards();
    Actions actions = new Actions();
    JavaServer server = new JavaServer();

    /**
     * Determines the bot's action based on its hand.
     * @param botHand The list of card indices representing the bot's hand and table cards.
     */
    void action(List<Integer> botHand) {
        List<Integer> hand = new ArrayList<>();
        List<Integer> table = new ArrayList<>();
        hand.add(botHand.get(2));
        hand.add(botHand.get(3));

        for (int i = 4; i < botHand.size(); i++) {
            table.add(botHand.get(i));
        }
        int botValue = cards.getValue(hand, table);
        
    }
}
