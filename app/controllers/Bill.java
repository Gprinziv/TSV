package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Bill extends Controller {
   
   public static Result show(String id) {
      return ok(bill.render("Testing"));
   }
}
