package ch.theband.benno.jarepo;

import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable {
   private static final long serialVersionUID = 1L;
   private ArrayList<String> strategien;
   private int punkte;
   private int maxpunkte;
   private long zeit;

   public Score(ArrayList<String> strategien, int punkte, int maxpunkte, long zeit) {
      this.strategien = strategien;
      this.punkte = punkte;
      this.maxpunkte = maxpunkte;
      this.zeit = zeit;
   }

   public Score() {
   }

   public long getZeit() {
      return this.zeit;
   }

   public void setZeit(long zeit) {
      this.zeit = zeit;
   }

   public int getPunkte() {
      return this.punkte;
   }

   public void setPunkte(int punkte) {
      this.punkte = punkte;
   }

   public int getMaxpunkte() {
      return this.maxpunkte;
   }

   public void setMaxpunkte(int maxpunkte) {
      this.maxpunkte = maxpunkte;
   }

   public ArrayList<String> getStrategien() {
      return this.strategien;
   }

   public void setStrategien(ArrayList<String> strategien) {
      this.strategien = strategien;
   }

   public String toString() {
      return "Score [strategien=" + this.strategien + ", maxpunkte=" + this.maxpunkte + ", punkte=" + this.punkte + ",  zeit=" + this.zeit + "]";
   }

   public void addStrategie(String value) {
      if (this.strategien == null) {
         this.strategien = new ArrayList();
      }

      this.strategien.add(value);
   }
}
