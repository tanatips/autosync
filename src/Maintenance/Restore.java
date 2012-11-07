/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Maintenance;

import ConnectDatabase.DriverDataBase;
import FFC_Form.MainForm;
import FileManager.FileSettingDataBaseFFCManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class Restore implements Runnable{
    
    String databaseName;
    String dateBackup;
    String backupPath;
    Thread restoreThread;

    public Restore(String databaseName,String dateBackup,String backupPath){
        this.databaseName = databaseName;
        this.dateBackup = dateBackup;
        this.backupPath = backupPath;
    }

    public Restore(){

    }

    @Override
    public void run(){
        Thread timeThread = new Thread(countTime);
        timeThread.start();
        //check backup file
        if(!checkDatabase(this.databaseName+"_"+this.dateBackup+".sql",this.backupPath)){
            JOptionPane.showMessageDialog(MainForm.maintenForm, "ไม่พบ Backup File !!!");
            System.out.println("Not Find Backup !!!");
            return;
        }

        //Write Command To Restore.bat
        String backupPathFull = this.backupPath + "\\" + databaseName + "_" + dateBackup + ".sql";
        FileSettingDataBaseFFCManager settingDatabaseFile = new FileSettingDataBaseFFCManager();
        settingDatabaseFile.setPathFile("./FFC/config_mysql_local.ffc");
        DriverDataBase driverDatabase = settingDatabaseFile.readDriverDataBase();
        RestoreManager restoreManager = new RestoreManager();
        restoreManager.setPathData("./FFC/restore.bat");
        restoreManager.writeBatFileRestore(driverDatabase.getUserName(), driverDatabase.getPassword(), this.databaseName, backupPathFull);

        //Run backup.bat
        try {
            Process p = Runtime.getRuntime().exec("./FFC/restore.bat");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.maintenForm, "การ Restore ฐานข้อมูลล้มเหลว !!!");
            return;
        }

        MainForm.maintenForm.setVisibleRestoreDialog(false);
        JOptionPane.showMessageDialog(MainForm.maintenForm, "การ Restore ฐานข้อมูลเสร็จสิ้น !!!");
        MainForm.maintenForm.setEnabled(true);

    }

    private boolean checkDatabase(String databaseName,String databasePath){
        System.out.println(databaseName);
        File folder = new File(databasePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            /*if (listOfFiles[i].isFile()) {
            System.out.println("File " + listOfFiles[i].getName());
            } else */if (/*listOfFiles[i].isDirectory() && */(listOfFiles[i].getName() == null ? databaseName == null : listOfFiles[i].getName().equals(databaseName))) {
                System.out.println("Directory " + listOfFiles[i].getName());
                return true;
            }
       }
       return false;
    }

    public void getRestoreThread(Thread restoreThread){
        this.restoreThread = restoreThread;
    }

    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(restoreThread.isAlive()){
                try {
                    Thread.sleep(1000);
                    MainForm.maintenForm.setTextTimeLabelRestore(String.valueOf(count));
                    count++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }
    };
}
