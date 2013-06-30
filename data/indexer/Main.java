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
         transndx = new Indexer("./indexes/transndx");
         legndx = new Indexer("./indexes/legndx");

         Hearing hear = Hearing.getOne(new Date(112, 5 , 27), "201120120SB1530", 0);
         List<Utterance> utterances = hear.getTranscript();
         List<Bill> bills = Bill.getMany(0, Integer.MAX_VALUE);
            
         //index the transcript in it's own index (for prototype only!)
         transndx.indexUtterance(utterances);

         List<Listable> docs = new ArrayList<Listable>();
         Listable docu;
      
         //add all utterances to main search docs
         for (int i=0; i < utterances.size(); i++) {
            docu = (Listable) utterances.get(i);
            if (docu != null) {
               docs.add(docu);
            }
            
         }

         //add all bills to main search docs
         for (int i=0; i < bills.size(); i++) {
            docu = bills.get(i);
            if (docu != null) {               
               docs.add(docu);
            }
            
         }

         //docs.add((Listable)hear);
      
         //index all documents
         legndx.indexListable(docs);
         
//         legndx.closeIndexWriter();

      } catch (IOException e) {
         System.err.println("Unable to create indexer.");

         System.exit(1);

      } 
      
   }
}
