package ch.theband.benno.jarepo;

import ch.theband.benno.jarepo.util.CollectionHelper;
import ch.theband.benno.jarepo.util.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class HighScoreTableModel extends AbstractTableModel implements TableModel {
   private ArrayList<Score> scores;
   private int newestHighScoreIndex = -1;

   public HighScoreTableModel(ArrayList<Score> scores2) {
      this.setScores(scores2);
   }

   public int getColumnCount() {
      return HighScoreTableModel.Columns.values().length;
   }

   public int getRowCount() {
      return this.scores.size();
   }

   public Class<?> getColumnClass(int columnIndex) {
      switch (HighScoreTableModel.Columns.values()[columnIndex]) {
         case STRATEGIEN:
            return ArrayList.class;
         case PUNKTE:
            return Integer.class;
         case MAX_PUNKTE:
            return Integer.class;
         case ZEIT:
            return Long.class;
         default:
            return Object.class;
      }
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      Score score = (Score)this.scores.get(rowIndex);
      switch(HighScoreTableModel.Columns.values()[columnIndex]) {
      case STRATEGIEN:
         return score.getStrategien();
      case PUNKTE:
         return score.getPunkte();
      case MAX_PUNKTE:
         return score.getMaxpunkte();
      case ZEIT:
         return score.getZeit();
      default:
         return null;
      }
   }

   public String getColumnName(int column) {
      switch(HighScoreTableModel.Columns.values()[column]) {
      case STRATEGIEN:
         return "Strategien";
      case PUNKTE:
         return "Punkte";
      case MAX_PUNKTE:
         return "max. Punkte";
      case ZEIT:
         return "Zeit";
      default:
         return null;
      }
   }

   public void setScores(ArrayList<Score> scores) {
      this.scores = scores;
      Collections.sort(scores, new HighScoreTableModel.ScoreComparator());
      this.fireTableDataChanged();
   }

   public ArrayList<Score> getScores() {
      return this.scores;
   }

   public void addNewestHighscore(Score score) {
      this.filterScores(score);
      this.scores.add(score);
      Collections.sort(this.scores, new HighScoreTableModel.ScoreComparator());
      this.newestHighScoreIndex = this.scores.indexOf(score);
      this.fireTableDataChanged();
   }

   private int getNewestHighScoreIndex() {
      return this.newestHighScoreIndex;
   }

   private void filterScores(final Score score) {
      CollectionHelper.filter(this.scores, new Filter<Score>() {
         public boolean match(Score s) {
            return s.getStrategien().size() == score.getStrategien().size() && s.getStrategien().containsAll(score.getStrategien());
         }
      });
   }

   public ListSelectionModel getSelectionModel() {
      ReadOnlyListSelectionModel readOnlyListSelectionModel = new ReadOnlyListSelectionModel(this.getNewestHighScoreIndex());
      return readOnlyListSelectionModel;
   }

   private static enum Columns {
      STRATEGIEN,
      PUNKTE,
      MAX_PUNKTE,
      ZEIT;

      private Columns() {
      }
   }

   public static class ScoreComparator implements Comparator<Score> {
      public ScoreComparator() {
      }

      public int compare(Score o1, Score o2) {
         if (o1.getStrategien().size() < o2.getStrategien().size()) {
            return 1;
         } else if (o1.getStrategien().size() > o2.getStrategien().size()) {
            return -1;
         } else {
            int compareEntries = this.compareEntries(o1, o2, 0);
            if (compareEntries == 0) {
               float deltaPkt = (float)o2.getPunkte() / (float)o2.getMaxpunkte() - (float)o1.getPunkte() / (float)o1.getMaxpunkte();
               if (deltaPkt == 0.0F) {
                  Long long1 = o1.getZeit() - o2.getZeit();
                  return long1.intValue();
               } else {
                  return Math.round(deltaPkt * 1000.0F);
               }
            } else {
               return compareEntries;
            }
         }
      }

      private int compareEntries(Score o1, Score o2, int i) {
         if (o1.getStrategien().size() > i) {
            int compareTo = ((String)o1.getStrategien().get(i)).compareTo((String)o2.getStrategien().get(i));
            return compareTo == 0 ? this.compareEntries(o1, o2, i + 1) : compareTo;
         } else {
            return 0;
         }
      }
   }
}
