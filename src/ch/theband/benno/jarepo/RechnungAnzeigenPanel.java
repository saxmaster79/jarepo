package ch.theband.benno.jarepo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Application;

public class RechnungAnzeigenPanel extends JPanel {
   private static final long serialVersionUID = 1L;
   private static final Color KORREKT_COLOR;
   private static final Color FALSCH_COLOR;
   private RechnungAnzeigenModel model;
   private JTextField lueckeTextField;
   private JLabel nachLueckeLabel;
   private JLabel vorLueckeLabel;
   private Icon icon;

   static {
      KORREKT_COLOR = Color.BLUE;
      FALSCH_COLOR = Color.RED;
   }

   public RechnungAnzeigenPanel() {
      this.initGUI();
   }

   private void initGUI() {
      try {
         MigLayout ml = new MigLayout("nogrid", "push[]push", "push[]push");
         this.setLayout(ml);
         this.vorLueckeLabel = new JLabel();
         this.add(this.vorLueckeLabel);
         this.vorLueckeLabel.setName("vorLuecke");
         this.lueckeTextField = new JTextField();
         this.add(this.lueckeTextField, "w min:100:max, h min:54:max");
         this.lueckeTextField.setName("luecke");
         this.lueckeTextField.setHorizontalAlignment(4);
         this.lueckeTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               RechnungAnzeigenPanel.this.checkResultat();
            }
         });
         this.nachLueckeLabel = new JLabel();
         this.add(this.nachLueckeLabel);
         this.nachLueckeLabel.setName("nachLuecke");
         Application.getInstance().getContext().getResourceMap(this.getClass()).injectComponents(this);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public RechnungAnzeigenModel getModel() {
      return this.model;
   }

   public void setModel(RechnungAnzeigenModel model) {
      this.model = model;
      this.updateGUI();
   }

   private void updateModel() {
      this.model.setLuecke(this.lueckeTextField.getText());
   }

   private void updateGUI() {
      if (this.model.isFertig()) {
         this.lueckeTextField.setEnabled(false);
      } else {
         this.lueckeTextField.setEnabled(true);
         String vorLuecke = this.model.getVorLuecke();
         this.setTextToLabel(this.vorLueckeLabel, vorLuecke);
         String nachLuecke = this.model.getNachLuecke();
         this.setTextToLabel(this.nachLueckeLabel, nachLuecke);
         this.lueckeTextField.requestFocusInWindow();
      }

   }

   private void setTextToLabel(JLabel label, String textToShow) {
      label.setText(textToShow);
      if (textToShow != null && !textToShow.isEmpty()) {
         label.setVisible(true);
      } else {
         label.setVisible(false);
      }

   }

   private void checkResultat() {
      this.updateModel();
      if (this.model.checkResultat()) {
         this.model.nextRechnung();
         this.markLuecke(KORREKT_COLOR);
         this.lueckeTextField.setText("");
         if (this.model.isFertig()) {
            ResultDialog r = new ResultDialog((JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, this));
            this.showHighScore(r);
         }

         this.updateGUI();
      } else {
         this.markLuecke(FALSCH_COLOR);
         this.lueckeTextField.requestFocus();
      }

   }

   private void showHighScore(ResultDialog r) {
      String s = (String)JOptionPane.showInputDialog(this, "Bitte gib deinen Namen ein", "Namen eingeben", -1, this.icon, (Object[])null, "");
      if (s != null && s.length() > 0) {
         r.setUsername(s);
         r.addScore(this.model.getScore());
         r.setVisible(true);
      }

   }

   public void markLuecke(Color c) {
      this.lueckeTextField.setBackground(Color.WHITE);
      this.lueckeTextField.setSelectionColor(c);
      this.lueckeTextField.setSelectionStart(0);
      this.lueckeTextField.setSelectionEnd(this.lueckeTextField.getText().length());
   }
}
