package models;

public class Utterance {
   String body;
   Integer time;
   Legislator speaker;
   
   public Utterance(String body, Integer time, Legislator speaker) {
      this.body = body;
      this.time = time;
      this.speaker = speaker;
   }
}
