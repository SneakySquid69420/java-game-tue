import java.io.*;
import java.net.Socket;

/**
 * The client for the poker game.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to server!");

        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String input;
        while ((input = userInput.readLine()) != null) {
            out.println(input);
        }
    }
}
