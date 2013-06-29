package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
   public static Connection getConnection() {
      Connection conn = null;

      try {
         conn = DriverManager.getConnection("jdbc:mysql://localhost/opengov",
               "root", "");
      } catch (Exception e) {
         return null;
      }

      return conn;
   }
}
