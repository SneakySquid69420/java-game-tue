import java.util.*;

public class JavaGame {
    public List<Integer> allCards = new ArrayList<>();
    private Cards cards = new Cards();
    private Random random = new Random();

    public void runSinglePlayer() {
        Set<Integer> used = new HashSet<>();
        allCards.clear();

        // Player hand
        allCards.add(getUniqueCard(used));
        allCards.add(getUniqueCard(used));

        // Bot hand
        allCards.add(getUniqueCard(used));
        allCards.add(getUniqueCard(used));

        // Table cards (5)
        for (int i = 0; i < 5; i++) {
            allCards.add(getUniqueCard(used));
        }
    }

    private int getUniqueCard(Set<Integer> used) {
        int card;
        do {
            card = random.nextInt(52);
        } while (used.contains(card));
        used.add(card);
        return card;
    }
}
