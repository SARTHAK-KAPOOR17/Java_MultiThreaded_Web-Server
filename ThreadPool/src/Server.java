import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {

    private final ExecutorService threadPool;

    public Server(int poolSize){
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    public void handleClient(Socket clientSocket) {
        try (PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)) {
            toSocket.println("Hello from server " + clientSocket.getInetAddress());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 10;
        Server server = new Server(poolSize);

        try{
            ServerSocket serversocket = new ServerSocket(port);
            serversocket.setSoTimeout(70000);
            System.out.println("Server is litening on port " + port);


            while(true){
                Socket clientSocket = serversocket.accept();

                // Use the thread pool to handle the client
                server.threadPool.execute(() -> server.handleClient(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            server.threadPool.shutdown();
        }
    }
}