package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;
import data.indexer.search.Search;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public class Search extends Controller {

   public static Result searchAll(String query) {
      String index = "";
      String field = "";
      
      Search newSearch = new Search(field, index);
      List<Listable> results = newSearch.performSearch(query);
      
      return ok(browse.render(results));
   }
   
   public static Result index() {
      return ok();
   }
}
