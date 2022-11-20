import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Threads.VendedorThread;
import models.SellerMessage;

public class VendedorClient {
  public static void main(String[] args) {
    try {
      Socket client = new Socket("localhost", 4000);
      Scanner scanner = new Scanner(System.in);

      System.out.println("-----Conectado com servidor-----\n");
      System.out.println("Digite 'enviar' para informar um venda\nDigite 'sair' para encerrar a comunicação");

      boolean exit = false;
      while (!exit) {
        ObjectOutputStream send = new ObjectOutputStream(client.getOutputStream());

        String teclado = scanner.nextLine();

        SellerMessage message = new SellerMessage(teclado);

        if (teclado.equals("sair")) {
          exit = true;
          send.close();
          client.close();
        }

        send.writeObject(message);

        VendedorThread thread = new VendedorThread(client);
        thread.start();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
