package ch.theband.benno.jarepo;

public class Rechnung {
   private String luecke;
   private String vorLuecke;
   private String nachLuecke;

   public Rechnung(String luecke, String vorLuecke, String nachLuecke) {
      this.luecke = luecke;
      this.vorLuecke = vorLuecke;
      this.nachLuecke = nachLuecke;
   }

   public String getLuecke() {
      return this.luecke;
   }

   public void setLuecke(String luecke) {
      this.luecke = luecke;
   }

   public String getVorLuecke() {
      return this.vorLuecke;
   }

   public void setVorLuecke(String vorLuecke) {
      this.vorLuecke = vorLuecke;
   }

   public String getNachLuecke() {
      return this.nachLuecke;
   }

   public void setNachLuecke(String nachLuecke) {
      this.nachLuecke = nachLuecke;
   }

   public String toString() {
      return this.getClass().getName() + ": " + this.vorLuecke + "[" + this.luecke + "]" + this.nachLuecke;
   }
}
