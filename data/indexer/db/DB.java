package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
   public static Connection getConnection() {
      Connection conn = null;
      try {
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection("jdbc:mysql://localhost/opengov",
               "root", "");
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }

      return conn;
   }
}
