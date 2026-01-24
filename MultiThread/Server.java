package MultiThread;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

   public Consumer<Socket> getConsumer(){
    return (clientSocket) -> {
        try(PrintWriter toSocket = new PrintWriter(clientSocket.getOutputStream(), true)){
            toSocket.println("Hello from Server: " + clientSocket.getInetAddress());
        } catch(IOException ex){
            ex.printStackTrace();
        }
    };
   }


    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        
        try {
            ServerSocket serversocket = new ServerSocket(port);
            serversocket.setSoTimeout(70000);
            System.out.println("Server is listening on port: " + port);
            while (true) {
                Socket clientSocket = serversocket.accept();

                // create and start a new thread for each client
                Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
                thread.start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}