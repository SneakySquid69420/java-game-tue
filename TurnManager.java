import java.util.*;

public class TurnManager {
    private JavaGame game;
    private JavaSwing swing;
    public Bot bot;
    private Actions actions;

    private List<Integer> hand; // full hand with player, bot, and table cards
    public int round = 0; // 0 = pre-flop, 1 = flop, 2 = turn, 3 = river, 4 = end
    private boolean playerTurn = true;
    public boolean folded = false; // flag to track if someone folded
    public int raise = 0;
    
    /**
     * Constructor for TurnManager.
     * @param game The JavaGame instance.
     * @param swing The JavaSwing instance.
     * @param bot The Bot instance.
     * @param actions The Actions instance.
     */
    public TurnManager(JavaGame game, JavaSwing swing, Bot bot, Actions actions) {
        this.game = game;
        this.swing = swing;
        this.bot = bot;
        this.actions = actions;

        this.swing.setActions(actions); 
        this.actions.setTurnManager(this); 
    }

    /**
     * Starts a new game.
     */
    public void startGame() {
        game.runSinglePlayer();
        hand = new ArrayList<>(game.allCards);
        round = 0;
        playerTurn = true;
        folded = false;
        bot.action(hand);
        updateGUI();
    }

    /**
     * Calculates the value of a given hand.
     * @param hands The list of card indices representing the hand and table cards.
     * @return The calculated value of the hand.
     */
    public int getValue(List<Integer> hands) {
        List<Integer> hand = new ArrayList<>();
        List<Integer> table = new ArrayList<>(hands);

        hand.add(hands.get(0));
        hand.add(hands.get(1));
        table.removeFirst();
        table.removeFirst();
        int value = 0;
        value = game.cards.getValue(hand, table);
        return value;
    }

    /**
     * Called by Actions when player completes an action.
     */
    public void playerDidAction() {
        if (folded) {
            return; // no further actions if folded
        }
        playerTurn = false;
        nextTurn();
    }

    /**
     * Advances the game to the next turn or round.
     */
    public void nextTurn() {
        if (folded) {
            System.out.println("Game ended due to fold.");
            endOfRound();
            return;
        }
        updateRaise();
        boolean check = bot.checked;

        if (!playerTurn && !actions.playerCalled) {
            botTurn();
            if (round < 3 && (raise == 0 || check)) {
                round++;
                swing.round = round;
            } else if (round >= 3 || swing.opponentMoney == 0 || swing.playerMoney == 0) {
                endOfRound();
            }
        } else if (!playerTurn && actions.playerCalled) {
            if (round < 3) {
                round++;
                swing.round = round;
                updateGUI();
            } else {
                endOfRound();
            }
        } else {
            updateGUI();
        }
    }

    /**
     * Called by Actions when player raises.
     */
    public void playerRaised() {
        if (round < 3) {
            round++;
            swing.round = round;
        } else if (round >= 3 || swing.opponentMoney == 0 || swing.playerMoney == 0) {
            endOfRound();
        }
        updateGUI();
    }

    public void updateRaise() {
        raise = bot.randomRaise;
    }

    /**
     * Handles the bot's turn.
     */
    private void botTurn() {
        if (folded) {
            return;
        }
        bot.action(hand);
        playerTurn = true;
        updateGUI();
    }

    /**
     * Updates the GUI to reflect the current game state.
     */
    public void updateGUI() {
        swing.run(hand); 
    }

    /**
     * Called by Actions when player folds.
     */
    public void playerFolded() {
        folded = true;
        swing.playerFolded = true;
        endOfRound();
    }

    private void endOfRound() {
        swing.disableActions();
        swing.round = 4;
        round = 4;
        swing.setStatusText("The round ended");
        updateGUI();
    }

    /**
     * Called by Bot when bot folds.
     */
    public void botFolded() {
        swing.botFolded = true;
        folded = true;
        endOfRound();
    }
}