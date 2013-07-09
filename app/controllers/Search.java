package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.browse;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import models.SearchResult;
import org.apache.lucene.queryparser.classic.ParseException;

import models.Listable;

public class Search extends Controller {

   public static Result search(String query) {
      String index = "data/indexer/indexes/legndx";
      String fields[] = {"heading", "description", "link"};
      List<SearchResult> results = new ArrayList<SearchResult>();
      
      try {
         Searcher newSearch = new Searcher(fields, index);
         results = newSearch.performSearch(query);
      } catch (IOException e) {
         System.err.println("Unable to open index");
         e.printStackTrace();
         
         System.exit(1);
      } catch (ParseException x) {
         System.err.println("Unable to open parse query");
         System.exit(1);
      }
      
      return ok(browse.render(results));
   }

   public static Result transSearch(String query) {
      String index = "data/indexer/indexes/transndx";
      String fields[] = {"speakerFirst", "speakerLast", "timeStart", "utterance"};
      List<Listable> results = new ArrayList<Listable>();
      
      try {
         Searcher newSearch = new Searcher(fields, index);
         results = newSearch.performTransSearch(query);
      } catch (IOException e) {
         System.err.println("Unable to open index");
         e.printStackTrace();
         
         System.exit(1);
      } catch (ParseException x) {
         System.err.println("Unable to open parse query");
         System.exit(1);
      }
      
      return ok(browse.render(results));
   }
   
   public static Result index() {
      return ok();
   }
}
