package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Listable;
import db.DB;

public class Bill implements Listable {
   private String id;
   private String type;
   private Integer number;
   private String state;
   private String status;
   private String house;
   private Integer session;
   private BillVersion currentVersion;
   private LinkedHashMap<String, BillVersion> versions;

   private static final String CACHE_PREFIX = "Bill.";

   private static final String GET = "SELECT * FROM Bill WHERE bid = ?";
   private static final String GET_MANY = "SELECT * FROM Bill limit ?,?";
   
   public Bill(String id, String type, Integer number, String state,
         String status, String house, Integer session,
         LinkedHashMap<String, BillVersion> versions) {
      this.id = id;
      this.type = type;
      this.number = number;
      this.state = state;
      this.status = status;
      this.house = house;
      this.session = session;
      this.versions = versions;
      this.currentVersion = versions.get(versions.keySet().toArray()[0]);
   }   

   public static List<Bill> getMany(int page, int count) {
      List<Bill> ret = null;
      Bill bill = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET_MANY);
         get.setInt(1, page);
         get.setInt(2, count);
         res = get.executeQuery();

         ret = new ArrayList<Bill>();
         while (res.next()) {
            bill = new Bill(res.getString("bid"), res.getString("type"), res.getInt("number"),
                  res.getString("state"), res.getString("status"),
                  res.getString("house"), res.getInt("session"),
                  BillVersion.getVersions(res.getString("bid")));
            ret.add(bill);
         }

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
            if (res != null) {
               res.close();
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
   
   public static Bill get(String id) {
      Bill ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet bill = null;

      try {
         get = connection.prepareStatement(GET);
         get.setString(1, id);
         bill = get.executeQuery();
         
         bill.next();
         
         ret = new Bill(id, bill.getString("type"), bill.getInt("number"),
               bill.getString("state"), bill.getString("status"),
               bill.getString("house"), bill.getInt("session"),
               BillVersion.getVersions(id));

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
      String name = type + " " + number;
      String title = currentVersion.getTitle();
      
      if (title != null) {
         name += ": " + title;
      }
      
      return name;
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

   public Map<String, BillVersion> getVersions() {
      return versions;
   }

   public BillVersion getCurrentVersion() {
      return currentVersion;
   }

   public String getHeading() {
      return getName();
   }
   
   public String getDescription() {
      return currentVersion.getDigest().replaceAll("\\<.*?\\>", "");
   }
   
   public String getLink() {
      return "/bill/" + id;
   }
}
