import java.io.*;
import java.net.*;
import java.util.*;

public class PokerServer {
    private static final int PORT = 12345;
    private static final int MAX_PLAYERS = 2;

    private static List<ClientHandler> players = new ArrayList<>();
    private static GameState gameState = new GameState();
    private static int currentPlayerIndex = 0;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (players.size() < MAX_PLAYERS) {
            Socket socket = serverSocket.accept();
            String playerName = "Player" + (players.size() + 1);
            ClientHandler handler = new ClientHandler(socket, playerName);
            players.add(handler);
            new Thread(handler).start();
            System.out.println(playerName + " connected.");
        }

        startGame();
    }

    private static void startGame() {
        // Initialize gameState
        gameState.pot = 0;
        gameState.round = 0;
        gameState.playerMoney = new HashMap<>();
        gameState.playerHands = new HashMap<>();
        for (ClientHandler p : players) {
            gameState.playerMoney.put(p.name, 5000);
            gameState.playerHands.put(p.name, drawHand()); // implement drawHand()
        }
        gameState.tableCards = new ArrayList<>();
        gameState.currentPlayer = players.get(currentPlayerIndex).name;
        broadcast(new Message(Message.Type.STATE, gameState));
        players.get(currentPlayerIndex).send(new Message(Message.Type.CHAT, "Your turn!"));
    }

    public static synchronized void handleAction(PlayerAction action) {
        System.out.println(action.playerName + " -> " + action.action);
        // Update game state based on action
        switch (action.action) {
            case FOLD -> endRound(action.playerName + " folded!");
            case CALL, CHECK, RAISE -> {
                gameState.pot += action.amount;
                int money = gameState.playerMoney.get(action.playerName);
                gameState.playerMoney.put(action.playerName, money - action.amount);
                broadcast(new Message(Message.Type.STATE, gameState));
            }
        }

        // Next player's turn
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        gameState.currentPlayer = players.get(currentPlayerIndex).name;
        broadcast(new Message(Message.Type.STATE, gameState));
        players.get(currentPlayerIndex).send(new Message(Message.Type.CHAT, "Your turn!"));
    }

    private static void endRound(String result) {
        broadcast(new Message(Message.Type.RESULT, result + " Pot: " + gameState.pot));
        // Optionally reset or start next round
    }

    private static void broadcast(Message msg) {
        for (ClientHandler c : players) c.send(msg);
    }

    private static List<Integer> drawHand() {
        Random r = new Random();
        return List.of(r.nextInt(52), r.nextInt(52)); // simple random hand
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        public String name;

        public ClientHandler(Socket socket, String name) throws IOException {
            this.socket = socket;
            this.name = name;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            send(new Message(Message.Type.CHAT, "Welcome " + name));
        }

        public void send(Message msg) {
            try { out.writeObject(msg); out.flush(); } 
            catch (IOException e) { e.printStackTrace(); }
        }

        public void run() {
            try {
                Object obj;
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof Message msg && msg.data instanceof PlayerAction action) {
                        PokerServer.handleAction(action);
                    }
                }
            } catch (Exception e) {
                System.out.println(name + " disconnected.");
            }
        }
    }
}
