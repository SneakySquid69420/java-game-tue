import java.util.*;

/**
 * Standard deck of cards.
 * @author Lago van der Meer
 * @id 2324717
 * @author Daan Michielse
 * @id 2318644
 */

public class Cards {
    static String[] suits = {"clubs", "diamonds", "hearts", "spades"};
    static String[] rank = {"2", "3", "4", "5", "6", "7", "8", "9", "10", 
        "jack", "queen", "king", "ace"};

    // private int suitsInx;
    private int rankInx;
  

    /**
     * Determines the suit and rank of a card based on its index in the deck.
     * @param deckInx The index of the card in the deck.
     */
    public void run(int deckInx) {
        // suitsInx = getSuitsInx(deckInx); 
        rankInx = getRankInx(deckInx);
        System.out.println(rankInx);
        // System.out.println(rank[rankInx] + " of " + suits[suitsInx]);
    }

    /**
     * Determines the suit index of a card based on its index in the deck.
     * @param deckInx The index of the card in the deck.
     */
    private int getSuitsInx(int deckInx) {
        int suitsInx = deckInx / 13;
        return suitsInx;
    }

    /**
     * Determines the rank index of a card based on its index in the deck.
     * @param deckInx The index of the card in the deck.
     */
    private int getRankInx(int deckInx) {
        int rankInx = deckInx % 13;
        return rankInx;
    }

    /**
     * Checks if the given card index conflicts with any already used cards.
     * @param deckInx The index of the card to check.
     * @param used A collection of already used card indices.
     * @return true if there is handS0 conflict, false otherwise.
     */
    public boolean conflict(int deckInx, Collection<Integer> used) {
        boolean conflict = false;
        for (int use : used) {
            if (deckInx == use) {
                conflict = true;
            }
        }
        return conflict;
    }

    /**
     * Calculates the value of a hand combined with table cards.
     * @param hand A list of two card indices representing the player's hand.
     * @param table A list of five card indices representing the table cards.
     * @return The calculated value of the hand.
     */
    public int getValue(List<Integer> hand, List<Integer> table) {
        int value = 0;
        int counter = 0;
        boolean flush = false;
        boolean straight = true;

        while (counter < 2) {
            value += getRankInx(hand.get(counter)) + 1;
            counter++;
        }
        System.out.println("value after first: " + value);

        int[] sort = new int[hand.size() + table.size()];
        int[] sortDeckInx = new int[hand.size() + table.size()];
        // Set all variables in an array for sorting
        for (int i = 0; i < hand.size(); i++) {
            sort[i] = getRankInx(hand.get(i));
        }
        for (int i = 0; i < table.size(); i++) {
            sort[i + 2] = getRankInx(table.get(i));
        }

        for (int i = 0; i < hand.size(); i++) {
            sortDeckInx[i] = hand.get(i);
        }
        for (int i = 0; i < table.size(); i++) {
            sortDeckInx[i + 2] = table.get(i);
        }

        sortArray(sortDeckInx);
        sortArray(sort);
        for (int i = 0; i < sort.length; i++) {
            System.out.print(sort[i] + "(" + getRankInx(sort[i]) + ")");
            if (i < 2) {
                System.out.println(suits[getSuitsInx(hand.get(i))]);
            } else {
                System.out.println(suits[getSuitsInx(table.get(i - 2))]);
            }
        }

        List<Integer> sorted = new ArrayList<>();
        for (int i : sort) {
            sorted.add(i);
        }
        boolean pairs = false;
        List<Integer> pair = new ArrayList<>();
        boolean threeOfAKind = false;
        boolean fourOfAKind = false;
        Map<Integer, Integer> rankCounts = new HashMap<>();

        for (int card : sorted) {
            int rank = getRankInx(card);
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
            if (rankCounts.get(rank) == 2) {
                pair.add(rank);
                pairs = true;
            } else if (rankCounts.get(rank) == 3) {
                threeOfAKind = true;
            } else if (rankCounts.get(rank) == 4) {
                fourOfAKind = true;
            }
        }
        // Check for pair and double pair
        if (pair.size() == 1) {
            value += 10;
        } else if (pair.size() >= 2) {
            value += 20;
        }
        // System.out.println("value after pairs: " + value);
        // Check for three of a kind
        if (threeOfAKind) {
            value += 30;
        }
        // System.out.println("value after three of a kind: " + value);
        // Check for full house
        if (pairs && threeOfAKind) {
            value += 60;
        }
        // System.out.println("value after full house: " + value);
        // Check for four of a kind
        if (fourOfAKind) {
            value += 70;
        }
        // System.out.println("value after four of a kind: " + value);
        // Check for flush
        if (checkForFlush(sortDeckInx)) {
            value += 50;
            flush = true;
        }
        // System.out.println("value after flush: " + value);

        System.out.println(removeDup(sort));

        // Check for straight
        if (checkForStraight(sort)) {
            value += 40;
            straight = true;
        }
        // System.out.println("value after straight: " + value);
        
        // Check for straight flush
        if  (straight && flush) {
            value += 80;
        }
        // System.out.println("value after straight flush: " + value);

        // Check for royal flush
        if (straight && flush && removeDup(sort).contains(12) && removeDup(sort).contains(11)) {
            value += 1000; 
        }
        System.out.println("value after royal flush: " + value);
        return value;
    }

    private boolean checkForFlush(int[] cards) {
        Map<Integer, Integer> suitCounts = new HashMap<>();

        for (int card : cards) {
            int suit = getSuitsInx(card);
            suitCounts.put(suit, suitCounts.getOrDefault(suit, 0) + 1);
            if (suitCounts.get(suit) >= 5) {
                return true; 
            }
        }
        return false;
    }

    private boolean checkForStraight(int[] cards) {
        
        List<Integer> noDup = removeDup(cards);
        if (noDup.size() < 5) {
            return false;
        }

        // Check for ace, special case
        if (noDup.contains(12) && noDup.contains(0) && noDup.contains(1) && noDup.contains(2)
            && noDup.contains(3)) {
            return true;
        }
        
        int streak = 1;
        for (int i = 1; i < noDup.size(); i++) {
            if (noDup.get(i) == noDup.get(i - 1) + 1) {
                streak++;
                if (streak == 5) {
                    return true;
                }
            } else {
                streak = 1;
            }
        }
        return false;
    }

    private List<Integer> removeDup(int [] sort) {
        List<Integer> noDup = new ArrayList<>();
        for (int i : sort) {
            boolean isDuplicate = false;
            for (int j : noDup) {
                if (j == i) {
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {
                noDup.add(i);
            }
        }
        return noDup;
    }

    // private Set<Integer> allDup(Collection<Integer> cards) {
    //     Map<Integer, Integer> rankCount = new HashMap<>();
    //     Set<Integer> duplicates = new HashSet<>();

    //     for (int card : cards) {
    //         int rank = getRankInx(card);
    //         rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
    //         if (rankCount.get(rank) == 2) { 
    //             duplicates.add(rank);
    //         }
    //     }
    //     return duplicates;
    // }

    /**
     * Sorts an array of card indices based on their rank.
     * @param sort The array of card indices to sort.
     * @return The sorted array of card indices.
     */
    public int[] sortArray(int[] sort) {
        int minJ;
        for (int i = 0; i < sort.length; i++) {
            minJ = i;
            for (int j = i + 1; j < sort.length; j++) {
                if (getRankInx(sort[j]) < getRankInx(sort[minJ])) {
                    minJ = j;
                }
            }
            int temp = sort[i];
            sort[i] = sort[minJ];
            sort[minJ] = temp;
        }
        return sort;
    }

    /**
     * Generates the image filename for a card based on its index in the deck.
     * @param deckInx The index of the card in the deck.
     * @return The filename of the card image.
     */
    public String toImage(int deckInx) {
        String image;
        int rankInx = getRankInx(deckInx);
        int suitsInx = getSuitsInx(deckInx);
        if (rankInx > 8 && rankInx < 12) {
            image = "./playing_cards/" + rank[rankInx] + "_of_" + suits[suitsInx] + "2.png";
        } else {
            image = "./playing_cards/" + rank[rankInx] + "_of_" + suits[suitsInx] + ".png";
        }
        return image;
    }
}
