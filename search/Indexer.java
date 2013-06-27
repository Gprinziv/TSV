/**
 * Indexer class for search
 * @author klpaters
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.util.Iterator;

public class Indexer {

  Directory dir;
  Analyzer analyzer;
  IndexWriterConfig iwc;
  IndexWriter writer;
   /**
    * Index documents
    */
   public Indexer(String indexPath) throws IOException {
     dir = FSDirectory.open(new File(indexPath));
     analyzer = new StandardAnalyzer(Version.LUCENE_43);
     iwc = new IndexWriterConfig(Version.LUCENE_43, analyzer);

     iwc.setOpenMode(OpenMode.CREATE);
     writer = new IndexWriter(dir, iwc);
   }
  
  

   /**
    * This needs to be a bill datatype
    */
/*   public void indexListable(ArrayList<Listable> bills) {
      
        Document doc;
        for(int i=0; i<bills.size(); i++) {
          doc = new Document();
        }
        }*/

  /**
   * Indexes utterance data given an ArrayList of Utterance objects.
   */
  public void indexUtterance(ArrayList<Utterance> utterances) throws IOException { 
    
    Document doc;
    for(int i=0; i<utterances.size(); i++) {
      doc = new Document();
      
      doc.add(new TextField("speakerFirst", utterances.get(i).getSpeakerFirst(), Field.Store.YES));
      doc.add(new TextField("speakerLast", utterances.get(i).getSpeakerLast(), Field.Store.YES));
      doc.add(new IntField("timeStart", utterances.get(i).getTimeStart(), Field.Store.YES));
      doc.add(new IntField("confidence", utterances.get(i).getConfidence(), Field.Store.YES));
      doc.add(new TextField("utterance", utterances.get(i).getUtterance(), Field.Store.YES));      
              
      writer.addDocument(doc);

      
    }

  }
   
   
   
      
}
