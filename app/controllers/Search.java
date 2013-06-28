package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.search;

import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import search.Indexer;

public class Search extends Controller {

   public static Result search(String query) {
      return ok(search.render(query));
   }
   
   public static Result index() {
      //Create indexers (one for singluar transcript), one for all leg docs
      Indexer transndx = new Indexer("../../data/indexes/transndx");
      Indexer legndx = new Indexer("../../data/indexes/legndx");

      Hearing hear = Hearing.getOne(new Date(0,0,0), "201120120SB1530", 0);
      List<Utterance> utterances = hear.getTranscript();
      List<Bill> bills = Bill.getMany(0, Integer.MAX_VALUE);
            
      //index the transcript in it's own index (for prototype only!)
      transndx.indexUtterance(utterances);

      List<Listable> docs = new ArrayList<Listable>();
      Listable docu;
      
      for (int i=0; i < utterances.size(); i++) {
         docu = utterances.get(i);
         docs.add(docu);
      }

      for (int i=0; i < bills.size(); i++) {
         docu = bills.get(i);
         docs.add(docu);
      }

      docs.add((Listable)hear);
      
      //index all documents
      legndx.indexUtterance(docs);
  
      return ok();
   }
}
