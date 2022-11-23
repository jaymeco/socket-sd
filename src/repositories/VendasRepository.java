package repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import database.Database;
import models.Sell;

public class VendasRepository {
  private ArrayList<Sell> sells = new ArrayList<Sell>();
  private Database db;

  public VendasRepository() {
    this.db = new Database();
    // sells = new ArrayList<Sell>();
  }

  public String add(Sell sell) {
    // sells.add(sell);
    db.insert(sell);
    return "Ok! Venda salva com sucesso";
  }

  public ArrayList<Sell> getAll() {
    return this.db.select();
  }

  public String getTotalVendasBySeller(String name) {
    int count = 0;

    for (Sell sell : this.getAll()) {
      if (sell.getSellerName().equals(name)) {
        count++;
      }
    }

    return "\nO vendedor "
        + name +
        " teve o total de "
        + count +
        " vendas!\n";
  }

  public String getTotalVendasByStore(String name) {
    int count = 0;

    for (Sell sell : this.getAll()) {
      if (sell.getStore().equals(name)) {
        count++;
      }
    }

    return "\nA loja "
        + name +
        " teve o total de "
        + count +
        " vendas!\n";
  }

  public String getBestSeller() {
    Map<String, Float> grouped = new HashMap<String, Float>();
    Map.Entry<String, Float> maxSell = null;
    for (Sell sell : this.getAll()) {
      String key = sell.getSellerName();
      if (!grouped.containsKey(key)) {
        grouped.put(key, sell.getValue());
      } else {
        grouped.replace(key, grouped.get(key) + sell.getValue());
      }
    }
    for (Entry<String, Float> sell : grouped.entrySet()) {
      if (maxSell == null || sell.getValue().compareTo(maxSell.getValue()) > 0) {
        maxSell = sell;
      }
    }

    return "\nO melhor vendedor foi "
        + maxSell.getKey() +
        " com um total de "
        + maxSell.getValue() +
        " reais\n";
  }

  public String getBestStore() {
    Map<String, Float> grouped = new HashMap<String, Float>();
    Map.Entry<String, Float> maxSell = null;
    for (Sell sell : this.getAll()) {
      String key = sell.getStore();
      if (!grouped.containsKey(key)) {
        grouped.put(key, sell.getValue());
      } else {
        grouped.replace(key, grouped.get(key) + sell.getValue());
      }
    }
    for (Entry<String, Float> sell : grouped.entrySet()) {
      if (maxSell == null || sell.getValue().compareTo(maxSell.getValue()) > 0) {
        maxSell = sell;
      }
    }

    return "\nA melhor loja foi "
        + maxSell.getKey() +
        " com um total de "
        + maxSell.getValue() +
        " reais\n";
  }
}