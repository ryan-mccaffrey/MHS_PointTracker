package mathhonorsociety;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * The main class that is run when the program is first started
 * @author Ryan McCaffrey
 */
public class MainClass {
    
    private static final StartWindow welcome = new StartWindow();
    private static final MenuWindow menuWindow1 = new MenuWindow();
    
    
    /*
    * Main method. When run, method opens a new welcome window in which user enters password.
    * Initializes a menu window to be used once password is entered correctly.
    * @param args Unused.
    * @exception IOException On file error.
    */
    public static void main(String[] args) throws IOException {
        FileHandler.ReadFile();
        menuWindow1.UpdateStudentJList();
        menuWindow1.updateSettingsTab();
        
        menuWindow1.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        menuWindow1.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(menuWindow1, "Are you sure you want to close this application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.OK_OPTION) {
                    try {
                        FileHandler.ToFile(MainOperations.getMemberList());
                    } catch (IOException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                }
            }
        });
        menuWindow1.setLocation(500, 200);
        
        welcome.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        welcome.addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(welcome, "Are you sure you want to close this application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        welcome.setLocation(500, 200);
        welcome.setVisible(true);
        //SwingEmbedding embedding = new SwingEmbedding();
        //embedding.setVisible(true);
    }
    
    /*
    * Returns the welcome window
    * @return StartWindow Returns welcome.
    */
    public static StartWindow getStartWindow()
    {
        return welcome;
    }
    
    /*
    * Returns the menu window
    * @return StartWindow Returns menuWindow1.
    */
    public static MenuWindow getMenuWindow1()
    {
        return menuWindow1;
    }
}
