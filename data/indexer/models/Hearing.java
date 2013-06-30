package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import db.DB;

public class Hearing {
   Date date;
   String bid;
   Integer cid;
   List<Utterance> transcript;
   
   private static final String CACHE_PREFIX = "Hearing.";
   
   private static final String GET = "SELECT cid, bid, date FROM Hearing " +
         "WHERE cid = ? AND bid = ? AND date = ?";
   
   private static final String GET_TRANSCRIPT = "SELECT pid, time, text, first, last " +
   		"FROM Utterance WHERE cid = ? AND bid = ? AND date = ?";

   public Hearing(Date date, String bid, Integer cid) {
      this.date = date;
      this.bid = bid;
      this.cid = cid;
      this.transcript = null;
   }
   
   public static Hearing getOne(Date date, String bid, Integer cid) {
      
      Hearing ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET);
         get.setInt(1, cid);
         get.setString(2, bid);
         get.setDate(3, date);
         System.out.println(get);
         res = get.executeQuery();

         if (res.next()) {
            ret = new Hearing(res.getDate("date"), res.getString("bid"),
                  res.getInt("cid"));
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

   public Date getDate() {
      return date;
   }

   public String getBid() {
      return bid;
   }

   public Integer getCid() {
      return cid;
   }

   public List<Utterance> getTranscript() {
      if (transcript != null) {
         return transcript;
      }
      
      Legislator speaker;
      List<Utterance> ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET_TRANSCRIPT);
         get.setInt(1, cid);
         get.setString(2, bid);
         get.setDate(3, (java.sql.Date) date);
         System.out.println(get);
         res = get.executeQuery();

         ret = new ArrayList<Utterance>();
         while (res.next()) {
            speaker = Legislator.get(res.getInt("pid"));
            ret.add(new Utterance(res.getString("text"), res.getInt("time"),
                     speaker, res.getString("first"), res.getString("last")));
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
