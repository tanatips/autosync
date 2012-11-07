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
public class AdjustDatabase_old implements Runnable{

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
            FileBehavior fileBehavior = new FileBehavior();
            fileBehavior.setPathData("./FFC/check_database.ffc");
            ArrayList<String> data = fileBehavior.readDataMultipleline();
            for (int i = 1; i < data.size(); i++) {
                String[] dataSplit = data.get(i).split("=");
                switch (i) {
                    case 1:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffcPoi()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 2:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffcCpoitype()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 3:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffcHospital()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 4:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.modifyVillageTemple()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 5:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.modifyVillageschool()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 6:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffcandroidvisit()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 7:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffcvisitoldter()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                    case 8:
                        if (dataSplit[1].equals("false")) {
                            if (adjust.create_ffc_visitspecialperson()) {
                                data.set(i, dataSplit[0] + "=true");
                            } else {
                                JOptionPane.showMessageDialog(Service.Service.mainform, "ไม่สามารถปรับปรุงฐานข้อมูลได้\n" + "ERROR : " + adjust.getErrorMessage());
                                Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
                                Service.Service.mainform.setVisible(false);
                                fileBehavior.writeDataArrayList(data);
                                return;
                            }
                        }
                        break;
                }
            }
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                    //MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                    System.out.println("Error Thread");
                }
            }
            time.stop();
            data.set(0, "adjustDatabaseAll=true");
            fileBehavior.writeDataArrayList(data);
            Service.Service.mainform.setVisibleAdjustDatabaseDialog(false);
            //FileBehavior fileBehavior = new FileBehavior();
            //fileBehavior.setPathData("./FFC/check_database.ffc");
            //fileBehavior.writeData("adjustDatabase=true");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdjustDatabase_old.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabase_old.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(AdjustDatabase_old.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(AdjustDatabase_old.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    };

    public void getAdjustThread(Thread adjustThread){
        this.adjustThread = adjustThread;
    }

}
