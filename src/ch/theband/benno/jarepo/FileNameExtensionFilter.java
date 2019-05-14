package ch.theband.benno.jarepo;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameExtensionFilter implements FilenameFilter {
   private final String[] extensions;

   public FileNameExtensionFilter(String... extensions) {
      this.extensions = new String[extensions.length];

      for(int i = 0; i < extensions.length; ++i) {
         this.extensions[i] = "." + extensions[i];
      }

   }

   public boolean accept(File dir, String name) {
      String[] var6;
      int var5 = (var6 = this.extensions).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String ext = var6[var4];
         if (name.endsWith(ext)) {
            return true;
         }
      }

      return false;
   }
}
