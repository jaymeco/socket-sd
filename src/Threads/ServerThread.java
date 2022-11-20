package Threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import models.Message;
import models.SellerMessage;

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
        ObjectInputStream received = new ObjectInputStream(socket.getInputStream());

        DataOutputStream send = new DataOutputStream(socket.getOutputStream());

        Message message = (Message) received.readObject();
        String clientConnected = message.getClientConnected();

        if (clientConnected.equals("SELLER")) {
          this.handleSellerOption((SellerMessage) message);
        } else if (clientConnected.equals("MANAGER")) {
          //
        } else {
          send.writeUTF("ERROR: Opção seleciona é inválida!");
        }

        if (message.getMessage().equals("exit")) {
          exitServer = true;
          received.close();
          send.close();
          socket.close();

          return;
        }
      }
    } catch (Exception e) {
      System.out.println("Error in server Thread\n");
      e.printStackTrace();
    }
  }

  private void handleSellerOption(SellerMessage message) {
    try {
      DataOutputStream send = new DataOutputStream(this.socket.getOutputStream());

      switch (message.getMessage()) {
        case "enviar":
          send.writeUTF(
              "Informe a venda no seguinte formato: sell - Código da operação; Nome do vendedor; Nome da loja; data da venda (DD/MM/yyyy); valor da venda");
          break;
        default:
          if (message.hasSellOption()) {
            send.writeUTF(message.getSell().save());
          } else {
            send.writeUTF("ERROR!");
          }
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
