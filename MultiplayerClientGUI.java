import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MultiplayerClientGUI {
    private JavaSwing swing;
    private PokerClient client;

    public MultiplayerClientGUI(JavaSwing swing) {
        this.swing = swing;
    }

    public void startClient() {
        try {
            // Automatically fetch local IP
            String serverIP = getServerIP();
            int port = 12345; // default port

            client = new PokerClient(serverIP, "Player");

            new Thread(() -> {
                try {
                    Object obj;
                    while ((obj = client.getInput().readObject()) != null) {
                        if (obj instanceof Message msg) {
                            switch (msg.type) {
                                case CHAT -> swing.setStatusText(msg.data.toString());
                                case STATE -> {
                                    GameState state = (GameState) msg.data;
                                    swing.potMoney = state.pot;
                                    swing.run(state.tableCards); // show table cards
                                }
                                case RESULT -> swing.setStatusText(msg.data.toString());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getServerIP() {
        try {
            // Attempts to detect local IP automatically
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "127.0.0.1"; // fallback to localhost
        }
    }

    public void sendAction(MultiplayerAction.ActionType type, int amount) {
        client.sendAction(type, amount);
    }
}
