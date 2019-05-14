package ch.theband.benno.jarepo;

import ch.theband.benno.jarepo.io.ConfigReaderWriter;
import ch.theband.benno.jarepo.io.RechnungReader;
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;

public class JaRePo extends SingleFrameApplication {
   private static final Logger logger = Logger.getLogger(JaRePo.class.getName());
   private RechnungAnzeigenPanel rap;
   private JaRePoConfig loadedConfig;
   private JaRePoMode mode;

   public JaRePo() {
      this.mode = JaRePoMode.NORMAL;
   }

   protected void startup() {
      JToolBar toolbar = new JToolBar();
      JButton playPauseButton = new JButton();
      playPauseButton.setName("playPauseButton");
      JButton showOptionenButton = new JButton();
      showOptionenButton.setName("showOptionenButton");
      toolbar.add(playPauseButton);
      toolbar.add(showOptionenButton);
      ApplicationContext ctxt = this.getContext();
      ResourceMap resource = ctxt.getResourceMap(JaRePo.class);
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());
      panel.add(toolbar, "North");
      resource.injectComponent(panel);
      ActionMap map = ctxt.getActionMap(this);
      playPauseButton.setAction(map.get("playPause"));
      showOptionenButton.setAction(map.get("showOptionen"));
      this.rap = new RechnungAnzeigenPanel();
      panel.add(this.rap, "Center");
      this.rap.setVisible(false);
      JPanel logoPanel = new JPanel();
      logoPanel.setName("logoPanel");
      JLabel logo = new JLabel();
      logo.setName("logo");
      logoPanel.add(logo);
      panel.add(logoPanel, "South");
      this.show(panel);
      this.getMainFrame().setExtendedState(6);
   }

   @Action(
      enabledProperty = "actionsEnabled"
   )
   public void playPause() {
      System.out.println("playPause");
      this.startGame(JaRePoMode.NORMAL);
   }

   @Action(
      enabledProperty = "actionsEnabled"
   )
   public void showOptionen() {
      System.out.println("showOptionen");
      OptionenDialog dialog = new OptionenDialog(this.getMainFrame());
      dialog.setConfig(this.loadedConfig);
      dialog.setVisible(true);
      if (dialog.isOk()) {
         this.loadedConfig = dialog.getConfig();
         ConfigReaderWriter crw = new ConfigReaderWriter(this.getContext().getLocalStorage());

         try {
            crw.save(this.loadedConfig);
         } catch (IOException var4) {
            logger.log(Level.WARNING, "saving config failed", var4);
            JOptionPane.showMessageDialog(this.getMainFrame(), var4.getMessage(), (String)null, 0);
         }
      }

   }

   @Action(
      enabledProperty = "actionsEnabled"
   )
   public void test() {
      this.startGame(JaRePoMode.TEST);
   }

   private void startGame(JaRePoMode mode) {
      this.setMode(mode);
      RechnungReader reader = new RechnungReader(this.loadedConfig.getFiles());

      try {
         List<Rechnung> rechnungen = reader.read();
         System.out.println(rechnungen);
         this.rap.setVisible(true);
         this.rap.setModel(new RechnungAnzeigenModel(this.loadedConfig.getStrategien(), rechnungen, this.loadedConfig.getAnzahlAufgaben(), mode));
      } catch (IOException var4) {
         logger.log(Level.WARNING, "", var4);
         JOptionPane.showMessageDialog(this.getMainFrame(), var4.getMessage(), (String)null, 0);
      }

   }

   protected void initialize(String[] args) {
      ConfigReaderWriter crw = new ConfigReaderWriter(this.getContext().getLocalStorage());

      try {
         this.loadedConfig = crw.load();
      } catch (Exception var4) {
         logger.log(Level.WARNING, "loading config failed", var4);
         this.loadedConfig = new JaRePoConfig();
      }

   }

   public static void main(String[] args) {
      Application.launch(JaRePo.class, args);
   }

   public boolean isActionsEnabled() {
      return this.mode.actionsEnabled;
   }

   public void setMode(JaRePoMode mode) {
      boolean oldValue = this.isActionsEnabled();
      this.mode = mode;
      this.firePropertyChange("actionsEnabled", oldValue, this.isActionsEnabled());
   }
}
