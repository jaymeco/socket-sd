package models;

public class SellerMessage extends Message {
  private boolean isValidSell = false;
  private String data = "";
  private Sell sell;

  public SellerMessage(String message) {
    super(message, "SELLER");

    if (!message.equals("enviar") && !message.equals("sair")) {
      String typeOperation = message.split("-")[0].toLowerCase();
      this.isValidSell = typeOperation.equals("sell");
      if (isValidSell) {
        String data = message.split("-")[1];
        this.data = data;

        String[] dataList = data.split(";");
        this.isValidSell = this.isValidSell && (dataList.length >= 5);

        if (this.isValidSell) {
          Sell sell = new Sell(
              dataList[0].trim(),
              dataList[1],
              dataList[2],
              dataList[3],
              Float.parseFloat(dataList[4]));
          this.sell = sell;

        } else {
          System.out.println("\nERRO: Os dados informados estão inválidos!\n");
        }
      } else {
        System.out.println("\nERRO: Os dados informados estão inválidos!\n");
      }
    }
  }

  public boolean hasSellOption() {
    return this.isValidSell;
  }

  public Sell getSell() {
    return this.sell;
  }
}
