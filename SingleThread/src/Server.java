import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public void run() throws IOException, UnknownHostException {
        int port = 8010;
        ServerSocket socket = new ServerSocket(port);
        socket.setSoTimeout(20000);
        while(true){
            System.out.println("Server is listening on port " + port);
            Socket accetedConnection = socket.accept();
            System.out.println("Connected to " + accetedConnection.getRemoteSocketAddress());
            PrintWriter toClient = new PrintWriter(accetedConnection.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(accetedConnection.getInputStream()));
            toClient.println("Hello World From Server!!");
        }
    }


    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}