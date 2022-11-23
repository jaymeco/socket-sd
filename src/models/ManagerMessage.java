package models;

public class ManagerMessage extends Message {
  private boolean isValidOption = false;
  private String option = "menu";
  private String seller = "";
  private String store = "";

  public ManagerMessage(String message) {
    super(message, "MANAGER");

    if (!message.equals("menu")) {
      String typeOperation = message.split("-")[0].toLowerCase();
      this.option = typeOperation;
      switch (typeOperation) {
        case "01":
          this.seller = message.split("-")[1];
          break;
        case "02":
          this.store = message.split("-")[1];
          break;
        default:
          break;
      }
    }
  }

  public String getOption() {
    return this.option;
  }

  public String getSellerName() {
    return this.seller;
  }

  public String getStore() {
    return this.store;
  }
}
