package ch.theband.benno.jarepo.io;

import java.io.BufferedWriter;
import java.io.IOException;

public class LocalStorageReaderWriter {
   protected static final String SEPARATOR = "=";

   public LocalStorageReaderWriter() {
   }

   public void writeKeyValue(BufferedWriter w, Key key, int i) throws IOException {
      w.write(key.name() + "=" + i);
      w.newLine();
   }

   public void writeKeyValue(BufferedWriter w, Key key, long l) throws IOException {
      w.write(key.name() + "=" + l);
      w.newLine();
   }

   public void writeKeyValue(BufferedWriter w, Key key, String value) throws IOException {
      w.write(key.name() + "=" + value);
      w.newLine();
   }
}
