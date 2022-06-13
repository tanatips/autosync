/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;

import AdbManager.AdbCommand;
import AdbManager.AdbFileManager;
import AdbManager.RunAdb;
import ConvertSQLiteToSQL.ConvertSQLiteToSQL;
import FFC_Form.MainForm;
import FFC_Information.FFCInformationManager;
import FileManager.FileBehavior;
import FileManager.FileManager;
import Management.PersonCheckManager;
import Management.VillageBookInformationManager;
import Management.VisitMaxInformationManager;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class AndroidToJhcis implements Runnable{
    ConvertSQLiteToSQL convert;

    // visitMaxInformationManager = new VisitMaxInformationManager();
    VillageBookInformationManager villageBookInformationManager = new VillageBookInformationManager();
    //PersonCheckManager checkperson = new PersonCheckManager();
    FileManager fileManager1 = new FileManager();

    FFCInformationManager ffcInformationManager = new FFCInformationManager();

    public RunAdb.OnAdbErrorListener adblistener = new RunAdb.OnAdbErrorListener() {

        @Override
        public void onFailedToCopy() {
            JOptionPane.showMessageDialog(MainForm.transForm, "กรุณา Turn off USB Storage บนเครื่อง Android\n"
                    + "เนื่องจากระบบไม่สามารถเขียนไฟล์ฐานข้อมูลได้ !!!");
        }

        @Override
        public void onRemoteObjectNotExist() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

    @Override
    public void run()
    {
//            try {
//
//            Service.Service.connectionSQL.closeConnection();
//            Service.Service.connectionSQL.createConnection();
//            Service.Service.connectionSQL.connection.setAutoCommit(false);
            try
            {
            convert = new ConvertSQLiteToSQL();
            ffcInformationManager.openConnection(Service.Service.ffcInformationPath);
            Service.Service.updateCount.clearValue();
            Service.Service.SQLiteConnection.connectSQLite(Service.Service.mJHCISPath);
            
            convert.setConnection(Service.Service.SQLiteConnection);
            
           Thread timerThread = new Thread(countTime);
           timerThread.start();
           
            MainForm.transForm.setTextLogLabel("อัพเดทข้อมูล");

            convert.updatePerson();

            MainForm.transForm.setValueTransferProgressBar(10);

            convert.updateGisGroup();

            MainForm.transForm.setValueTransferProgressBar(20);
//
            convert.updatePersonbehavior();
            convert.updatePersondeath();

            MainForm.transForm.setValueTransferProgressBar(30);

            convert.insertVisit("visit");

            MainForm.transForm.setValueTransferProgressBar(50);

            convert.updateWomen();

            MainForm.transForm.setValueTransferProgressBar(55);

            convert.updatePersonunableGroup();

            MainForm.transForm.setValueTransferProgressBar(60);

            convert.updatePersongrow();

            MainForm.transForm.setValueTransferProgressBar(65);
            
            convert.printUpdateCount();
            convert.insertVisitCount();
//            
            }catch(Exception ex){
                this.catchEvent(ex);
            }

            //คัดลอกรูปภาพ
            ArrayList<String> listFile = new ArrayList<String>();
            FileManager fileManager = new FileManager();
            listFile = new AdbCommand().getFileListAndroid("/sdcard/Android/data/th.in.ffc/pictures/person");
            if (!fileManager.isDirectory("./FFC/Photoes_tmp")) {
                fileManager.createDirectory("./FFC/Photoes_tmp");
                System.out.println("Folder ./FFC/Photoes_tmp not fount Create it");
            }
            AdbFileManager adbManager = new AdbFileManager();
            adbManager = new AdbFileManager();
            adbManager.setPathFileBat("./FFC/adb/adbPull.bat");
            adbManager.writeAdbBatFileCopyPull(Service.Service.serialDeviceConnect, "/sdcard/Android/data/th.in.ffc/pictures/person", "../Photoes_tmp");
            RunAdb run2 = new RunAdb("./FFC/adb/adbPull.bat");
            if (!run2.runAdb()) {
                return;
            }
            if (!fileManager.isDirectory(Service.Service.defaultJhcisPath + "/Photoes/")) {
                fileManager.createDirectory(Service.Service.defaultJhcisPath + "/Photoes/");
                System.out.println("Folder : " + Service.Service.defaultJhcisPath + "/Photoes/ not fount Create it");
            }
            fileManager.copyFile("./FFC/Photoes_tmp/", Service.Service.defaultJhcisPath + "/Photoes");
            fileManager.deleteDirectory("./FFC/Photoes_tmp");
            MainForm.transForm.setTextLogLabel("อัพเดท Last Update");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",java.util.Locale.US);
            Date currentDate = new Date();
            try{
            this.ffcInformationManager.updateLastupdate(Service.Service.serialDeviceConnect,dateFormat.format(currentDate).toString());
            //this.villageBookInformationManager.updateDateUpDateInformation(Service.Service.serialDeviceConnect);
            MainForm.transForm.setValueTransferProgressBar(80);
            MainForm.transForm.setTextLogLabel("อัพเดท Max Visit");
            this.ffcInformationManager.updateMaxvisit(Service.Service.serialDeviceConnect, String.valueOf(Service.Service.SQLiteConnection.getVisitMaxAndroid()));
            //this.visitMaxInformationManager.upDateInformation(Service.Service.serialDeviceConnect, String.valueOf(Service.Service.SQLiteConnection.getVisitMaxAndroid()));
            this.ffcInformationManager.updateMaxpid(Service.Service.serialDeviceConnect, String.valueOf(Service.Service.SQLiteConnection.getPidMaxAndroid()));
            //this.checkperson.upDateInformation(Service.Service.serialDeviceConnect, String.valueOf(Service.Service.SQLiteConnection.getPidMaxAndroid()));
            this.ffcInformationManager.updateMaxhcode(Service.Service.serialDeviceConnect, String.valueOf(Service.Service.SQLiteConnection.getHcodeMaxAndroid()));
            this.ffcInformationManager.closeConnection();
            }catch(Exception Ex){
                this.catchEvent(Ex);
            }
            MainForm.transForm.setValueTransferProgressBar(100);
            MainForm.transForm.setTextLogLabel("เสร็จสิ้น");
            try {
                MainForm.transForm.setvillageAndroidLabel();
            } catch (SQLException ex) {
                this.catchEvent(ex);
                Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                this.catchEvent(ex);
                Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainForm.transForm.setTextPersonUpdateLabel(convert.getPersonUpdateCount());
            //MainForm.transForm.setTextPersonBehaviorUpdateLabel(convert.getPersonBehaviorUpdateCount());
            MainForm.transForm.setTextVisitUpdateLabel(convert.getVisitUpdateCount());
            MainForm.transForm.setTextVisitDiagUpdateLabel(convert.getVisitdiagUpdateCount());
            MainForm.transForm.setTextVisitdrugUpdateLabel(convert.getVisitDrugUpdateCount());
            //MainForm.transForm.setTextHouseUpdateLabel(convert.gethouseUpdateCount());
            MainForm.transForm.setTextPicNumLabel(String.valueOf(fileManager.getFileCount()));
            fileManager.resetFileCount();
            convert.closeConnection();
//            Service.Service.SQLiteConnection.closeConnection();
        try {
            //fileManager.deleteFile("C:/Users/PeeT/Documents/NetBeansProjects/AutoSyncV2_2/Auto_Sync_V2/FFC/Db_tmp/mJHCIS.sdb");
            if (Service.Service.SQLiteConnection.closeConnection()) {
                System.out.println("Conection CLoseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            } //FileManager fileManage = new FileManager();
        } catch (SQLException ex) {
            this.catchEvent(ex);
            Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
        }
            File file = new File("./FFC/Db_tmp/mJHCIS.sdb");
            if (file.delete()) {
                System.out.println("yessssssssssssssssssssss");
            } else {
                System.out.println("nooooooooooooooooooooooo");
            }
            // Security.SecurityManager manager = new Security.SecurityManager("./FFC/Db_tmp/");
            // manager.encryptDatabaseFile();
            File file1 = new File("./FFC/Db_tmp/mJHCIS.db");
            file.deleteOnExit();
            if (file1.delete()) {
                System.out.println("yessssssssssssssssssssss");
            } else {
                System.out.println("nooooooooooooooooooooooo");
            }
            MainForm.transForm.resetValueLabelUpdate();
            MainForm.transForm.setUpdateLabel();
            MainForm.transForm.setVisibleUpTOJhcisReportDialog(true);
            //MainForm.transForm.setVisibleUpdateToJhcisDialog(true);
//        } catch (Exception ex) {
//            
//                Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
//                File logfile = new File("./FFC/log.txt");
//                FileBehavior fileBehavior = new FileBehavior();
//                fileBehavior.setPathData(logfile.getPath());
//                fileBehavior.writeData(ex.toString() + "\n" + ex.getStackTrace().toString());
//                JOptionPane.showMessageDialog(MainForm.transForm, "การอัพเดทข้อมูลล้มเหลว : " + ex.getMessage(), "ERROR !!!", JOptionPane.ERROR_MESSAGE);
//                MainForm.transForm.setEnableUpToJhcisButton(true);
//                MainForm.transForm.setVisibleProgressComponent(false);
//                return;
//        
//            }
        
        
    }
    
    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(MainForm.transForm.getUpToJhcisThread().isAlive()){
                try {
                    Thread.sleep(1000);
                    MainForm.transForm.setTexttimerLabel(String.valueOf(count));
                    count++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(JhcisToAndroid.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }   
    };

    public void catchEvent(Exception ex){
        Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
        Logger.getLogger(AndroidToJhcis.class.getName()).log(Level.SEVERE, null, ex);
        File logfile = new File("./FFC/log.txt");
        FileBehavior fileBehavior = new FileBehavior();
        fileBehavior.setPathData(logfile.getPath());
        fileBehavior.writeData(ex.toString() + "\n" + ex.getStackTrace().toString());
        JOptionPane.showMessageDialog(MainForm.transForm, "การอัพเดทข้อมูลล้มเหลว : " + ex.getMessage(), "ERROR !!!", JOptionPane.ERROR_MESSAGE);
    }

}
