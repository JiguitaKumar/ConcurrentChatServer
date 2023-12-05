import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private Integer port = 9001;
    private Socket clientSocket;
    private ServerWorker serverWorker;
    private List<ServerWorker> serverWorkers = new ArrayList<>();
    private ExecutorService cachedPool;


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        try {

            serverSocket = new ServerSocket(port);
            cachedPool = Executors.newCachedThreadPool();

            while (true) {
                clientSocket = serverSocket.accept();

                serverWorker = new ServerWorker(clientSocket, this);
                serverWorkers.add(serverWorker);
                cachedPool.submit(serverWorker);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadCastAll(String message, String name) {
        for (ServerWorker client : serverWorkers) {

            if (client.getName().equals(name)) {
                continue;
            }

            client.send(name + ": " + message);
        }
    }

    public void greeting(String name) {
        for (ServerWorker client : serverWorkers) {

            if (client.getName().equals(name)) {
                continue;
            }

            client.send(name + " joined the conversation!");
        }
    }

    public void close(ServerWorker serverWorker) {
            serverWorker.closeSocket();
            serverWorkers.remove(serverWorker);

            broadCastAll("Left this conversation :", serverWorker.getName());
    }

}