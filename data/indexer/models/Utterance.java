package models;

public class Utterance implements Listable {
   public String body;
   public String first;
   public String last;
   public Integer time;
   public Legislator speaker;
   
   public Utterance(String body, Integer time, Legislator speaker, String first, String last) {
      this.first = first;
      this.last = last;
      this.body = body;
      this.time = time;
      this.speaker = speaker;
   }

   @Override
   public String getHeading() {
      int seconds = time / 1000;
      int second = seconds % 60;
      int minute = (seconds / 60) % 60;
      int hours = (seconds / 60 / 60);
      return "SB 1530: An act to amend Section 435 of the Education Code, relating to pupils. 6/27/2012 " + hours + ":" + minute + ":" + second + " " + first + " " + last;
   }

   @Override
   public String getDescription() {
      return body;
   }

   @Override
   public String getLink() {
      return "http://www.youtube.com/watch?v=0xvIq8GTh8o&autoplay=1&t=" + (time / 1000);
   }
}
