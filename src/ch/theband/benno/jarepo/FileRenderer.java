package ch.theband.benno.jarepo;

import java.io.File;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class FileRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
   private static final long serialVersionUID = 1L;

   public FileRenderer() {
   }

   protected void setValue(Object value) {
      if (value instanceof File) {
         File f = (File)value;
         this.setText(f.getName());
      } else {
         super.setValue(value);
      }

   }
}
