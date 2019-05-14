package ch.theband.benno.jarepo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class JaRePoConfig implements Serializable {
   private static final long serialVersionUID = 1L;
   private int anzahlAufgaben = 10;
   private transient ArrayList<File> files = new ArrayList();

   public JaRePoConfig() {
   }

   public void setFiles(ArrayList<File> files) {
      this.files = files;
   }

   public int getAnzahlAufgaben() {
      return this.anzahlAufgaben;
   }

   public void setAnzahlAufgaben(int anzahlAufgaben) {
      this.anzahlAufgaben = anzahlAufgaben;
   }

   public void addFile(File file) {
      this.files.add(file);
   }

   public ArrayList<File> getFiles() {
      return this.files;
   }

   public ArrayList<String> getStrategien() {
      ArrayList<String> list = new ArrayList(this.files.size());
      Iterator var3 = this.files.iterator();

      while(var3.hasNext()) {
         File file = (File)var3.next();
         list.add(StringUtils.removeEnd(StringUtils.removeEnd(file.getName(), ".txt"), ".jarepo"));
      }

      Collections.sort(list);
      return list;
   }

   public String toString() {
      ToStringBuilder b = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
      b.append("anzahlAufgaben", this.anzahlAufgaben);
      b.append("files", this.files);
      return b.toString();
   }
}
