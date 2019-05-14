package ch.theband.benno.jarepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.time.StopWatch;

public class RechnungAnzeigenModel {
   private static final int BONUS_ERSTES_MAL = 1;
   private StopWatch startzeit;
   private final List<Rechnung> rechnungen;
   private Iterator<Rechnung> iter;
   private final int anzahlAufgaben;
   private Rechnung aktuelleRechnung;
   private int geloesteAufgaben;
   private String luecke;
   private long gebrauchteZeit;
   private final List<String> strategien;
   private int punkte;
   private int aktuelleAufgabeFalsch;
   private final boolean shuffle;
   private final JaRePoMode mode;

   public long getGebrauchteZeit() {
      return this.gebrauchteZeit;
   }

   public void setGebrauchteZeit(long gebrauchteZeit) {
      this.gebrauchteZeit = gebrauchteZeit;
   }

   public RechnungAnzeigenModel(List<String> strategien, List<Rechnung> rechnungen, int anzahlAufgaben, JaRePoMode mode) {
      this(strategien, rechnungen, anzahlAufgaben, mode, true);
   }

   protected RechnungAnzeigenModel(List<String> strategien, List<Rechnung> rechnungen, int anzahlAufgaben, JaRePoMode mode, boolean shuffle) {
      this.strategien = strategien;
      this.anzahlAufgaben = anzahlAufgaben;
      this.shuffle = shuffle;
      this.mode = mode;
      this.geloesteAufgaben = 0;
      this.punkte = this.getMaxpunkte();
      this.aktuelleAufgabeFalsch = 0;
      this.gebrauchteZeit = 0L;
      this.startzeit = new StopWatch();
      this.startzeit.start();
      this.rechnungen = new ArrayList(rechnungen);
      this.mischeln();
      this.aktuelleRechnung = (Rechnung)this.iter.next();
   }

   private void mischeln() {
      if (this.shuffle) {
         Collections.shuffle(this.rechnungen);
      }

      this.iter = this.rechnungen.iterator();
   }

   public void nextRechnung() {
      if (!this.iter.hasNext()) {
         this.mischeln();
      }

      this.aktuelleRechnung = (Rechnung)this.iter.next();
      ++this.geloesteAufgaben;
   }

   public String getNachLuecke() {
      return this.aktuelleRechnung.getNachLuecke();
   }

   public String getVorLuecke() {
      return this.aktuelleRechnung.getVorLuecke();
   }

   public void setLuecke(String luecke) {
      this.luecke = luecke;
   }

   public boolean checkResultat() {
      boolean result = this.isKorrekt();
      if (!result) {
         this.reduzierePunkte();
         ++this.aktuelleAufgabeFalsch;
      } else {
         this.aktuelleAufgabeFalsch = 0;
      }

      return result;
   }

   private void reduzierePunkte() {
      if (this.aktuelleAufgabeFalsch <= 1 && this.punkte > 0) {
         --this.punkte;
      }

   }

   public boolean isKorrekt() {
      boolean result;
      if (this.aktuelleRechnung.getLuecke().equals(this.luecke)) {
         result = true;
      } else {
         result = false;
      }

      return result;
   }

   public boolean isFertig() {
      boolean fertig = this.geloesteAufgaben >= this.anzahlAufgaben;
      if (fertig && this.gebrauchteZeit == 0L) {
         this.startzeit.stop();
         this.gebrauchteZeit = this.startzeit.getTime();
      }

      return fertig;
   }

   private int getPunkte() {
      return this.punkte;
   }

   public Score getScore() {
      Score score = new Score();
      score.setStrategien(new ArrayList(this.strategien));
      score.setMaxpunkte(this.getMaxpunkte());
      score.setPunkte(this.getPunkte());
      score.setZeit(this.gebrauchteZeit);
      return score;
   }

   private int getMaxpunkte() {
      return this.anzahlAufgaben * 2;
   }
}
