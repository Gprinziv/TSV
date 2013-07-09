package models;

public class Utterance implements Listable {
   String body;
   String first;
   String last;
   Integer time;
   Legislator speaker;
   
   public Utterance(String body, Integer time, Legislator speaker, String first, String last) {
      this.first = first;
      this.last = last;
      this.body = body;
      this.time = time;
      this.speaker = speaker;
   }

   @Override
   public String getHeading() {
      return first + " " + last;
   }

   @Override
   public String getDescription() {
      return body;
   }

   @Override
   public String getLink() {
      return "http://www.youtube.com/v/0xvIq8GTh8o&autoplay=1&start=" + (time / 1000);
   }
}
