package ch.theband.benno.jarepo;

public class Pair<L, R> {
   private L left;
   private final R right;

   public Pair(L left, R right) {
      this.left = left;
      this.right = right;
   }

   public L getLeft() {
      return this.left;
   }

   public R getRight() {
      return this.right;
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + (this.left == null ? 0 : this.left.hashCode());
      result = 31 * result + (this.right == null ? 0 : this.right.hashCode());
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
         Pair<?, ?> other = (Pair)obj;
         if (this.left == null) {
            if (other.left != null) {
               return false;
            }
         } else if (!this.left.equals(other.left)) {
            return false;
         }

         if (this.right == null) {
            if (other.right != null) {
               return false;
            }
         } else if (!this.right.equals(other.right)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return "Pair [" + this.left + ", " + this.right + "]";
   }

   public void setLeft(L val) {
      this.left = val;
   }
}
