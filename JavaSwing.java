import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * The Swing GUI for the poker game.
 */
public class JavaSwing {
    private Actions actions;
    private boolean buttonsEnabled = true;

    /**
     * Sets the Actions instance for the GUI.
     * @param actions The Actions instance.
     */
    public void setActions(Actions actions) {
        this.actions = actions;
    }

    /**
     * Disables action buttons in the GUI.
     */
    public void disableActions() {
        buttonsEnabled = false;
    }

    JavaGame game = new JavaGame();
    Cards cards = new Cards();
    public int round = 0;
    public int potMoney = 20;
    public int playerMoney = 5000;
    public int opponentMoney = 5000;
    private JLabel statusLabel = new JLabel("Game has started");
    private JFrame frame;
    public boolean playerFolded = false;
    public boolean botFolded = false;
    public boolean botChecked = false;
    public boolean botRaised = false;
    private boolean firstRound = true;
    private boolean playerRaised = false;

    /**
     * Runs the GUI with the given hand of cards.
     * @param hand The list of card indices representing the player's hand and table cards.
     */
    public void run(List<Integer> hand) {

        if (frame != null) {
            frame.dispose();
        }

        frame = new JFrame("cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(5, 5, 10, 10));
        BufferedImage image1;
        BufferedImage image2;
        BufferedImage combinedImage;
        BufferedImage combinedImage2;
        BufferedImage image3;
        BufferedImage image4;
        BufferedImage image5;
        BufferedImage image6;
        BufferedImage image7;
        BufferedImage combinedImage3;
        BufferedImage combinedImage4;
        BufferedImage image8;

        statusLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        
        
        try {
            image1 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(0))));
            image2 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(1))));

            Image scaled1 = image1.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            Image scaled2 = image2.getScaledInstance(100, 150, Image.SCALE_SMOOTH);

            combinedImage = new BufferedImage(200, 150, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = combinedImage.createGraphics();

            g.drawImage(scaled1, 0, 0, null);
            g.drawImage(scaled2, 100, 0, null);

            g.dispose();

            image3 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(hand.size() - 1))));
            image4 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(hand.size() - 2))));
            image5 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(hand.size() - 3))));
            image6 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(hand.size() - 4))));
            image7 = ImageIO.read(getClass().getClassLoader().getResource(cards.toImage(
                hand.get(hand.size() - 5))));
            image8 = ImageIO.read(getClass().getClassLoader().getResource(
                "playing_cards/Card-Back.png"));

            Image scaled3 = image3.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            Image scaled4 = image4.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            Image scaled5 = image5.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            Image scaled6 = image6.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            Image scaled7 = image7.getScaledInstance(50, 75, Image.SCALE_SMOOTH);
            Image scaled8 = image8.getScaledInstance(200, 150, Image.SCALE_SMOOTH);

            combinedImage2 = new BufferedImage(150, 75, BufferedImage.TYPE_INT_ARGB);
            combinedImage3 = new BufferedImage(200, 75, BufferedImage.TYPE_INT_ARGB);
            combinedImage4 = new BufferedImage(250, 75, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = combinedImage2.createGraphics();
            Graphics2D g3 = combinedImage3.createGraphics();
            Graphics2D g4 = combinedImage4.createGraphics();

            g2.drawImage(scaled3, 0, 0, null);
            g2.drawImage(scaled4, 50, 0, null);
            g2.drawImage(scaled5, 100, 0, null);

            g2.dispose();

            g3.drawImage(scaled3, 0, 0, null);
            g3.drawImage(scaled4, 50, 0, null);
            g3.drawImage(scaled5, 100, 0, null);
            g3.drawImage(scaled6, 150, 0, null);

            g3.dispose();

            g4.drawImage(scaled3, 0, 0, null);
            g4.drawImage(scaled4, 50, 0, null);
            g4.drawImage(scaled5, 100, 0, null);
            g4.drawImage(scaled6, 150, 0, null);
            g4.drawImage(scaled7, 200, 0, null);

            g4.dispose();

            for (int i = 0; i < 2; i++) {
                //JLabel filler = new JLabel();
                //frame.add(filler);
                frame.add(new JLabel(Integer.toString(i)));
            }

            JButton check = new JButton("Check");
            JButton call = new JButton("Call");
            JButton raise = new JButton("Raise");
            JButton fold = new JButton("Fold");
            
            List<Integer> player = new ArrayList<>(hand);
            player.remove(2);
            player.remove(2);
            List<Integer> bot = new ArrayList<>(hand);
            bot.remove(0);
            bot.remove(0);
            if (!buttonsEnabled) {
                check.setEnabled(false);
                call.setEnabled(false);
                raise.setEnabled(false);
                fold.setEnabled(false);
                int playerValue = actions.getValue(player);
                int botValue = actions.getValue(bot);
                if ((playerValue > botValue && !playerFolded) || botFolded) {
                    winMessage("the player");
                    playerMoney += potMoney;
                    potMoney = 0;
                } else if (playerValue == botValue && !playerFolded && !botFolded) {
                    winMessage("noone");
                    playerMoney = 5010;
                    opponentMoney = 5010;
                    potMoney = 0;
                } else if ((botValue > playerValue && !botFolded) || playerFolded) {
                    winMessage("the bot");
                    opponentMoney += potMoney;
                    potMoney = 0;
                }
            } else if (playerMoney == 0) {
                raise.setEnabled(false);
            }

            actions.updateBotRaised();
            if (botChecked || firstRound || (actions.playerCalled && !actions.botRaised)
                || playerRaised) {
                firstRound = false;
                playerRaised = false;
                call.setEnabled(false);
            } else if (botRaised) {
                check.setEnabled(false);
            }

            JLabel opponentCards = new JLabel();
            frame.add(opponentCards);
            opponentCards.setIcon(new ImageIcon(scaled8));

            JLabel theirMoney = new JLabel("their money: €" + Integer.toString(opponentMoney));
            theirMoney.setFont(new Font("Arial", Font.PLAIN, 30));
            frame.add(theirMoney);

            for (int i = 4; i < 11; i++) {
                //JLabel filler = new JLabel();
                //frame.add(filler);
                frame.add(new JLabel(Integer.toString(i)));
            }

            JLabel pot = new JLabel("pot: €" + Integer.toString(potMoney));
            pot.setFont(new Font("Arial", Font.PLAIN, 50));
            frame.add(pot);
            switch (round) {
                default -> {
                    JLabel filler = new JLabel();
                    frame.add(filler);
                }
                case 1 -> {
                    JLabel river = new JLabel();
                    frame.add(river);
                    river.setIcon(new ImageIcon(combinedImage2));
                }
                case 2 -> {
                    JLabel river = new JLabel();
                    frame.add(river);
                    river.setIcon(new ImageIcon(combinedImage3));
                }
                case 3, 4 -> {
                    JLabel river = new JLabel();
                    frame.add(river);
                    river.setIcon(new ImageIcon(combinedImage4));
                }
            }
           
            frame.add(new JLabel("13"));
            frame.add(statusLabel);
            
            for (int i = 15; i < 17; i++) {
                //JLabel filler2 = new JLabel();
                //frame.add(filler2);
                frame.add(new JLabel(Integer.toString(i)));
            }   

            JLabel label = new JLabel();
            frame.add(label);
            label.setIcon(new ImageIcon(combinedImage));

            JLabel sliderFrame = new JLabel();
            frame.add(sliderFrame);

            JSlider slider = new JSlider(0, playerMoney);
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            if (playerMoney < 500) {
                slider.setMajorTickSpacing(100);
                slider.setMinorTickSpacing(20);
            } else if (playerMoney < 1000) {
                slider.setMajorTickSpacing(200);
                slider.setMinorTickSpacing(50);
            } else if (playerMoney < 4000) {
                slider.setMajorTickSpacing(500);
                slider.setMinorTickSpacing(100);
            } else {
                slider.setMajorTickSpacing(1000);
                slider.setMinorTickSpacing(200);
            }
            sliderFrame.setText("your bet = " + slider.getValue());
            frame.add(slider);
            slider.addChangeListener((ChangeEvent e) -> {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int value = (int) source.getValue();
                    sliderFrame.setText("your bet = " + value);
                }
            });

            frame.add(check);
            frame.add(call);
            frame.add(raise);
            frame.add(fold);

            check.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    actions.check();
                    actions.nextTurn();
                }
            });
            call.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    actions.call();
                    actions.nextTurn();
                }
            });
            raise.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    playerRaised = true;
                    actions.raise(slider.getValue());
                    // actions.nextTurn();
                }
            });
            fold.addActionListener(new ActionListener() {
                @Override 
                public void actionPerformed(ActionEvent e) {
                    actions.fold();
                    actions.nextTurn();
                }
            });
            JLabel moneyLabel = new JLabel("your money: €" + Integer.toString(playerMoney));
            moneyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            frame.add(moneyLabel);

        } catch (IOException e) {
            System.out.println("something went wrong");
        }
        frame.setSize(1500, 900);
        frame.setVisible(true);
    }

    private void winMessage(String winner) {
        if (!winner.equals("noone")) {
            setStatusText("The winner is: " + winner);
        } else {
            setStatusText("It's a draw");
        }
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }
}