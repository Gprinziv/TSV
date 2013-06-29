package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;

public class Legislator implements Listable {
   private Integer id;
   private String first;
   private String last;
   private List<Term> terms;
   
   private static final String CACHE_PREFIX = "Legislator.";
   
   private static final String GET = "SELECT * FROM Legislator WHERE pid = ?";
   private static final String GET_MANY = "SELECT * FROM Legislator LIMIT ?, ?";
   
   public Legislator(Integer id, String first, String last, List<Term> terms) {
      super();
      this.id = id;
      this.first = first;
      this.last = last;
      this.terms = terms;
   }
   
   public static List<Legislator> getMany(int page, int count) {
      List<Legislator> ret = null;
      Legislator cur = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET_MANY);
         get.setInt(1, page);
         get.setInt(2, count);
         res = get.executeQuery();

         ret = new ArrayList<Legislator>();
         while (res.next()) {
            cur = new Legislator(
                  res.getInt("pid"),
                  res.getString("first"),
                  res.getString("last"),
                  Term.getTerms(res.getInt("pid"))
               );
            ret.add(cur);
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
   
   public static Legislator get(Integer id) {
      Legislator ret = null;
      Connection connection = DB.getConnection();
      PreparedStatement get = null;
      ResultSet res = null;

      try {
         get = connection.prepareStatement(GET);
         get.setInt(1, id);
         res = get.executeQuery();

         if (res.next()) {
            ret = new Legislator(
                     id,
                     res.getString("first"),
                     res.getString("last"),
                     Term.getTerms(id)
                  );
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

   public Integer getId() {
      return id;
   }

   public String getFirst() {
      return first;
   }

   public String getLast() {
      return last;
   }

   public List<Term> getTerms() {
      return terms;
   }

   @Override
   public String getHeading() {
      return first + " " + last;
   }

   @Override
   public String getDescription() {
      // TODO Auto-generated method stub
      return null;
   }
   
   public String getLink() {
      return "/person/" + id;
   }
}
