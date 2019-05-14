package ch.theband.benno.jarepo.io;

import ch.theband.benno.jarepo.JaRePoConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.logging.Logger;
import org.jdesktop.application.LocalStorage;

public class ConfigReaderWriter extends LocalStorageReaderWriter {
   private static final String FILE_NAME = "jarepoconfig.ini";
   private static final Logger logger = Logger.getLogger(ConfigReaderWriter.class.getName());
   private final LocalStorage storage;

   public ConfigReaderWriter(LocalStorage storage) {
      this.storage = storage;
   }

   public void save(JaRePoConfig config) throws IOException {
      OutputStream stream = this.storage.openOutputFile("jarepoconfig.ini", false);
      BufferedWriter w = new BufferedWriter(new OutputStreamWriter(stream));

      try {
         this.writeKeyValue(w, ConfigReaderWriter.Keys.VERSION, "1");
         this.writeKeyValue(w, ConfigReaderWriter.Keys.ANZAHL_AUFGABEN, config.getAnzahlAufgaben());
         Iterator var5 = config.getFiles().iterator();

         while(var5.hasNext()) {
            File file = (File)var5.next();
            this.writeKeyValue(w, ConfigReaderWriter.Keys.FILE, file.getAbsolutePath());
         }

         logger.finer("saving file " + w);
      } finally {
         w.close();
      }
   }

   public JaRePoConfig load() throws IOException, WrongVersionException {
      JaRePoConfig config = new JaRePoConfig();
      InputStream stream = null;

      try {
         stream = this.storage.openInputFile("jarepoconfig.ini");
         LineNumberReader reader = new LineNumberReader(new BufferedReader(new InputStreamReader(stream)));

         String line;
         while((line = reader.readLine()) != null) {
            String[] split = line.split("=");
            String value = split[1];
            if (split.length == 2) {
               switch(ConfigReaderWriter.Keys.valueOf(split[0])) {
               case VERSION:
                  if (!value.equals("1")) {
                     throw new WrongVersionException("Unbekannte Version " + value);
                  }
                  break;
               case ANZAHL_AUFGABEN:
                  config.setAnzahlAufgaben(Integer.valueOf(value));
                  break;
               case FILE:
                  File file = new File(value);
                  config.addFile(file);
                  break;
               default:
                  logger.info("Ignored " + line);
               }
            }
         }
      } finally {
         if (stream != null) {
            stream.close();
         }

      }

      return config;
   }

   private static enum Keys implements Key {
      VERSION,
      ANZAHL_AUFGABEN,
      FILE;

      private Keys() {
      }
   }
}
