package ch.theband.benno.jarepo.util;

import java.util.Collection;
import java.util.Iterator;

public class CollectionHelper {
   public CollectionHelper() {
   }

   public static <S> void filter(Collection<S> col, Filter<? super S> filter) {
      Iterator<S> iter = col.iterator();

      while(iter.hasNext()) {
         if (!filter.match(iter.next())) {
            iter.remove();
         }
      }

   }
}
