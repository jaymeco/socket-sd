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
  private VendasRepository repository;

  public ServerThread(Socket socket) {
    this.socket = socket;
    this.repository = new VendasRepository();
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

        switch (clientConnected) {
          case "SELLER":
            this.handleSellerOption((SellerMessage) message);
            break;
          case "MANAGER":
            this.handleManagerOption((ManagerMessage) message);
            break;
          default:
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
              "\nInforme a venda no seguinte formato:\nsell-Código da venda;Nome do vendedor;Nome da loja;Data da venda (DD/MM/yyyy);Valor da venda\n");
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
        case "03":
          String totalAtPeriod = this.repository.getTotalVendasAtPeriod(message.getInitalDate(), message.getFinalDate());
          send.writeUTF(totalAtPeriod);
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
          + "01 - Visualizar total de vendas de um vendedor (Informe nesse formato: 01-Nome do vendedor)\n"
          + "02 - Visualizar total de vendas de uma loja (Informe nesse formato: 02-Nome da loja)\n"
          + "03 - Visualizar total de vendas das lojas por periodo (Informe nesse formato: 03-Data inicial (DD/MM/yyyy);Data final (DD/MM/yyyy))\n"
          + "04 - Visualizar vendedor com maior valor de vendas (Informe nesse formato: 04)\n"
          + "05 - Visualizar loja com maior valor de vendas (Informe nesse formato: 05)\n";

      send.writeUTF(text);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
