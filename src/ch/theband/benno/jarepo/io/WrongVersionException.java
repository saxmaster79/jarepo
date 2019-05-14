package ch.theband.benno.jarepo.io;

public class WrongVersionException extends Exception {
   private static final long serialVersionUID = 1L;

   public WrongVersionException(String string) {
      super(string);
   }
}
