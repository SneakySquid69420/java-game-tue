import java.io.*;
import java.net.Socket;

public class PokerClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String playerName;

    public PokerClient(String serverIP, int port) throws Exception {
        socket = new Socket(serverIP, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void sendAction(MultiplayerAction.ActionType type, int amount) {
        try {
            out.writeObject(new MultiplayerAction("Player", type, amount));
            out.flush();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public ObjectInputStream getInput() { return in; }
}
