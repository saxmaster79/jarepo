package ch.theband.benno.jarepo;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableColumn;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;

public class OptionenDialog extends JDialog {
   private static final Logger logger = Logger.getLogger(OptionenDialog.class.getName());
   private static final long serialVersionUID = 1L;
   private JLabel ordnerLabel;
   private JScrollPane jScrollPane1;
   private JButton openFileChooser;
   private JButton abbrechenButton;
   private JButton okButton;
   private JTable dateien;
   private JLabel jLabel1;
   private JLabel anzahlRechnungenLabel;
   private JTextField pfad;
   private JSpinner anzahlrechnungen;
   private boolean ok = false;
   private DateienModel dateienModel;

   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame();
            OptionenDialog inst = new OptionenDialog(frame);
            inst.setVisible(true);
         }
      });
   }

   public OptionenDialog(JFrame frame) {
      super(frame, true);
      this.initGUI();
   }

   private void initGUI() {
      try {
         ApplicationContext ctxt = Application.getInstance().getContext();
         ActionMap map = ctxt.getActionMap(this);
         MigLayout thisLayout = new MigLayout();
         thisLayout.setColumnConstraints("[][fill, grow]");
         thisLayout.setRowConstraints("[][][][grow][]");
         this.setLayout(thisLayout);
         this.ordnerLabel = new JLabel();
         this.ordnerLabel.setName("ordnerLabel");
         this.add(this.ordnerLabel, "cell 0 0");
         this.pfad = new JTextField();
         this.add(this.pfad, "cell 1 0");
         this.pfad.setEditable(false);
         this.openFileChooser = new JButton();
         this.getContentPane().add(this.openFileChooser, "cell 1 0, w 24lp:32lp:pref");
         this.openFileChooser.setName("openFileChooser");
         this.openFileChooser.setAction(map.get("openFileChooser"));
         this.anzahlRechnungenLabel = new JLabel();
         this.add(this.anzahlRechnungenLabel, "cell 0 1");
         this.anzahlRechnungenLabel.setName("anzahlRechnungen");
         SpinnerNumberModel model = new SpinnerNumberModel(10, 1, (Comparable)null, 1);
         this.anzahlrechnungen = new JSpinner(model);
         this.add(this.anzahlrechnungen, "cell 1 1, w 48lp:48lp:max, gapright push");
         this.jLabel1 = new JLabel();
         this.add(this.jLabel1, "cell 0 2 2 1");
         this.jLabel1.setName("jLabel1");
         this.jScrollPane1 = new JScrollPane();
         this.getContentPane().add(this.jScrollPane1, "cell 0 3 2 1,growx, growy, h min:256lp:max");
         this.dateien = new JTable();
         this.jScrollPane1.setViewportView(this.dateien);
         this.dateien.setDefaultRenderer(File.class, new FileRenderer());
         this.dateienModel = new DateienModel();
         this.dateien.setModel(this.dateienModel);
         TableColumn selectedColumn = this.dateien.getColumnModel().getColumn(0);
         selectedColumn.setMaxWidth(22);
         this.okButton = new JButton();
         this.getContentPane().add(this.okButton, "cell 0 4 2, tag ok");
         this.okButton.setName("okButton");
         this.okButton.setAction(map.get("ok"));
         this.abbrechenButton = new JButton();
         this.getContentPane().add(this.abbrechenButton, "cell 0 4, tag cancel");
         this.abbrechenButton.setName("abbrechenButton");
         this.abbrechenButton.setAction(map.get("abbrechen"));
         ctxt.getResourceMap(this.getClass()).injectComponents(this);
         this.pack();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   @Action
   public void abbrechen() {
      this.setVisible(false);
   }

   @Action
   public void ok() {
      this.ok = true;
      this.abbrechen();
   }

   @Action
   public void openFileChooser() {
      JFileChooser jfc = new JFileChooser();
      jfc.setFileSelectionMode(1);

      try {
         jfc.setCurrentDirectory(new File(this.pfad.getText()));
      } catch (Exception var4) {
      }

      int returnVal = jfc.showOpenDialog(this);
      if (returnVal == 0) {
         System.out.println("You chose to open this file: " + jfc.getSelectedFile().getName());
         String dir = jfc.getSelectedFile().getAbsolutePath();
         this.pfad.setText(dir);
         this.dateienModel.setDirectory(dir);
      }

   }

   public void setConfig(JaRePoConfig loadedConfig) {
      this.anzahlrechnungen.getModel().setValue(loadedConfig.getAnzahlAufgaben());
      List<File> filesInConfig = loadedConfig.getFiles();
      String directory;
      File file;
      if (!filesInConfig.isEmpty()) {
         file = (File)filesInConfig.get(0);
         directory = file.getParent();
      } else {
         file = new File("." + File.separator + "Rechnungsstrategien");
         if (!file.exists()) {
            file = new File(".");
         }

         try {
            directory = file.getCanonicalPath();
         } catch (IOException var7) {
            logger.log(Level.WARNING, "", var7);
            directory = ".";
         }
      }

      this.dateienModel.setDirectory(directory);
      this.pfad.setText(directory);
      List<Pair<Boolean, File>> dateienInDir = this.dateienModel.getDateien();
      Iterator var6 = dateienInDir.iterator();

      while(var6.hasNext()) {
         Pair<Boolean, File> pair = (Pair)var6.next();
         if (filesInConfig.contains(pair.getRight())) {
            pair.setLeft(Boolean.TRUE);
         }
      }

   }

   public JaRePoConfig getConfig() {
      JaRePoConfig config = new JaRePoConfig();
      config.setAnzahlAufgaben((Integer)this.anzahlrechnungen.getModel().getValue());
      List<Pair<Boolean, File>> dateienInDir = this.dateienModel.getDateien();
      Iterator var4 = dateienInDir.iterator();

      while(var4.hasNext()) {
         Pair<Boolean, File> pair = (Pair)var4.next();
         if ((Boolean)pair.getLeft()) {
            config.addFile((File)pair.getRight());
         }
      }

      return config;
   }

   public boolean isOk() {
      return this.ok;
   }
}
