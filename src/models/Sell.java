package models;

import java.io.Serializable;

public class Sell implements Serializable {
  private String code = "";
  private String seller = "";
  private String store = "";
  private String date = "";
  private float value = 0;

  public Sell(String code, String seller, String store, String date, float value) {
    this.code = code;
    this.seller = seller;
    this.store = store;
    this.date = date;
    this.value = value;
  }

  public String getSellerName() {
    return this.seller;
  }

  public String getStore() {
    return this.store;
  }

  public String getCode() {
    return this.code;
  }

  public String getDate() {
    return this.date;
  }

  public float getValue() {
    return this.value;
  }
}
