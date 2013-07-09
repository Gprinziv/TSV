package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Person extends Controller {
   public static Result list() {
      List<? extends models.Listable> people = models.Legislator.getMany(0, 10);
      
      return ok(browse.render(people, null, "People", null));
   }

   public static Result show(Integer id) {
      return ok(person.render());
   }
}
