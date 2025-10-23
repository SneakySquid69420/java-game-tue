import java.io.Serializable;

public class PlayerAction implements Serializable {
    public String playerName;
    public ActionType action;
    public int amount;

    public enum ActionType { FOLD, CALL, RAISE, CHECK }

    public PlayerAction(String playerName, ActionType action, int amount) {
        this.playerName = playerName;
        this.action = action;
        this.amount = amount;
    }
}
