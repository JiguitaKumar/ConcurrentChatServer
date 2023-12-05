import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerWorker implements Runnable {

    private Socket clientSocket;
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private String name;

    public ServerWorker(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }


    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());

            userName();
            server.greeting(name);

            while (!clientSocket.isClosed()) {

                String message = in.readLine();

                if (message.equals("/quit")) {
                    server.close(this);
                } else {
                    System.out.println(name + ": " + message);
                    server.broadCastAll(message, name);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message) {
        out.println(message);
        out.flush();
    }

    public void userName() {
        try {
            out.println("Enter Name: ");
            out.flush();
            name = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeSocket(){
        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return this.name;
    }
}