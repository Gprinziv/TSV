package models;

public class Utterance implements Listable {
   String body;
   Integer time;
   Legislator speaker;
   
   public Utterance(String body, Integer time, Legislator speaker) {
      this.body = body;
      this.time = time;
      this.speaker = speaker;
   }

   @Override
   public String getHeading() {
      return speaker.getFirst() + " " + speaker.getLast();
   }

   @Override
   public String getDescription() {
      return body;
   }

   @Override
   public String getLink() {
      return null;
   }
}
