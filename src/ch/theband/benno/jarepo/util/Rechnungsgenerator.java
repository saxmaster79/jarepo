package ch.theband.benno.jarepo.util;

public class Rechnungsgenerator {
   public Rechnungsgenerator() {
   }

   public static void main(String[] args) {
      int anzahl = 0;

      for(int i = 0; i <= 20; ++i) {
         for(int j = 0; j <= 20; ++j) {
            int resultat = i - j;
            if (resultat >= 0) {
               ++anzahl;
               System.out.println(i + " - " + j + " = [" + resultat + "]");
            }
         }
      }

      System.out.println(anzahl + " Rechnungen");
   }
}
