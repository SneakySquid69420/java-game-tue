import java.io.*;
import java.net.*;

public class PokerClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("172.16.0.107", 12345);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        // Listening thread
        new Thread(() -> {
            try {
                Object obj;
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof Message msg) {
                        switch (msg.type) {
                            case STATE -> updateGUI((GameState) msg.data);
                            case CHAT -> System.out.println("[CHAT] " + msg.data);
                            case RESULT -> System.out.println("[RESULT] " + msg.data);
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }).start();

        // Sending actions manually
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = console.readLine()) != null) {
            String[] parts = line.split(" ");
            PlayerAction.ActionType type = PlayerAction.ActionType.valueOf(parts[0].toUpperCase());
            int amount = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
            out.writeObject(new Message(Message.Type.ACTION, new PlayerAction("Player1", type, amount)));
            out.flush();
        }
    }

    private static void updateGUI(GameState state) {
        System.out.println("Pot: " + state.pot + ", Round: " + state.round);
        System.out.println("Current player: " + state.currentPlayer);
        state.playerMoney.forEach((k,v) -> System.out.println(k + ": " + v));
        System.out.println("Table cards: " + state.tableCards);
    }
}
