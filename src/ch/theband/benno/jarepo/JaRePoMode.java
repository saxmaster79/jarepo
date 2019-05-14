package ch.theband.benno.jarepo;

enum JaRePoMode {
   TEST(false),
   NORMAL(true);

   final boolean actionsEnabled;

   private JaRePoMode(boolean actionsEnabled) {
      this.actionsEnabled = actionsEnabled;
   }
}
