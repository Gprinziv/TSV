package controllers;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import play.mvc.Controller;
import play.mvc.Result;

import views.html.hearings;
import views.html.browse;

import models.Listable;

public class Hearing extends Controller {
   public static Result list() {
      List<Listable> list = new ArrayList<Listable>();

      return ok(browse.render(list, null, "Transcripts", "/transcript"));
   }

   public static Result show(String id) {
      return ok(hearings.render());
   }
}
