package models;

public class SellerMessage extends Message {
  private boolean isValidSell = false;
  private String data = "";
  private Sell sell;

  public SellerMessage(String message) {
    super(message, "SELLER");

    if (!message.equals("enviar")) {
      String typeOperation = message.split("-")[0].toLowerCase();
      String data = message.split("-")[1];
      this.data = data;

      this.isValidSell = typeOperation.equals("sell");
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
