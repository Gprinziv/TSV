package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.person;

public class Person extends Controller {

   public static Result show(Integer id) {
      return ok(person.render());
   }
}
