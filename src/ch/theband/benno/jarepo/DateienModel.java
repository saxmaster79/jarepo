package ch.theband.benno.jarepo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.lang.ObjectUtils;

public class DateienModel extends AbstractTableModel {
   private static final long serialVersionUID = 1L;
   private List<Pair<Boolean, File>> rows = new ArrayList();
   private String oldpfad;
   private static final Logger logger = Logger.getLogger(DateienModel.class.getName());

   public DateienModel() {
   }

   public void setDirectory(String pfad) {
      if (!ObjectUtils.equals(this.oldpfad, pfad)) {
         this.oldpfad = pfad;
         this.rows.clear();
         File dir = new File(pfad);

         try {
            if (dir.isDirectory()) {
               File[] listFiles = dir.listFiles(new FileNameExtensionFilter(new String[]{"txt", "jarepo"}));
               File[] var7 = listFiles;
               int var6 = listFiles.length;

               for(int var5 = 0; var5 < var6; ++var5) {
                  File file = var7[var5];
                  this.rows.add(new Pair(Boolean.FALSE, file));
               }

               Collections.sort(this.rows, new PairComparator(PairComparator.SortCriteria.RIGHT));
            }
         } catch (RuntimeException var8) {
            logger.log(Level.WARNING, "", var8);
         }

         this.fireTableDataChanged();
      }

   }

   public int getColumnCount() {
      return DateienModel.Columns.values().length;
   }

   public int getRowCount() {
      return this.rows.size();
   }

   public Object getValueAt(int rowIndex, int columnIndex) {
      Pair<Boolean, File> row = this.rows.get(rowIndex);
      switch(Columns.values()[columnIndex]) {
      case SELECTED:
         return row.getLeft();
         case FILENAME:
         return row.getRight();
      default:
         return null;
      }
   }

   public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      Pair<Boolean, File> row = this.rows.get(rowIndex);
      switch(Columns.values()[columnIndex]) {
      case SELECTED:
         this.rows.set(rowIndex, new Pair(aValue, row.getRight()));
      default:
      }
   }

   public Class<?> getColumnClass(int columnIndex) {
      return DateienModel.Columns.values()[columnIndex].getColumnClass();
   }

   public boolean isCellEditable(int rowIndex, int columnIndex) {
      return DateienModel.Columns.values()[columnIndex].getEditable();
   }

   public List<Pair<Boolean, File>> getDateien() {
      return this.rows;
   }

   public String getColumnName(int columnIndex) {
      switch(DateienModel.Columns.values()[columnIndex]) {
      case FILENAME:
         return "Dateiname";
      default:
         return "";
      }
   }

   private enum Columns {
      SELECTED(Boolean.class, true),
      FILENAME(File.class, false);

      private final Class<?> cls;
      private final boolean editable;

      private Columns(Class<?> cls, boolean editable) {
         this.cls = cls;
         this.editable = editable;
      }

      public Class<?> getColumnClass() {
         return this.cls;
      }

      public boolean getEditable() {
         return this.editable;
      }
   }
}
