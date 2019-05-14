package ch.theband.benno.jarepo.io;

import ch.theband.benno.jarepo.Rechnung;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RechnungReader {
   List<File> files = new ArrayList();

   public RechnungReader(List<File> files) {
      this.files = files;
   }

   public List<Rechnung> read() throws IOException {
      List<Rechnung> rechnungen = new ArrayList();
      Iterator var3 = this.files.iterator();

      while(var3.hasNext()) {
         File file = (File)var3.next();
         BufferedReader input = new BufferedReader(new FileReader(file));

         try {
            String line = null;

            while((line = input.readLine()) != null) {
               if (!line.isEmpty() && !line.startsWith("#")) {
                  rechnungen.add(this.decode(line));
               }
            }
         } catch (IllegalArgumentException var9) {
            System.out.println(var9);
         } finally {
            input.close();
         }
      }

      return rechnungen;
   }

   private Rechnung decode(String line) {
      int posKlammerAuf = line.indexOf(91);
      int posKlammerZu = line.indexOf(93);
      if (posKlammerAuf >= 0 && posKlammerZu >= 0 && posKlammerZu >= posKlammerAuf) {
         String luecke = line.substring(posKlammerAuf + 1, posKlammerZu);
         String vorluecke = this.getSubstring(line, 0, posKlammerAuf);
         String nachluecke = this.getSubstring(line, posKlammerZu + 1, line.length());
         return new Rechnung(luecke, vorluecke, nachluecke);
      } else {
         throw new IllegalArgumentException();
      }
   }

   private String getSubstring(String line, int startIndex, int endIndex) {
      String sub = line.substring(startIndex, endIndex);
      return sub.isEmpty() ? null : sub;
   }
}
