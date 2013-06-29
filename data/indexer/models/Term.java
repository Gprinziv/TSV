package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import db.DB;

public class Term {
   private Date year;
   private Integer district;
   private String house;
   private String party;
   private Date start;
   private Date end;
   
   private static final String GET_FROM_PID = "SELECT * FROM Term WHERE pid = ?;";
   
   public Term(Date year, Integer district, String house, String party,
         Date start, Date end) {
      this.year = year;
      this.district = district;
      this.house = house;
      this.party = party;
      this.start = start;
      this.end = end;
   }
   
   public static List<Term> getTerms(Integer id) {
      List<Term> ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET_FROM_PID);
         get.setInt(1, id);
         res = get.executeQuery();

         ret = new ArrayList<Term>();
         while (res.next()) {
            ret.add(new Term(
                 res.getDate("year"),
                 res.getInt("district"),
                 res.getString("house"),
                 res.getString("party"),
                 res.getDate("start"),
                 res.getDate("end")
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
   
   public Date getYear() {
      return year;
   }
   public Integer getDistrict() {
      return district;
   }
   public String getHouse() {
      return house;
   }
   public String getParty() {
      return party;
   }
   public Date getStart() {
      return start;
   }
   public Date getEnd() {
      return end;
   }
}
