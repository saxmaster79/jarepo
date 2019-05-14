package ch.theband.benno.jarepo;

import java.io.File;
import java.util.Comparator;

public class PairComparator implements Comparator<Pair<Boolean, File>> {
   private final PairComparator.SortCriteria field;
   private final PairComparator.Direction dir;

   public PairComparator(PairComparator.SortCriteria field) {
      this(field, PairComparator.Direction.ASC);
   }

   public PairComparator(PairComparator.SortCriteria field, PairComparator.Direction dir) {
      this.field = field;
      this.dir = dir;
   }

   public int compare(Pair<Boolean, File> o1, Pair<Boolean, File> o2) {
      return PairComparator.SortCriteria.LEFT == this.field ? ((Boolean)o1.getLeft()).compareTo((Boolean)o2.getLeft()) * this.dir.factor : ((File)o2.getRight()).compareTo((File)o2.getRight()) * this.dir.factor;
   }

   public static enum Direction {
      ASC(1),
      DESC(-1);

      private int factor;

      private Direction(int factor) {
         this.factor = factor;
      }
   }

   public static enum SortCriteria {
      LEFT,
      RIGHT;

      private SortCriteria() {
      }
   }
}
