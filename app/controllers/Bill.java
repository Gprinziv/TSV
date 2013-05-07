package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Bill extends Controller {
   
   public static Result show(String id) {
      models.Bill result = models.Bill.get(id);
      models.BillVersion version = result.getVersions().get(0);
      
      return ok(bill.render(result, version));
   }
}
