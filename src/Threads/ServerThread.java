package Threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import models.ManagerMessage;
import models.Message;
import models.SellerMessage;
import repositories.VendasRepository;

public class ServerThread extends Thread {
  private Socket socket;
  private VendasRepository repository = new VendasRepository();

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
          this.handleManagerOption((ManagerMessage) message);
        } else {
          send.writeUTF("ERROR: Opção seleciona é inválida!");
        }

        if (message.getMessage().equals("sair")) {
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
              "Informe a venda no seguinte formato:\nsell-Código da venda;Nome do vendedor;Nome da loja;Data da venda (DD/MM/yyyy);Valor da venda\n");
          break;
        default:
          if (message.hasSellOption()) {
            String response = repository.add(message.getSell());
            send.writeUTF(response);
          } else {
            send.writeUTF("ERROR!");
          }
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleManagerOption(ManagerMessage message) {
    try {
      DataOutputStream send = new DataOutputStream(this.socket.getOutputStream());
      String mainMessage = message.getOption();

      switch (mainMessage) {
        case "menu":
          this.showMManagerMenuOptions();
          break;
        case "01":
          String totalSeller = this.repository.getTotalVendasBySeller(message.getSellerName());
          send.writeUTF(totalSeller);
          break;
        case "02":
          String totalStore = this.repository.getTotalVendasByStore(message.getStore());
          send.writeUTF(totalStore);
          break;
        case "04":
          String response = this.repository.getBestSeller();
          send.writeUTF(response);
          break;
        case "05":
          String bestStore = this.repository.getBestStore();
          send.writeUTF(bestStore);
          break;
        default:
          send.writeUTF("Invalid sintaxe");
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showMManagerMenuOptions() {
    try {
      DataOutputStream send = new DataOutputStream(this.socket.getOutputStream());
      String text = "\n-----Menu de opções-----\n"
          + "01 - Visualizar total de vendas de um vendedor\n"
          + "02 - Visualizar total de vendas de uma loja\n"
          + "03 - Visualizar total de vendas das lojas por periodo\n"
          + "04 - Visualizar vendedor com maior valor de vendas\n"
          + "05 - Visualizar loja com maior valor de vendas\n";

      send.writeUTF(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
