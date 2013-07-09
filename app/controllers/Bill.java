package controllers;

import java.util.List;

import models.Listable;

import play.mvc.*;

import views.html.*;

public class Bill extends Controller {
   public static Result list() {
      List<? extends Listable> bills = models.Bill.getMany(0, 10);
      
      return ok(browse.render(bills, null, "Bills", null));
   }

   public static Result show(String id, String vid) {      
      models.Bill result = models.Bill.get(id);
      
      if (vid == null) {
         vid = (String) result.getVersions().keySet().toArray()[0];
      }
      
      models.BillVersion current_version = result.getVersions().get(vid);
      
      return ok(bill.render(result, current_version));
   }
}
