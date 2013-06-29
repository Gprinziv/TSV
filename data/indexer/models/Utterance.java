package models;

public class Utterance {
   public String body;
   public Integer time;
   public Legislator speaker;
   
   public Utterance(String body, Integer time, Legislator speaker) {
      this.body = body;
      this.time = time;
      this.speaker = speaker;
   }
}
