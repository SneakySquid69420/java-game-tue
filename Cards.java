import java.util.*;

public class Cards {
    static String[] suits = {"clubs", "diamonds", "hearts", "spades"};
    static String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10",
            "jack", "queen", "king", "ace"};

    private int getRankInx(int deckInx) { return deckInx % 13; }
    private int getSuitsInx(int deckInx) { return deckInx / 13; }

    public int getValue(List<Integer> hand, List<Integer> table) {
        int value = 0;
        for (int card : hand) value += getRankInx(card) + 1;

        int[] combined = new int[hand.size() + table.size()];
        for (int i = 0; i < hand.size(); i++) combined[i] = hand.get(i);
        for (int i = 0; i < table.size(); i++) combined[i + hand.size()] = table.get(i);

        Arrays.sort(combined, Comparator.comparingInt(this::getRankInx));

        Map<Integer, Integer> rankCount = new HashMap<>();
        for (int c : combined) rankCount.put(getRankInx(c), rankCount.getOrDefault(getRankInx(c), 0) + 1);

        if (rankCount.containsValue(4)) value += 700;
        else if (rankCount.containsValue(3) && rankCount.containsValue(2)) value += 600;
        else if (rankCount.containsValue(3)) value += 300;
        else if (Collections.frequency(new ArrayList<>(rankCount.values()), 2) == 2) value += 200;
        else if (rankCount.containsValue(2)) value += 100;

        if (checkForFlush(combined)) value += 500;
        if (checkForStraight(combined)) value += 400;

        return value;
    }

    private boolean checkForFlush(int[] cards) {
        Map<Integer, Integer> suits = new HashMap<>();
        for (int c : cards) {
            suits.put(getSuitsInx(c), suits.getOrDefault(getSuitsInx(c), 0) + 1);
            if (suits.get(getSuitsInx(c)) >= 5) return true;
        }
        return false;
    }

    private boolean checkForStraight(int[] cards) {
        List<Integer> ranks = new ArrayList<>();
        for (int c : cards) if (!ranks.contains(getRankInx(c))) ranks.add(getRankInx(c));
        Collections.sort(ranks);
        int streak = 1;
        for (int i = 1; i < ranks.size(); i++) {
            if (ranks.get(i) == ranks.get(i - 1) + 1) streak++;
            else streak = 1;
            if (streak >= 5) return true;
        }
        return false;
    }

    public String toImage(int deckInx) {
        int r = getRankInx(deckInx);
        int s = getSuitsInx(deckInx);
        return "./playing_cards/" + rank[r] + "_of_" + suits[s] + ".png";
    }
}
