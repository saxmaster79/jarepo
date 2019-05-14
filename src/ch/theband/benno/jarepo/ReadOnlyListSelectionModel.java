package ch.theband.benno.jarepo;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

final class ReadOnlyListSelectionModel implements ListSelectionModel {
   private List<ListSelectionListener> listeners;
   private int selection;

   public ReadOnlyListSelectionModel(int index) {
      assert index > 0 : "index sollte positiv sein";

      this.selection = index;
      this.listeners = new ArrayList();
   }

   public int getSelection() {
      return this.selection;
   }

   public void setSelection(int selection) {
      this.selection = selection;
   }

   public void addSelectionInterval(int index0, int index1) {
   }

   public void insertIndexInterval(int index, int length, boolean before) {
   }

   public void removeIndexInterval(int index0, int index1) {
   }

   public void removeSelectionInterval(int index0, int index1) {
   }

   public void addListSelectionListener(ListSelectionListener x) {
      this.listeners.add(x);
   }

   public void clearSelection() {
   }

   public int getAnchorSelectionIndex() {
      return this.selection;
   }

   public int getLeadSelectionIndex() {
      return this.selection;
   }

   public int getMaxSelectionIndex() {
      return this.selection;
   }

   public int getMinSelectionIndex() {
      return this.selection;
   }

   public boolean isSelectedIndex(int index) {
      return index == this.selection;
   }

   public int getSelectionMode() {
      return 0;
   }

   public boolean getValueIsAdjusting() {
      return false;
   }

   public boolean isSelectionEmpty() {
      return false;
   }

   public void removeListSelectionListener(ListSelectionListener x) {
      this.listeners.add(x);
   }

   public void setAnchorSelectionIndex(int index) {
   }

   public void setLeadSelectionIndex(int index) {
   }

   public void setSelectionInterval(int index0, int index1) {
   }

   public void setSelectionMode(int selectionMode) {
   }

   public void setValueIsAdjusting(boolean valueIsAdjusting) {
   }
}
