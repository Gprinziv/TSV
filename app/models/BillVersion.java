package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import play.cache.Cache;
import play.db.DB;

public class BillVersion {
   private String vid;
   private String bid;
   private Date date;
   private String state;
   private Boolean appropriation;
   private Boolean substantive_changes;
   private String title;
   private String digest;
   private String text;
   
   private static final String CACHE_PREFIX = "BillVersion.";

   private static final String GET = "SELECT vid, bid, date, state, " +
           "appropriation, substantive_changes FROM BillVersion WHERE vid = ?";

   private static final String GET_FROM_BID = "SELECT vid, bid, date, state, " +
           "appropriation, substantive_changes, digest, title " +
           "FROM BillVersion WHERE bid = ? ORDER BY date ASC";

   private static final String GET_TEXT = "SELECT text FROM BillVersion " +
           "WHERE vid = ?";

   public BillVersion(String vid, String bid, Date date, String state,
         Boolean appropriation, Boolean substantive_changes, String title,
         String digest, String text) {
      this.vid = vid;
      this.bid = bid;
      this.date = date;
      this.state = state;
      this.appropriation = appropriation;
      this.substantive_changes = substantive_changes;
      this.title = title;
      this.digest = digest;
      this.text = text;
   }

   public static BillVersion get(String id) {
      BillVersion ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      if ((ret = (BillVersion) Cache.get(CACHE_PREFIX + id)) != null) {
         return ret;
      }

      try {
         get = connection.prepareStatement(GET);
         get.setString(1, id);
         res = get.executeQuery();

         if (res.next()) {
            ret = new BillVersion(
                  res.getString("vid"),
                  res.getString("bid"),
                  res.getDate("date"),
                  res.getString("state"),
                  res.getBoolean("appropriation"),
                  res.getBoolean("substansive_changes"),
                  res.getString("title"),
                  res.getString("digest"),
                  null
                  );
            Cache.set(CACHE_PREFIX + id, ret);
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

   public String getVid() {
      return vid;
   }

   public String getBid() {
      return bid;
   }

   public Date getDate() {
      return date;
   }

   public String getState() {
      return state;
   }

   public Boolean getAppropriation() {
      return appropriation;
   }

   public Boolean getSubstantive_changes() {
      return substantive_changes;
   }

   public String getTitle() {
      return title;
   }

   public String getDigest() {
      return digest;
   }

   public String getText() {
      if (text != null) {
         return text;
      } else {
         String ret = null;
         Connection connection = DB.getConnection();
         PreparedStatement get = null;
         ResultSet res = null;

         if ((ret = (String) Cache.get(CACHE_PREFIX + vid + ".text")) != null) {
            return ret;
         }

         try {
            get = connection.prepareStatement(GET_TEXT);
            get.setString(1, vid);
            res = get.executeQuery();

            if (res.next()) {
               ret = res.getString("text");
               Cache.set(CACHE_PREFIX + vid + ".text", ret);
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
   }

   public static LinkedHashMap<String, BillVersion> getVersions(String id) {
      LinkedHashMap<String, BillVersion> ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET_FROM_BID);
         get.setString(1, id);
         res = get.executeQuery();

         ret = new LinkedHashMap<String, BillVersion>();
         
         while (res.next()) {
            ret.put(res.getString("vid"), new BillVersion(
                  res.getString("vid"),
                  res.getString("bid"),
                  res.getDate("date"),
                  res.getString("state"),
                  res.getBoolean("appropriation"),
                  res.getBoolean("substantive_changes"),
                  res.getString("title"),
                  res.getString("digest"),
                  null
                  ));
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
}
