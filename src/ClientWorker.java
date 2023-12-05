import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientWorker implements Runnable {

    private PrintWriter write;
    private Socket socket;
    private Scanner scanner;

    public ClientWorker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            write = new PrintWriter(socket.getOutputStream());
            scanner = new Scanner(System.in);

            while (!socket.isClosed()) {

                String message = scanner.nextLine();

                write.println(message);
                write.flush();

                if (message.equals("/quit")) {
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}