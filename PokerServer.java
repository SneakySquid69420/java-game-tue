import java.io.*;
import java.net.*;
import java.util.*;

public class PokerServer {
    private static final int PORT = 12345;
    private static List<ClientHandler> players = new ArrayList<>();
    private static GameState gameState = new GameState();
    private static int currentPlayerIndex = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (players.size() < 2) {
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket, "Player" + (players.size() + 1));
            players.add(handler);
            new Thread(handler).start();
            System.out.println(handler.name + " connected.");
        }

        gameState.currentPlayer = players.get(0).name;
        broadcast(new Message(Message.Type.STATE, gameState));
        players.get(0).send(new Message(Message.Type.CHAT, "Your turn!"));
    }

    public static synchronized void handleAction(MultiplayerAction action) {
        switch (action.action) {
            case FOLD -> {
                broadcast(new Message(Message.Type.CHAT, action.playerName + " folds."));
                endRound();
                return;
            }
            case CALL, CHECK, RAISE -> {
                gameState.pot += action.amount;
                broadcast(new Message(Message.Type.STATE, gameState));
            }
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        gameState.currentPlayer = players.get(currentPlayerIndex).name;
        broadcast(new Message(Message.Type.STATE, gameState));
        players.get(currentPlayerIndex).send(new Message(Message.Type.CHAT, "Your turn!"));
    }

    private static void endRound() {
        broadcast(new Message(Message.Type.RESULT, "Round over! Pot = " + gameState.pot));
        gameState.pot = 0;
    }

    private static void broadcast(Message msg) {
        for (ClientHandler c : players) c.send(msg);
    }

    static class ClientHandler implements Runnable {
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;
        String name;

        public ClientHandler(Socket socket, String name) throws IOException {
            this.socket = socket;
            this.name = name;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }

        public void send(Message msg) {
            try { out.writeObject(msg); out.flush(); } catch (IOException e) { e.printStackTrace(); }
        }

        public void run() {
            try {
                Object obj;
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof MultiplayerAction action) handleAction(action);
                }
            } catch (Exception e) { System.out.println(name + " disconnected."); }
        }
    }
}
