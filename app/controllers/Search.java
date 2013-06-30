package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.browse;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

import models.Listable;

public class Search extends Controller {

   public static Result search(String query) {
      String index = "indexes/legndx";
      String fields[] = {"heading", "description", "link"};
      
      Searcher newSearch = new Searcher(fields, index);
      List<Listable> results = newSearch.performSearch(query);
      
      return ok(browse.render(results));
   }
   
   public static Result index() {
      return ok();
   }
}
