import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketApp {
    private static final Logger logger = Logger.getLogger(ServerSocketApp.class.getName());

    public static void main(String[] args) {
        final int SERVER_PORT = 8081;

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            logger.info("Server is listening on port " + SERVER_PORT);

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true, StandardCharsets.UTF_8)) {

                logger.info("Client connected.");

                out.println("Привіт, клієнте! Введіть ваше привітання:");

                String clientGreeting = in.readLine();
                logger.info("Client greeting: " + clientGreeting);

                if (containsRussianLetters(clientGreeting)) {
                    out.println("Що таке паляниця?");
                    String userAnswer = in.readLine();
                    logger.info("Client's answer: " + userAnswer);

                    if (userAnswer.equalsIgnoreCase("український хліб")) {
                        out.println("Поточна дата та час: " + java.time.LocalDateTime.now());
                        logger.info("Server sent date and time to the client.");
                    } else {
                        out.println("Неправильна відповідь. З'єднання буде закрито.");
                        logger.info("Server: Неправильна відповідь. Закриваю з'єднання.");
                    }
                } else {
                    out.println("Неправильне привітання. Закриваю з'єднання.");
                    logger.info("Server: Неправильне привітання. Закриваю з'єднання.");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Exception occurred", e);
                System.err.println("Exception occurred: " + e.getMessage());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }

    private static boolean containsRussianLetters(String input) {
        // Перевірка наявності російських букв у введеному тексті
        return input.matches(".*[а-яА-Я].*");
    }
}