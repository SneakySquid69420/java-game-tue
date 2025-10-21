import java.util.*;
/**
 * The game logic.
 * 
 * @author Lago van der Meer
 * @id 2324717
 * @author Daan Michielse
 * @id 2318644
 */

public class JavaGame {

    Random randomGenerator;
    Cards cards = new Cards();
    public List<Integer> allCards = new ArrayList<>();

    /**
     * The main game loop.
     */
    public void runSinglePlayer() {
        randomGenerator = new Random();
        List<Integer> usedCards = new ArrayList<>();
        int cardsLeft = 52 - usedCards.size();

        int random = randomGenerator.nextInt(cardsLeft); 
        int amountOfCards = 4; 
        int counter = 0;

        List<Integer> tableCards = new ArrayList<>();
        List<List<Integer>> allPairs = new ArrayList<>();
        List<Integer> totalValues = new ArrayList<>();

        // Get as many random cards as needed
        while (counter < amountOfCards) {
            cardsLeft = 52 - usedCards.size();
            boolean conflict = cards.conflict(random, usedCards);
            if (!conflict) {
                cards.run(random);
                usedCards.add(random);
                counter++;
                random = randomGenerator.nextInt(cardsLeft);
            } else {
                random = randomGenerator.nextInt(cardsLeft);
            }
        }
        
 
        // Set all pairs of hands in a list
        for (int i = 0; i < usedCards.size(); i += 2) {
            int first = usedCards.get(i);
            int second = usedCards.get(i + 1);
            allPairs.add(Arrays.asList(first, second));
        }

        // Get the 5 random table cards
        counter = 0;
        random = randomGenerator.nextInt(cardsLeft);
        while (counter < 5) {
            boolean conflict = cards.conflict(random, usedCards);
            if (!conflict) {
                cardsLeft = 52 - usedCards.size();
                tableCards.add(random);
                usedCards.add(random);
                random = randomGenerator.nextInt(cardsLeft);
                counter++;
            } else {
                random = randomGenerator.nextInt(cardsLeft);
            }
        }
        for (int i : usedCards) {
            allCards.add(i);
        }
        
        // System.out.println(tableCards);
        for (int i : tableCards) {
            System.out.println("table:");
            cards.run(i);
        }

        // Calculate the value of each hand combined with the table cards
        for (int i = 0; i < allPairs.size(); i++) {
            List<Integer> hand = new ArrayList<>();
            hand.addAll(allPairs.get(i));
            totalValues.add(cards.getValue(hand, tableCards));
            hand.clear();
        }
    }

    /**
     * Gebruik voor usedCards en cards.toImage();
     * Clones a list of integers.
     * @param list The list to clone.
     * @return A new list that is a clone of the input list.
     */
    public List<Integer> cloneList(List<Integer> list) {
        List<Integer> clone = new ArrayList<>();
        clone.addAll(list);
        return clone;
    }

    /**
     * The main method to run the JavaGame program.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new JavaGame().runSinglePlayer();
    }
}