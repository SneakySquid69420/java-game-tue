import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Persistent Swing GUI for the poker game.
 */
public class JavaSwing {
    private Client client;
    private Actions actions;
    private boolean buttonsEnabled = true;

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel statusLabel;
    private JLabel potLabel;
    private JLabel playerMoneyLabel;
    private JLabel opponentMoneyLabel;
    private JLabel playerCardsLabel;
    private JLabel tableCardsLabel;
    private JSlider raiseSlider;
    private JButton checkButton, callButton, raiseButton, foldButton;

    public int round = 0;
    public int potMoney = 20;
    public int playerMoney = 5000;
    public int opponentMoney = 5000;

    private Cards cards = new Cards();

    public JavaSwing(Client client) {
        this.client = client;
        initGUI();
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    private void initGUI() {
        frame = new JFrame("Poker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 900);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 5, 10, 10));
        frame.setContentPane(mainPanel);

        // Labels
        statusLabel = new JLabel("Game started!");
        potLabel = new JLabel("Pot: €" + potMoney);
        potLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        playerMoneyLabel = new JLabel("Your money: €" + playerMoney);
        playerMoneyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        opponentMoneyLabel = new JLabel("Opponent money: €" + opponentMoney);
        opponentMoneyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        playerCardsLabel = new JLabel();
        tableCardsLabel = new JLabel();

        // Slider
        raiseSlider = new JSlider(0, playerMoney);
        raiseSlider.setPaintTrack(true);
        raiseSlider.setPaintTicks(true);
        raiseSlider.setPaintLabels(true);

        raiseSlider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            if (!source.getValueIsAdjusting()) {
                int value = source.getValue();
                statusLabel.setText("Your bet = €" + value);
            }
        });

        // Buttons
        checkButton = new JButton("Check");
        callButton = new JButton("Call");
        raiseButton = new JButton("Raise");
        foldButton = new JButton("Fold");

        checkButton.addActionListener(e -> {
            actions.check();
            actions.nextTurn();
        });
        callButton.addActionListener(e -> {
            actions.call();
            actions.nextTurn();
        });
        raiseButton.addActionListener(e -> {
            actions.raise(raiseSlider.getValue());
            actions.nextTurn();
        });
        foldButton.addActionListener(e -> {
            actions.fold();
            actions.nextTurn();
        });

        // Add components to panel
        mainPanel.add(statusLabel);
        mainPanel.add(potLabel);
        mainPanel.add(playerMoneyLabel);
        mainPanel.add(opponentMoneyLabel);
        mainPanel.add(playerCardsLabel);
        mainPanel.add(tableCardsLabel);
        mainPanel.add(raiseSlider);
        mainPanel.add(checkButton);
        mainPanel.add(callButton);
        mainPanel.add(raiseButton);
        mainPanel.add(foldButton);

        frame.setVisible(true);
    }

    /**
     * Updates the displayed cards and money.
     * @param hand Full hand with player, bot, and table cards
     */
    public void run(List<Integer> hand) {
        try {
            // Player cards
            BufferedImage img1 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(hand.get(0))));
            BufferedImage img2 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(hand.get(1))));
            Image combined = combineImages(img1, img2, 100, 150);
            playerCardsLabel.setIcon(new ImageIcon(combined));

            // Table cards (river)
            int tableCount = Math.min(5, hand.size() - 2);
            BufferedImage[] tableImages = new BufferedImage[tableCount];
            for (int i = 0; i < tableCount; i++) {
                tableImages[i] = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(hand.get(i + 2))));
            }
            Image tableCombined = combineMultipleImages(tableImages, 50, 75);
            tableCardsLabel.setIcon(new ImageIcon(tableCombined));

            // Update money
            potLabel.setText("Pot: €" + potMoney);
            playerMoneyLabel.setText("Your money: €" + playerMoney);
            opponentMoneyLabel.setText("Opponent money: €" + opponentMoney);

            frame.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Image combineImages(BufferedImage img1, BufferedImage img2, int width, int height) {
        Image scaled1 = img1.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        Image scaled2 = img2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage combined = new BufferedImage(width * 2, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        g.drawImage(scaled1, 0, 0, null);
        g.drawImage(scaled2, width, 0, null);
        g.dispose();
        return combined;
    }

    private Image combineMultipleImages(BufferedImage[] images, int width, int height) {
        BufferedImage combined = new BufferedImage(width * images.length, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combined.createGraphics();
        for (int i = 0; i < images.length; i++) {
            Image scaled = images[i].getScaledInstance(width, height, Image.SCALE_SMOOTH);
            g.drawImage(scaled, i * width, 0, null);
        }
        g.dispose();
        return combined;
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    public void disableActions() {
        checkButton.setEnabled(false);
        callButton.setEnabled(false);
        raiseButton.setEnabled(false);
        foldButton.setEnabled(false);
    }
}
