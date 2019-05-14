package ch.theband.benno.jarepo;

import java.io.Externalizable;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TestBean implements Externalizable {
   private static final long serialVersionUID = 1L;
   transient String bla;
   transient int zahl;
   transient ArrayList<String> list;
   transient Boolean bool;
   private File file;

   public TestBean() {
   }

   public void initStuff() {
      this.bla = "bla";
      this.zahl = 5;
      this.bool = Boolean.TRUE;
      this.list = new ArrayList(Arrays.asList("bla", "5", "TRUE"));
      this.file = new File("C:\\Users\\benno\\Desktop\\Rechnungsstrategien\\04. 0,1,2 mehr.txt");
   }

   public String getBla() {
      return this.bla;
   }

   public void setBla(String bla) {
      this.bla = bla;
   }

   public int getZahl() {
      return this.zahl;
   }

   public void setZahl(int zahl) {
      this.zahl = zahl;
   }

   public ArrayList<String> getList() {
      return this.list;
   }

   public void setList(ArrayList<String> list) {
      this.list = list;
   }

   public Boolean getBool() {
      return this.bool;
   }

   public void setBool(Boolean bool) {
      this.bool = bool;
   }

   public File getFile() {
      return this.file;
   }

   public void setFile(File file) {
      this.file = file;
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + (this.bla == null ? 0 : this.bla.hashCode());
      result = 31 * result + (this.bool == null ? 0 : this.bool.hashCode());
      result = 31 * result + (this.list == null ? 0 : this.list.hashCode());
      result = 31 * result + this.zahl;
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         TestBean other = (TestBean)obj;
         if (this.bla == null) {
            if (other.bla != null) {
               return false;
            }
         } else if (!this.bla.equals(other.bla)) {
            return false;
         }

         if (this.bool == null) {
            if (other.bool != null) {
               return false;
            }
         } else if (!this.bool.equals(other.bool)) {
            return false;
         }

         if (this.list == null) {
            if (other.list != null) {
               return false;
            }
         } else if (!this.list.equals(other.list)) {
            return false;
         }

         if (this.zahl != other.zahl) {
            return false;
         } else {
            if (this.file == null) {
               if (other.file != null) {
                  return false;
               }
            } else if (!this.file.equals(other.file)) {
               return false;
            }

            return true;
         }
      }
   }

   public String toString() {
      ToStringBuilder builder = new ToStringBuilder(this);
      builder.append("bla", this.bla);
      builder.append("bool", this.bool);
      builder.append("list", this.list);
      builder.append("zahl", this.zahl);
      builder.append("file", this.file);
      return builder.toString();
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      String path = (String)in.readObject();
      System.out.println(path);
      this.file = new File(path);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.file.getAbsoluteFile());
   }
}
