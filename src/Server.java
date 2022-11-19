import java.net.ServerSocket;
import java.net.Socket;

import Threads.ServerThread;

public class Server {

    public static void main(String[] args) {
        // Creating a simple socket
        int port = 4000;
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server is running on PORT " + port);
            
            while(true) {
                Socket socket = server.accept();
                System.out.println("Cliente " + socket.getInetAddress().getHostAddress() +" se conectou\n");
                
                ServerThread thread = new ServerThread(socket);
                thread.start();
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}