import java.awt.*;
import java.util.List;
import javax.swing.*;

public class JavaSwing {
    public int potMoney = 20;
    public int playerMoney = 5000;
    public int opponentMoney = 5000;
    private JLabel statusLabel = new JLabel("Game started");
    private Actions actions;

    public JavaSwing(Object client) { }

    public void setActions(Actions actions) { this.actions = actions; }

    public void run(List<Integer> hand) {
        JFrame frame = new JFrame("Poker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JPanel infoPanel = new JPanel();
        infoPanel.add(new JLabel("Your money: €" + playerMoney));
        infoPanel.add(new JLabel("Pot: €" + potMoney));
        infoPanel.add(statusLabel);
        frame.add(infoPanel);

        JPanel cardPanel = new JPanel();
        for (int i = 0; i < 2; i++) cardPanel.add(new JLabel("Card " + i));
        frame.add(cardPanel);

        JPanel buttonPanel = new JPanel();
        JButton check = new JButton("Check");
        JButton call = new JButton("Call");
        JButton raise = new JButton("Raise");
        JButton fold = new JButton("Fold");

        buttonPanel.add(check); buttonPanel.add(call);
        buttonPanel.add(raise); buttonPanel.add(fold);

        check.addActionListener(e -> { actions.check(); actions.nextTurn(); });
        call.addActionListener(e -> { actions.call(); actions.nextTurn(); });
        raise.addActionListener(e -> { actions.raise(50); actions.nextTurn(); });
        fold.addActionListener(e -> { actions.fold(); actions.nextTurn(); });

        frame.add(buttonPanel);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public void setStatusText(String text) { statusLabel.setText(text); }
}
