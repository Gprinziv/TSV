/**
 * Lucene integration for searching
 * 
 * @author klpaters
 */
package search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.List;
import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.Version;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Search {   
   private IndexSearcher searcher = null;
   private QueryParser parser = null;
   private Directory dir = null;
   private IndexReader reader = null;
   private Analyzer analyzer;
    
   /** Creates a new instance of SearchEngine */
  public Search(String field, String index) throws IOException{
     reader = DirectoryReader.open(FSDirectory.open(new File(index)));
     searcher = new IndexSearcher(reader);
     analyzer = new StandardAnalyzer(Version.LUCENE_43);
     parser = new QueryParser(Version.LUCENE_43, field, analyzer);
   }
       
   public TopDocs performSearch(String queryString)
      throws IOException, ParseException    {
      Query query = parser.parse(queryString);
      TopDocs results = searcher.search(query, null, 100);
//      ScoreDoc[] hits = results.scoreDocs;
      
      return results;   
   }

  public void closeReader() throws IOException {
    reader.close();
  }
   
}

