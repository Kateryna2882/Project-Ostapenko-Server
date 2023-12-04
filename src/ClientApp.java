import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {
    private static final Logger logger = Logger.getLogger(ClientApp.class.getName());

    public static void main(String[] args) {
        final String SERVER_IP = "127.0.0.1";
        final int SERVER_PORT = 8081;

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {

            logger.info("Successfully connected to the server.");

            while (true) {
                String serverMessage = in.readLine();

                if (serverMessage == null) {
                    logger.info("Server closed the connection. Exiting.");
                    break;
                }

                System.out.println("Server: " + serverMessage);

                if (serverMessage.equals("Неправильна відповідь. З'єднання буде закрито.")) {
                    break;
                }

                Scanner scanner = new Scanner(System.in);
                System.out.print("Введіть відповідь: ");
                String userResponse = scanner.nextLine();
                logger.info("Sending response to the server: " + userResponse);
                out.println(userResponse);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }
}