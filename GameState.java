import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable {
    public String currentPlayer;
    public int pot = 0;
    public List<Integer> tableCards = new ArrayList<>();
}
