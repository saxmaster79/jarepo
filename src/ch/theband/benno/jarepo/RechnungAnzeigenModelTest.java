package ch.theband.benno.jarepo;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RechnungAnzeigenModelTest {
   private static final int ANZ_RG = 3;
   private List<Rechnung> rechnungen;
   private RechnungAnzeigenModel model;

   public RechnungAnzeigenModelTest() {
   }

   @Before
   public void setUp() throws Exception {
      List<String> strategien = Arrays.asList("Strat2", "Strat3");
      this.rechnungen = Arrays.asList(new Rechnung("2", "1+1", ""), new Rechnung("3", "1+2", (String)null), new Rechnung("5", "", "2+3"));
      this.model = new RechnungAnzeigenModel(strategien, this.rechnungen, 3, JaRePoMode.NORMAL, false);
   }

   @Test
   public void testNextRechnung() {
      this.model.nextRechnung();
      Assert.assertEquals("1+2", this.model.getVorLuecke());
      Assert.assertEquals((Object)null, this.model.getNachLuecke());
   }

   @Test
   public void testCheckResultat() {
      this.model.setLuecke("2");
      Assert.assertTrue(this.model.checkResultat());
   }

   @Test
   public void testIsKorrekt() {
      this.model.setLuecke("2");
      Assert.assertTrue(this.model.isKorrekt());
   }

   @Test
   public void testIsFertig() {
      for(int i = 0; i <= 3; ++i) {
         this.model.nextRechnung();
      }

      Assert.assertTrue(this.model.isFertig());
   }

   @Test
   public void testGetScore() {
      this.checkResult("2");
      this.checkResult("3");
      this.checkResult("6");
      this.checkResult("5");
      Score score = this.model.getScore();
      Assert.assertEquals(5L, (long)score.getPunkte());
      Assert.assertEquals(6L, (long)score.getMaxpunkte());
   }

   @Test
   public void testGetScoreVielFalsch() {
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("2");
      this.checkResult("3");
      this.checkResult("6");
      this.checkResult("5");
      Score score = this.model.getScore();
      Assert.assertEquals(3L, (long)score.getPunkte());
      Assert.assertEquals(6L, (long)score.getMaxpunkte());
   }

   @Test
   public void testGetScoreNochMehrFalsch() {
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("2");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("3");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("1");
      this.checkResult("5");
      Score score = this.model.getScore();
      Assert.assertEquals(0L, (long)score.getPunkte());
      Assert.assertEquals(6L, (long)score.getMaxpunkte());
   }

   private void checkResult(String luecke) {
      this.model.setLuecke(luecke);
      if (this.model.checkResultat()) {
         this.model.nextRechnung();
      }

   }
}
