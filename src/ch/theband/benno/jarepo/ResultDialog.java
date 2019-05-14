package ch.theband.benno.jarepo;

import ch.theband.benno.jarepo.io.HighScoreReadWriter;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.ApplicationActionMap;
import org.jdesktop.application.LocalStorage;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.Task.BlockingScope;

public class ResultDialog extends JDialog {
   private JLabel logo;
   private JTable highScore;
   private JButton okButton;
   private JLabel title;
   private JPanel contentPanel;
   private String username;
   private static final Logger logger = Logger.getLogger(ResultDialog.class.getName());

   public ResultDialog(JFrame frame) {
      super(frame);
      this.initGUI();
   }

   private void initGUI() {
      MigLayout thisLayout = new MigLayout();
      thisLayout.setColumnConstraints("[][fill, grow, 200lp:pref:max]");
      this.getContentPane().setLayout(thisLayout);
      this.logo = new JLabel();
      FlowLayout logoLayout = new FlowLayout(1);
      this.getContentPane().add(this.logo);
      this.logo.setLayout(logoLayout);
      this.logo.setName("logo");
      this.contentPanel = new JPanel();
      MigLayout jPanel1Layout = new MigLayout();
      jPanel1Layout.setColumnConstraints("[grow]");
      jPanel1Layout.setRowConstraints("[][]push[]");
      this.contentPanel.setLayout(jPanel1Layout);
      this.title = new JLabel();
      this.contentPanel.add(this.title, "cell 0 0 2 1,grow");
      this.title.setName("title");
      this.okButton = new JButton();
      this.contentPanel.add(this.okButton, "tag ok,cell 0 2,alignx right,aligny bottom");
      this.okButton.setName("ok");
      this.okButton.setAction(this.getAppActionMap().get("ok"));
      this.highScore = new JTable();
      this.highScore.setAutoCreateColumnsFromModel(true);
      JScrollPane scroll = new JScrollPane();
      scroll.setViewportView(this.highScore);
      this.contentPanel.add(scroll, "cell 0 1, grow, width 200:300:max");
      this.getContentPane().add(this.contentPanel);
      ResourceMap resourceMap = Application.getInstance().getContext().getResourceMap(this.getClass());
      resourceMap.injectComponents(this.getContentPane());
      resourceMap.injectFields(this);
      this.pack();
   }

   @Action(
      block = BlockingScope.WINDOW
   )
   public void ok() {
      this.setCursor(Cursor.getPredefinedCursor(3));
      LocalStorage localStorage = Application.getInstance().getContext().getLocalStorage();
      HighScoreReadWriter writer = new HighScoreReadWriter(localStorage, this.username);

      try {
         writer.writeHighScore(((HighScoreTableModel)this.highScore.getModel()).getScores());
      } catch (IOException var4) {
         logger.log(Level.WARNING, "saving config failed", var4);
         JOptionPane.showMessageDialog(this, var4.getMessage(), (String)null, 0);
      }

      this.setVisible(false);
      this.setCursor(Cursor.getDefaultCursor());
   }

   private ApplicationActionMap getAppActionMap() {
      return Application.getInstance().getContext().getActionMap(this);
   }

   public void setUsername(String username) {
      this.username = username;
      LocalStorage localStorage = Application.getInstance().getContext().getLocalStorage();
      HighScoreReadWriter reader = new HighScoreReadWriter(localStorage, username);
      ArrayList<Score> scores = reader.readHighScore();
      HighScoreTableModel model = new HighScoreTableModel(scores);
      model.setScores(scores);
      this.highScore.setRowSelectionAllowed(true);
      this.highScore.setColumnSelectionAllowed(false);
      this.highScore.setModel(model);
      this.highScore.setDefaultRenderer(Long.class, new DefaultTableCellRenderer() {
         protected void setValue(Object value) {
            Long l = (Long)value;
            String string = MessageFormat.format("{0, time, mm:ss}", l);
            super.setValue(string);
         }
      });
      this.highScore.getColumnModel().getColumn(0).setPreferredWidth(150);
      this.highScore.getSelectionModel().setSelectionInterval(2, 2);
   }

   public void addScore(Score score) {
      HighScoreTableModel model = (HighScoreTableModel)this.highScore.getModel();
      model.addNewestHighscore(score);
      this.highScore.setSelectionModel(model.getSelectionModel());
   }
}
