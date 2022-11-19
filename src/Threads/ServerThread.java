package Threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            boolean exitServer = false;

            while (!exitServer) {
                DataInputStream received = new DataInputStream(socket.getInputStream());
                
                DataOutputStream send = new DataOutputStream(socket.getOutputStream());
                 
                String message = received.readUTF();
                // System.out.println(message == "exit");
                if(message.equals("exit")) {
                    exitServer = true;
                    received.close();
                    send.close();
        
                    socket.close();

                    return;
                }
                String newMessage = message.toUpperCase();
                System.out.println("Mensagem recebida: " + message);
    
                send.writeUTF(newMessage);
            }
            // received.close();
            // send.close();

            // socket.close();
        } catch (Exception e) {
            System.out.println("Error in server Thread\n");
            e.printStackTrace();
        }
    }
}
