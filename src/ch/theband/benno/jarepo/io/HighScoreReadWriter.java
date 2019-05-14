package ch.theband.benno.jarepo.io;

import ch.theband.benno.jarepo.Score;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.LocalStorage;

public class HighScoreReadWriter extends LocalStorageReaderWriter {
   private static final Logger logger = Logger.getLogger(HighScoreReadWriter.class.getName());
   private final LocalStorage storage;
   private final String fileName;

   public HighScoreReadWriter(LocalStorage storage, String username) {
      this.storage = storage;
      this.fileName = username + ".jarepoScore";
   }

   public ArrayList<Score> readHighScore() {
      InputStream stream = null;
      ArrayList scores = new ArrayList();

      ArrayList var9;
      try {
         stream = this.storage.openInputFile(this.fileName);
         LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(stream)));
         Score score = new Score();

         String line;
         while((line = reader.readLine()) != null) {
            String[] split = line.split("=");
            String value = split[1];
            if (split.length == 2) {
               switch(HighScoreReadWriter.Keys.valueOf(split[0])) {
               case VERSION:
                  if (!value.equals("1")) {
                     throw new WrongVersionException("Unbekannte Version " + value);
                  }
                  break;
               case STRATEGIEN:
                  score.addStrategie(value);
                  break;
               case PUNKTE:
                  score.setPunkte(Integer.valueOf(value));
                  break;
                  case MAXPUNKTE:
                  score.setMaxpunkte(Integer.valueOf(value));
                  break;
               case ZEIT:
                  score.setZeit(Long.valueOf(value));
                  scores.add(score);
                  score = new Score();
                  break;
               default:
                  logger.info("Ignored " + line);
               }
            }
         }

         return scores;
      } catch (IOException var19) {
         logger.log(Level.INFO, "Fehler beim lesen der HighScore", var19);
         var9 = new ArrayList();
         return var9;
      } catch (WrongVersionException var20) {
         logger.log(Level.INFO, "Inkompatible Version", var20);
         var9 = new ArrayList();
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var18) {
               logger.log(Level.WARNING, "Fehler beim Schliessen des HighScore-Streams", var18);
            }
         }

      }

      return var9;
   }

   public void writeHighScore(ArrayList<Score> scores) throws IOException {
      logger.fine("writing HighScores " + scores);
      OutputStream stream = this.storage.openOutputFile(this.fileName, false);
      BufferedWriter w = new BufferedWriter(new OutputStreamWriter(stream));

      try {
         Iterator var5 = scores.iterator();

         while(var5.hasNext()) {
            Score score = (Score)var5.next();
            this.writeKeyValue(w, HighScoreReadWriter.Keys.VERSION, "1");
            this.writeKeyValue(w, HighScoreReadWriter.Keys.PUNKTE, score.getPunkte());
            this.writeKeyValue(w, HighScoreReadWriter.Keys.MAXPUNKTE, score.getMaxpunkte());
            Iterator var7 = score.getStrategien().iterator();

            while(var7.hasNext()) {
               String strat = (String)var7.next();
               this.writeKeyValue(w, HighScoreReadWriter.Keys.STRATEGIEN, strat);
            }

            this.writeKeyValue(w, HighScoreReadWriter.Keys.ZEIT, score.getZeit());
         }
      } finally {
         w.close();
      }

   }

   public static enum Keys implements Key {
      VERSION,
      STRATEGIEN,
      PUNKTE,
      MAXPUNKTE,
      ZEIT;

      private Keys() {
      }
   }
}
