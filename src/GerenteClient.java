import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Threads.GerenteThread;
import models.ManagerMessage;
import models.SellerMessage;

public class GerenteClient {
  public static void main(String[] args) {
    try {
      Socket client = new Socket("localhost", 4000);
      Scanner scanner = new Scanner(System.in);

      System.out.println("\n-----Conectado com servidor-----\n");
      System.out.println("Digite 'menu' para consultar o menu de opções\nDigite 'sair' para encerrar a comunicação\n");

      boolean exit = false;
      while (!exit) {
        ObjectOutputStream send = new ObjectOutputStream(client.getOutputStream());

        String teclado = scanner.nextLine();

        ManagerMessage message = new ManagerMessage(teclado);

        if (teclado.equals("sair")) {
          exit = true;
          send.close();
          client.close();
        }

        send.writeObject(message);

        GerenteThread thread = new GerenteThread(client);
        thread.start();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
