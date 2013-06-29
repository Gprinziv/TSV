package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;

public class Search extends Controller {

   public static Result search(String query) {
      return ok(search.render(query));
   }
   
   public static Result index() {
      return ok();
   }
}
