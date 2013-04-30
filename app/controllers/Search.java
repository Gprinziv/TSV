package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

public class Search extends Controller {

   public static Result search(String id) {
      return ok(search.render());
   }
}
