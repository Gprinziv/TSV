import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.sql.Date;

import search.Indexer;

import models.Hearing;
import models.Bill;
import models.Utterance;
import models.Listable;

public class Main {
   
   public static void main(String[] args) {
      Indexer transndx;
      Indexer legndx;
      
      //Create indexers (one for singluar transcript), one for all leg docs
      try {
         transndx = new Indexer("../../data/indexes/transndx");
         legndx = new Indexer("../../data/indexes/legndx");

         Hearing hear = Hearing.getOne(new Date(0), "201120120SB1530", 0);
         List<Utterance> utterances = hear.getTranscript();
         List<Bill> bills = Bill.getMany(0, Integer.MAX_VALUE);
            
         //index the transcript in it's own index (for prototype only!)
         transndx.indexUtterance(utterances);

         List<Listable> docs = new ArrayList<Listable>();
         Listable docu;
      
         for (int i=0; i < utterances.size(); i++) {
            docu = (Listable) utterances.get(i);
            docs.add(docu);
         }

         for (int i=0; i < bills.size(); i++) {
            docu = bills.get(i);
            docs.add(docu);
         }

         docs.add((Listable)hear);
      
         //index all documents
         legndx.indexListable(docs);


      } catch (IOException e) {
         System.err.println("Unable to create indexer.");
         System.exit(1);
      }
   }
}
