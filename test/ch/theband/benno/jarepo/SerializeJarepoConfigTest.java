package ch.theband.benno.jarepo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.junit.Assert;
import org.junit.Test;

public class SerializeJarepoConfigTest {
   public SerializeJarepoConfigTest() {
   }

   @Test
   public void testSimpleTestBean() throws FileNotFoundException {
      TestBean toSave = new TestBean();
      toSave.initStuff();
      TestBean readBack = (TestBean)this.writeAndReadBack(toSave, "TestBean.xml");
      Assert.assertEquals(toSave, readBack);
   }

   @Test
   public void testSerializability() throws FileNotFoundException {
      JaRePoConfig writeConfig = new JaRePoConfig();
      writeConfig.setAnzahlAufgaben(3);
      writeConfig.addFile(new File("c:\\Users\\benno\\workspace\\optionen.png"));
      JaRePoConfig readConfig = (JaRePoConfig)this.writeAndReadBack(writeConfig, "Test.xml");
      Assert.assertEquals((long)writeConfig.getAnzahlAufgaben(), (long)readConfig.getAnzahlAufgaben());
      Assert.assertEquals(writeConfig.getFiles(), readConfig.getFiles());
   }

   private <T> T writeAndReadBack(T writeConfig, String fileName) throws FileNotFoundException {
      XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)));
      e.writeObject(writeConfig);
      e.close();
      XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream(fileName)));
      Object obj = d.readObject();
      return (T) obj;
   }

   @Test
   public void testSerializabilityFile() throws FileNotFoundException {
      File wfile = new File("c:\\Users\\benno\\workspace\\optionen.png");
      XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("Test2.xml")));
      e.writeObject(wfile);
      e.close();
      XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("Test2.xml")));
      File rfile = (File)d.readObject();
      Assert.assertEquals(wfile, rfile);
   }
}
