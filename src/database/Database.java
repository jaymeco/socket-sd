package database;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import models.Sell;

public class Database {
  private static final String DELIMITER = ",";
  private static final String SEPARATOR = "\n";
  private static final String HEADER = "code,seller,store,date,value";
  private FileWriter file;

  public Database() {
    try {
      File file = new File("./src/database/database.csv");
      if (file.exists()) {
        Scanner scanner = new Scanner(file);
        if (!scanner.hasNext()) {
          this.file = new FileWriter(file);
          this.file.append(HEADER);
          this.file.append(SEPARATOR);
          this.file.flush();
        }
      } else {
        this.file = new FileWriter(file);
        this.file.append(HEADER);
        this.file.append(SEPARATOR);
        this.file.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private ArrayList<Sell> getTable() {
    try {
      ArrayList<Sell> table = new ArrayList<Sell>();

      Scanner scanner = new Scanner(new File("./src/database/database.csv"));
      scanner.useDelimiter(",");

      while (scanner.hasNext()) {
        String line = scanner.nextLine();
        if (!line.equals(HEADER)) {
          String[] columns = line.split(DELIMITER);
          Sell sell = new Sell(
              columns[0],
              columns[1],
              columns[2],
              columns[3],
              Float.parseFloat(columns[4]));
          table.add(sell);
        }
      }
      scanner.close();

      return table;
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<Sell>();
    }
    // return new ArrayList<Sell>();
  }

  public void insert(Sell sell) {
    try {
      this.file.append(sell.getCode());
      this.file.append(DELIMITER);
      this.file.append(sell.getSellerName());
      this.file.append(DELIMITER);
      this.file.append(sell.getStore());
      this.file.append(DELIMITER);
      this.file.append(sell.getDate());
      this.file.append(DELIMITER);
      this.file.append(Float.toString(sell.getValue()));
      this.file.append(SEPARATOR);
      this.file.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public ArrayList<Sell> select() {
    return this.getTable();
  }
}