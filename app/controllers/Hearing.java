package controllers;

import java.util.Date;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.hearings;

public class Hearing extends Controller {
   public static Result list() {
      return ok();
   }

   public static Result show(String id) {
      models.Hearing hearing = models.Hearing.getOne(new models.HearingPK(new Date(), "201120120AB1", 0));
      return ok(hearings.render());
   }
}
