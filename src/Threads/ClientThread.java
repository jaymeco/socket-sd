package Threads;

import java.io.DataInputStream;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream received = new DataInputStream(socket.getInputStream());
            
            String message = received.readUTF();
            System.out.println("Server Message: " + message);
            // received.close();
        } catch (Exception e) {
            System.out.println("Error in Client Thread\n");
            e.printStackTrace();
        }
    }
}
