import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 1234;
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);
            System.out.println("New client connected!");
            new Thread(new ClientHandler(client)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    broadcast(message);
                }
            } catch (IOException e) {
                System.out.println("Client disconnected");
            }
        }

        private void broadcast(String message) throws IOException {
            for (Socket client : clients) {
                if (!client.isClosed()) {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(message);
                }
            }
        }
    }
}
