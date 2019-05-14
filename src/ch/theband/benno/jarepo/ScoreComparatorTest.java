package ch.theband.benno.jarepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScoreComparatorTest {
   private List<Score> scores;
   private HighScoreTableModel.ScoreComparator sc;

   public ScoreComparatorTest() {
   }

   @Before
   public void setUp() throws Exception {
      this.scores = new ArrayList();
      this.sc = new HighScoreTableModel.ScoreComparator();
   }

   @Test
   public void testCompare() {
      Score s1 = new Score(new ArrayList(), 3, 3, 20000L);
      Score s2 = new Score(new ArrayList(), 3, 4, 20000L);
      int compare = this.sc.compare(s1, s2);
      Assert.assertTrue(compare + " !< 0", compare < 0);
      ArrayList<String> list = new ArrayList(Arrays.asList("asdf", "bsdf"));
      new Score(list, 3, 3, 20000L);
      list = new ArrayList(Arrays.asList("asdf"));
      new Score(list, 3, 3, 20000L);
      Assert.assertTrue(compare < 0);
   }

   @Test
   public void testSort() {
      Score s1 = new Score(new ArrayList(), 3, 3, 20000L);
      this.scores.add(s1);
      Score s2 = new Score(new ArrayList(), 3, 4, 20000L);
      this.scores.add(s2);
      ArrayList<String> list = new ArrayList(Arrays.asList("asdf", "bsdf"));
      Score s3 = new Score(list, 3, 3, 20000L);
      this.scores.add(s3);
      list = new ArrayList(Arrays.asList("asdf", "bsdf"));
      Score s4 = new Score(list, 3, 3, 30000L);
      this.scores.add(s4);
      Collections.sort(this.scores, this.sc);
      Assert.assertEquals(s3, this.scores.get(0));
      Assert.assertEquals(s4, this.scores.get(1));
      Assert.assertEquals(s1, this.scores.get(2));
      Assert.assertEquals(s2, this.scores.get(3));
   }
}
