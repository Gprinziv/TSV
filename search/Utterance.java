/**
 * Class which contains the fields for a utterance. 
 * An utterance is a piece of a transcript for which there is one
 * speaker. 
 * @author klpaters
 */

public class Utterance {
  private String speakerFirst;
  private String speakerLast;
  private int timestart;
  private int confidence;
  private String utterance;

  public Utterance(String speakerFirst, String speakerLast, 
                   int timestart, int confidence,
                   String utterance) {
    this.speakerFirst = speakerFirst;
    this.speakerLast = speakerLast;
    this.timestart = timestart;
    this.confidence = confidence;
    this.utterance = utterance;
  }

  public Utterance() {
    utterance = "";
  }

  /**
   * Takes an utterance and merges the string onto the content of 
   * of the Utterance object.
   */
  public Utterance mergeUtterance(Utterance ut, String newUt){
    String utOrig = ut.getUtterance();
    ut.setUtterance(utOrig + newUt);
    return ut;
  }

  public void setSpeaker(String speakerFirst, String speakerLast) {
    this.speakerFirst = speakerFirst;
    this.speakerLast = speakerLast;
  }

  public String getSpeakerFirst() {
    return this.speakerFirst;
  }

  public String getSpeakerLast() {
    return this.speakerLast;
  }

  public void setTimeStart(int timestart) {
    this.timestart = timestart;
  }

  public int getTimeStart() {
    return this.timestart;
  }

  public void setConfidence(int conf) {
    this.confidence = conf;
  }

  public int getConfidence() {
    return this.confidence;
  }

  public void setUtterance(String utterance) {
    this.utterance = utterance;
  }
  public String getUtterance() {
    return this.utterance;
  }

}
