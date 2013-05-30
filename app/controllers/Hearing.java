package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.hearings;

public class Hearing extends Controller {
   public static Result list() {
      return ok();
   }

   public static Result show(String id) {
      return ok(hearings.render());
   }
}
