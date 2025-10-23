import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    public int pot;
    public int round; // 0 = pre-flop, 1 = flop, 2 = turn, 3 = river
    public String currentPlayer;
    public Map<String, Integer> playerMoney; // playerName -> money
    public Map<String, List<Integer>> playerHands; // playerName -> hand
    public List<Integer> tableCards; // shared cards

    public GameState() { }
}
