/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Maintenance;

import ConnectDatabase.DriverDataBase;
import DeviceManager.BatFileManager;
import FFC_Form.MainForm;
import FFC_Form.MaintenenceForm;
import FileManager.FileManager;
import FileManager.FileSettingDataBaseFFCManager;
import Information.BackupInformation;
import Management.BackupInformationManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class BackUp implements Runnable{
    
    String backupPath;
    String databasePath;
    String databaseName;
    Thread backupThread;
   /* public void setBackupPath(String backupPath){
        this.backupPath = backupPath;
    }*/


    public BackUp(String databasePath, String backupPath, String databaseName){
        this.backupPath = backupPath;
        this.databasePath = databasePath;
        this.databaseName = databaseName;
    }

    @Override
    public void run(){
        Thread timeThread = new Thread(countTime);
        timeThread.start();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date currentDate = new Date();
        String backupName = this.databaseName + "_" + dateFormat.format(currentDate).toString();
        String backupFullPath = this.backupPath + "\\" +backupName + ".sql";
       
        
        
        System.out.println(dateFormat.format(currentDate).toString());
        //FileManager fileManager = new FileManager();
        //fileManager.copyFile(this.databasePath+"/"+this.databaseName, this.backupPath);

        /*boolean checkDataBase = this.checkDatabase(databaseName,databasePath);
        if(!checkDataBase){
            JOptionPane.showMessageDialog(MainForm.maintenForm, "ไม่มีฐานข้อมูลที่เลือก !!!");
            return;
        }*/

        //write command to backup.bat
        FileSettingDataBaseFFCManager settingDatabaseFile = new FileSettingDataBaseFFCManager();
        settingDatabaseFile.setPathFile("./FFC/config_mysql_local.ffc");
        DriverDataBase driverDatabase = settingDatabaseFile.readDriverDataBase();
        BackupManager backupFileManager = new BackupManager();
        backupFileManager.setPathData("./FFC/backup.bat");
        backupFileManager.writeBatFileBackup(this.databaseName,backupFullPath,driverDatabase.getUserName(),driverDatabase.getPassword());

        //Run backup.bat
        try {
            Process p = Runtime.getRuntime().exec("./FFC/backup.bat");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }   
        } catch (IOException ex) {
            Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.maintenForm, "การสำรองข้อมูลล้มเหลว !!!");
            return;
        }

         //write backup Information
        BackupInformationManager backupManager = new BackupInformationManager();
        backupManager.setFileURL("./FFC/Backup_Information.ffc");
        BackupInformation backupInformation = new BackupInformation(this.databaseName,backupName,dateFormat.format(currentDate).toString(),this.backupPath);
        backupManager.insertInformation(backupInformation);

        //refresh Backup List on Maintenance Form
        MainForm.maintenForm.showBackUpList();
        MainForm.maintenForm.setVisibleBackupDialog(false);
        JOptionPane.showMessageDialog(MainForm.maintenForm, "การสำรองฐานข้อมูลเสร็จสิ้น !!!");
        MainForm.maintenForm.setEnabled(true);
    }

    public boolean checkDatabase(String databaseName,String databasePath){
        File folder = new File(databasePath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            /*if (listOfFiles[i].isFile()) {
            System.out.println("File " + listOfFiles[i].getName());
            } else */if (listOfFiles[i].isDirectory() && (listOfFiles[i].getName() == null ? databaseName == null : listOfFiles[i].getName().equals(databaseName))) {
                System.out.println("Directory " + listOfFiles[i].getName());
                return true;
            }
       }
        return false;
    }

    public void getBackupthread(Thread thread){
        this.backupThread =  thread;
    }
    
    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(backupThread.isAlive()){
                try {
                    Thread.sleep(1000);  
                    MainForm.maintenForm.setTextTimeLabelBackup(String.valueOf(count));
                    count++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(BackUp.class.getName()).log(Level.SEVERE, null, ex);
                }
                  
              
            }
        }   
    };
}
