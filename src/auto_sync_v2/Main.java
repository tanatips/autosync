/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package auto_sync_v2;


/**
 *
 * @author PeeT
 */

import AdbManager.RunAdb;
import ConnectDatabase.ConnectSQLite;
import FFC_Form.ConfigServerForm;
import FFC_Form.FFCAgreement;
import FFC_Form.FFCRegisterUser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
     //public static MainForm mainform;
   //public static LogInForm loginform;
   //public static ConnectSQL connectionSQL;
   //public static ConnectSQLite connectionSQLite;
   //public static ConfigServerForm configServerForm;
   public static FFCRegisterUser ffcRegisterForm;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        setlookAndFeel();
        RunAdb runAdb = new RunAdb("./FFC/adb/adbStartServer.bat");
        Thread run = new Thread(runAdb);
        run.start();
        
        ffcRegisterForm = new FFCRegisterUser();
        //connectionSQLite = new ConnectSQLite();
        FFCAgreement fFCAgreement = new FFCAgreement();
        fFCAgreement.checkDateOut();
        
    }
    
    public static void closeMain()
    {
        Service.Service.mainform.setVisible(false);
    }
    
    public static void openMain()
    {
       Service.Service.mainform.setVisible(true);
    }

    public static void openRegisterUserform(){
        ffcRegisterForm.setVisible(true);
    }
    
    static void setlookAndFeel()
    {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            break;
        }
    }
    }


}
