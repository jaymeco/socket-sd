import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Threads.ClientThread;

public class Client {
  public static void main(String[] args) {
    try {
      Socket client = new Socket("localhost", 4000);
      Scanner scanner = new Scanner(System.in);
      System.out.println("Conectado com servidor\n");

      boolean exit = false;
      while (!exit) {
        DataOutputStream send = new DataOutputStream(client.getOutputStream());
        String teclado = scanner.nextLine();

        if (teclado.equals("exit")) {
          exit = true;
          send.close();
          client.close();
        }

        send.writeUTF(teclado);

        ClientThread thread = new ClientThread(client);
        thread.start();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
