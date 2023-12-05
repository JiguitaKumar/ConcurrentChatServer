import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String host = "localhost";
    private Integer port = 9001;
    private BufferedReader reader;

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread write = new Thread(new ClientWorker(socket));
            write.start();

            while (!socket.isClosed()) {
                String message = reader.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}