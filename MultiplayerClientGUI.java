import javax.swing.*;
import java.io.ObjectInputStream;
import java.net.InetAddress;

public class MultiplayerClientGUI {
    private JavaSwing swing;
    private PokerClient client;

    public MultiplayerClientGUI(JavaSwing swing) {
        this.swing = swing;
    }

    public void startClient() throws Exception {
        String serverIP = InetAddress.getLocalHost().getHostAddress();
        int port = 12345;
        client = new PokerClient(serverIP, port);

        new Thread(() -> {
            try {
                Object obj;
                while ((obj = client.getInput().readObject()) != null) {
                    if (obj instanceof Message msg) {
                        switch(msg.type) {
                            case CHAT -> swing.setStatusText(msg.data.toString());
                            case STATE -> {
                                GameState state = (GameState) msg.data;
                                // Update GUI with table cards and pot
                                swing.setStatusText("Current player: " + state.currentPlayer + " | Pot: €" + state.pot);
                            }
                            case RESULT -> swing.setStatusText(msg.data.toString());
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    public void sendAction(MultiplayerAction.ActionType type, int amount) {
        client.sendAction(type, amount);
    }
}
