/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AdjustDatabase;

import Transfer.*;
import AdbManager.AdbFileManager;
import AdbManager.RunAdb;
import FFC_Form.MainForm;
import FileManager.FileBehavior;
import FileManager.FileManager;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class AdjustDatabase implements Runnable{

    Thread adjustThread;

    public RunAdb.OnAdbErrorListener adblistener = new RunAdb.OnAdbErrorListener() {

        @Override
        public void onFailedToCopy() {
            

        }

        @Override
        public void onRemoteObjectNotExist() {
     
        }
    };

    public void run(){
        try {
            Thread time = new Thread(countTime);
            time.start();
            Service.Service.mainform.setVisibleAdjustDatabaseDialog(true);
            AdjustDatabaseSql adjust = new AdjustDatabaseSql();
            adjust.setConnection(Service.Service.connectionSQL.connection);
            AdjustDatabaseManager adjustmanager = new AdjustDatabaseManager();
            //if (adjustmanager.getvalue("add_ffc_poi") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_poi")) {
                    System.out.println("ffc_poi is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_poi", "1");
                } else {
                    if (adjust.create_ffcPoi()) {
                        //adjustmanager.updateResultAdjust("add_ffc_poi", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
           // }
           // if (adjustmanager.getvalue("add_ffc_poitype") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_cpoitype")) {
                    System.out.println("ffc_cpoitype is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_poitype", "1");
                } else {
                    if (adjust.create_ffcCpoitype()) {
                        //adjustmanager.updateResultAdjust("add_ffc_poitype", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
          //  }
          //  if (adjustmanager.getvalue("add_ffc_hospital") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_hospital")) {
                    System.out.println("ffc_hospital is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_hospital", "1");
                } else {
                    if (adjust.create_ffcHospital()) {
                        //adjustmanager.updateResultAdjust("add_ffc_hospital", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
          //  }
         //   if (adjustmanager.getvalue("modify_villagetemple") == 0) {
                if(Service.Service.connectionSQL.checkExistColumn("villagetemple", "dateupdate")){
                    System.out.println("dateupdate villagetemple column's is Existed");
                    //adjustmanager.updateResultAdjust("modify_villagetemple", "1");
                }else{
                if (adjust.modifyVillageTemple()) {
                    //adjustmanager.updateResultAdjust("modify_villagetemple", "1");
                } else {
                    JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                    Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                    Service.Service.mainform.setVisible(false);
                    return;
                }
                }
          //  }
        //    if (adjustmanager.getvalue("modify_villageschool") == 0) {
                if(Service.Service.connectionSQL.checkColumnLen("villageschool", "maxclass", 4)){
                    System.out.println("modified villageschool");
                    //adjustmanager.updateResultAdjust("modify_villageschool", "1");
                }else{
                if (adjust.modifyVillageschool()) {
                    //adjustmanager.updateResultAdjust("modify_villageschool", "1");
                } else {
                    JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                    Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                    Service.Service.mainform.setVisible(false);
                    return;
                }
                }
        //    }
         //   if (adjustmanager.getvalue("add_ffc_androidvisit") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_androidvisit")) {
                    System.out.println("ffc_androidvisit is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_androidvisit", "1");
                } else {
                    if (adjust.create_ffcandroidvisit()) {
                        //adjustmanager.updateResultAdjust("add_ffc_androidvisit", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
        //    }
        //    if (adjustmanager.getvalue("add_ffc_visitoldter") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_visitoldter")) {
                    System.out.println("ffc_visitoldter is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_visitoldter", "1");
                } else {
                    if (adjust.create_ffcvisitoldter()) {
                       // adjustmanager.updateResultAdjust("add_ffc_visitoldter", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
        //    }
        //    if (adjustmanager.getvalue("add_ffc_visitspecialperson") == 0) {
                if (Service.Service.connectionSQL.checkExistTable("ffc_visitspecialperson")) {
                    System.out.println("ffc_visitspecialperson is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_visitspecialperson", "1");
                } else {
                    if (adjust.create_ffc_visitspecialperson()) {
                        //adjustmanager.updateResultAdjust("add_ffc_visitspecialperson", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
                
                if (Service.Service.connectionSQL.checkExistTable("ffc_506radius")) {
                    System.out.println("ffc_506radius is Existed");
                    //adjustmanager.updateResultAdjust("add_ffc_visitspecialperson", "1");
                } else {
                    if (adjust.create_ffc_506radius()) {
                        //adjustmanager.updateResultAdjust("add_ffc_visitspecialperson", "1");
                    } else {
                        JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                        Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                        Service.Service.mainform.setVisible(false);
                        return;
                    }
                }
                
        //    }
           adjustmanager.updateResultAdjust("db_version", "1");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                    //MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                    System.out.println("Error Thread");
                }
            }
            time.stop();
            Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
            //FileBehavior fileBehavior = new FileBehavior();
            //fileBehavior.setPathData("./FFC/check_database.ffc");
            //fileBehavior.writeData("adjustDatabase=true");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdjustDatabase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(adjustThread.isAlive()){
                try {
                    Thread.sleep(1000);
                    Service.Service.mainform.setAdjustTimeLabel(String.valueOf(count));
                    count++;
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(AdjustDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    };

    Runnable delayTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(count != 5){
                try {
                    Thread.sleep(1000);
                    Service.Service.mainform.setAdjustTimeLabel(String.valueOf(count));
                    count++;

                } catch (InterruptedException ex) {
                    Logger.getLogger(AdjustDatabase.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    };

    public void getAdjustThread(Thread adjustThread){
        this.adjustThread = adjustThread;
    }
    
    private void adjust() throws ClassNotFoundException, SQLException{
        
    }



}
