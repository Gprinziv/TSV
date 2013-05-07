package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import play.cache.Cache;
import play.db.DB;

public class Bill {
   private String id;
   private String type;
   private Integer number;
   private String state;
   private String status;
   private String house;
   private Integer session;
   private List<BillVersion> versions;

   private static final String CACHE_PREFIX = "Bill.";

   private static final String GET = "SELECT * FROM Bill WHERE bid = ?";
   
   public Bill(String id, String type, Integer number, String state,
         String status, String house, Integer session,
         List<BillVersion> versions) {
      this.id = id;
      this.type = type;
      this.number = number;
      this.state = state;
      this.status = status;
      this.house = house;
      this.session = session;
      this.versions = versions;
   }   

   public static Bill get(String id) {
      Bill ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet bill = null;

      if ((ret = (Bill)Cache.get(CACHE_PREFIX + id)) != null) {
         return ret;
      }

      try {
         get = connection.prepareStatement(GET);
         get.setString(1, id);
         bill = get.executeQuery();
         
         bill.next();
         
         ret = new Bill(id, bill.getString("type"), bill.getInt("number"),
               bill.getString("state"), bill.getString("status"),
               bill.getString("house"), bill.getInt("session"),
               BillVersion.getVersions(id));
         Cache.set(CACHE_PREFIX + id, ret);

      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         try {
            if (get != null) {
               get.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }
         
         try {
            if (bill != null) {
               bill.close();
            }
         } catch (SQLException e1) {
            e1.printStackTrace();
         }
         
         try {
            connection.close();
         } catch (SQLException e) {
            e.printStackTrace();
         }
      }
      
      return ret;
   }

   public String getId() {
      return id;
   }

   public String getType() {
      return type;
   }

   public Integer getNumber() {
      return number;
   }

   public String getName() {
      return type + " " + number;
   }
   
   public String getState() {
      return state;
   }

   public String getStatus() {
      return status;
   }

   public String getHouse() {
      return house;
   }

   public Integer getSession() {
      return session;
   }

   public List<BillVersion> getVersions() {
      return versions;
   }
}
