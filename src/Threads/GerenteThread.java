package Threads;

import java.io.DataInputStream;
import java.net.Socket;

public class GerenteThread extends Thread{
  private Socket socket;

  public GerenteThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      DataInputStream received = new DataInputStream(socket.getInputStream());

      String message = received.readUTF();
      System.out.println(message);
    } catch (Exception e) {
      System.out.println("Error in Client Thread\n");
      e.printStackTrace();
    }
  }
}