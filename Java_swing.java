import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class Java_swing {
    public void run(){
        
    
JFrame frame = new JFrame("cards");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setLayout(new GridLayout(5,5, 10, 10));
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
int round = 3;
int playerMoney = 5000;
int opponentMoney = 5000;
int potMoney = 500;
try{
image1 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/2_of_clubs.png"));
image2 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/2_of_diamonds.png"));

            Image scaled1 = image1.getScaledInstance(100, 150, Image.SCALE_SMOOTH);
            Image scaled2 = image2.getScaledInstance(100, 150, Image.SCALE_SMOOTH);

combinedImage = new BufferedImage(200, 150, BufferedImage.TYPE_INT_ARGB);

Graphics2D g = combinedImage.createGraphics();

g.drawImage(scaled1, 0, 0, null);
g.drawImage(scaled2, 100, 0, null);

g.dispose();

image3 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/3_of_hearts.png"));
image4 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/3_of_diamonds.png"));
image5 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/5_of_diamonds.png"));
image6 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/6_of_diamonds.png"));
image7 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/7_of_diamonds.png"));
image8 = ImageIO.read(getClass().getClassLoader().getResource("playing_cards/Card-Back.png"));

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

if (round == 1){
JLabel river = new JLabel();
frame.add(river);
river.setIcon(new ImageIcon(combinedImage2));}
else if (round == 2){
    JLabel river2 = new JLabel();
    frame.add(river2);
    river2.setIcon(new ImageIcon(combinedImage3));
    } else if (round == 3){
        JLabel river3 = new JLabel();
        frame.add(river3);
        river3.setIcon(new ImageIcon(combinedImage4));
    } else {
        JLabel filler = new JLabel();
        frame.add(filler);
    }

for(int i = 13; i < 17; i++){
    //JLabel filler2 = new JLabel();
    //frame.add(filler2);
    frame.add(new JLabel(Integer.toString(i)));
}   

JLabel label = new JLabel();
frame.add(label);
label.setIcon(new ImageIcon(combinedImage));

//for(int i = 18; i < 19; i++){
    //JLabel filler3 = new JLabel();
    //frame.add(filler3);
//    frame.add(new JLabel(Integer.toString(i)));
//}

JLabel sliderFrame = new JLabel();
frame.add(sliderFrame);

JSlider slider = new JSlider(0, playerMoney);
    slider.setPaintTrack(true);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    if(playerMoney<500){
        slider.setMajorTickSpacing(100);
        slider.setMinorTickSpacing(20);
    } else 
     if(playerMoney<1000){
        slider.setMajorTickSpacing(200);
        slider.setMinorTickSpacing(50);
    } else 
     if(playerMoney<5000){
        slider.setMajorTickSpacing(500);
        slider.setMinorTickSpacing(100);
    } else {
        slider.setMajorTickSpacing(1000);
        slider.setMinorTickSpacing(200);
    }
    sliderFrame.setText("your bet =" + slider.getValue());
frame.add(slider);
slider.addChangeListener((ChangeEvent e) -> {
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
        int value = (int)source.getValue();
        sliderFrame.setText("your bet =" + value);
    }
});

frame.add(new JButton("Check"));
frame.add(new JButton("call"));
frame.add(new JButton("raise"));
frame.add(new JButton("fold"));

JLabel moneyLabel = new JLabel("your money: €" + Integer.toString(playerMoney));
moneyLabel.setFont(new Font("Arial", Font.PLAIN, 30));
frame.add(moneyLabel);

}catch(IOException e){}
frame.setSize(1500, 900);
frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Java_swing().run();
    }
}
