package models;


public class SearchResult implements Listable {
   private String heading;
   private String description;
   private String link;
   
   public SearchResult(String heading, String description, String link){
      this.heading = heading;
      this.description = description;
      this.link = link;
   }

   public String getHeading() {
      return heading;      
   }

   public String getDescription() {
      return description;
   }

   public String getLink() {
      return link;
   }

}
