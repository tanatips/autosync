/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;
import AdbManager.RunAdb;
import ConnectDatabase.ConnectSQLite;
import ConnectDatabase.DriverDataBase;
import ConnectDatabase.SqlQuery;
import FFC_Form.MainForm;
import FFC_Information.FFCInformationManager;
import FileManager.FileSettingDataBaseFFCManager;
import Information.CheckPersonInformation;
import Information.FFCInformation;
import Information.VillageBookInformation;
import Information.VisitMaxInformation;
import Management.VillageBookInformationManager;
import Management.VisitMaxInformationManager;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;



/**
 *
 * @author PeeT
 */
public class JhcisToAndroid implements Runnable{
    
    String village2Android;
    String houseToAndroid;
    DefaultListModel house2Android;
    Timer timer;
    private final int numOfTable = 80;
    private final int convertDbTotalPercent = 80;
    private final int processPercentOfEachTable = this.convertDbTotalPercent/this.numOfTable;
    public static int progressCount = 0;
    ConvertDatabase convertDatabase = new ConvertDatabase();
    ConvertSelectData convert = new ConvertSelectData();
    String villageLabelText = "";

    ArrayList<String> villageList = new ArrayList<>();
    ArrayList<ArrayList<String>> houseList = new ArrayList<>();

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
   
    
    public void innitInformation(DefaultListModel house2Android, String village2Android){
        this.house2Android = house2Android;
        this.village2Android = village2Android;
    }

    public void innitInformation2(ArrayList<String> village, ArrayList<ArrayList<String>> house){
        this.villageList = village;
        this.houseList = house;
    }
    
    @Override
    public void run() 
    {
        try {
            MainForm.transForm.setEnableComponent(false);
            progressCount = 0;
            Thread timerThread = new Thread(countTime);
            timerThread.start();
            System.out.println();
            //MainForm.transForm.setVisibleCancelButton(true);
            //ลบไฟล์เก่า
            File mjhcis = new File(Service.Service.mJHCISPath);
            if (mjhcis.exists()) {
                if (mjhcis.delete()) {
                    System.out.println("Deleted File");
                } else {
                    System.out.println("Not Deleted");
                }
            }
            MainForm.transForm.setTextLogLabel("แปลงฐานข้อมูล");
            ArrayList<String> dataConvert = new ArrayList<>();
            FileSettingDataBaseFFCManager cManager = new FileSettingDataBaseFFCManager();
            cManager.setPathFile("./FFC/config_mysql_local.ffc");
            DriverDataBase driverDataBase = cManager.readDriverDataBase();
            convertDatabase.initConverter(".", "/FFC/Db_tmp/", Service.Service.driveDeviceConnect, ":/FFC/DB/mJHCIS.db/", driverDataBase);
            //this.houseToAndroid = this.setHouseList();
            //this.convertDatabase.setHouseAndVillageList(this.village2Android.substring(0, 8), this.houseToAndroid);
            this.convertDatabase.setHouseAndVillageList(this.villageList, this.houseList);
            int maxVisitUpdate = 0;
            int maxPid = 0;
            int maxhcode = 0;

            if(this.villageList.isEmpty()){
                this.convertDatabase.convertMySQLtoSQLiteAll();
            }else{
                this.convertDatabase.convertMySQLtoSQLite();
            }
            //ConnectSQLite connectionSQLite = new ConnectSQLite();
            Service.Service.SQLiteConnection.connectSQLite(Service.Service.mJHCISPath);
            maxVisitUpdate = Service.Service.SQLiteConnection.getVisitMaxAndroid();
            maxPid = Service.Service.SQLiteConnection.getPidMaxAndroid();
            maxhcode = Service.Service.SQLiteConnection.getHcodeMaxAndroid();
            Service.Service.SQLiteConnection.closeConnection();
            String query = "SELECT user.pcucode,user.username,user.password,user.officertype FROM user";
            this.convertDatabase.ConvertMysqlToSQLite(query, "uJHCIS.db");
            this.convertDatabase.hashUserPassword("uJHCIS.db");

            SecurityNew.SecurityManager securityManager = new SecurityNew.SecurityManager();
            securityManager.encryptDatabaseFile();
            MainForm.transForm.setTextLogLabel("คัดลอกฐานข้อมูลลงอุปกรณ์");
            Thread thread = new Thread(runProgress);
            thread.start();
            //เขียนคำสั่งลงใน adb.bat
            AdbManager.AdbFileManager adbManager = new AdbManager.AdbFileManager();
            adbManager.setPathFileBat("./FFC/adb/adbPush.bat");
            adbManager.writeAdbBatFileCopyPush(Service.Service.serialDeviceConnect, "../Db_tmp/mJHCIS.sdb", "/sdcard/Android/data/th.in.ffc/databases/mJHCIS.sdb");
            RunAdb runAdb = new RunAdb("./FFC/adb/adbPush.bat");
            runAdb.setOnAdbErrorListener(this.adblistener);
            if (!runAdb.runAdb()) {
                MainForm.transForm.setVisibleProgressComponent(false);
                MainForm.transForm.setEnableUpToAndroidButton(true);
                progressCount = 0;
                return;
            }
            adbManager.writeAdbBatFileCopyPush(Service.Service.serialDeviceConnect, "../Db_tmp/uJHCIS.db", "/sdcard/Android/data/th.in.ffc/databases/uJHCIS.db");
            RunAdb pushUJHCIS = new RunAdb("./FFC/adb/adbPush.bat");
            pushUJHCIS.setOnAdbErrorListener(this.adblistener);
            if (!pushUJHCIS.runAdb()) {
                MainForm.transForm.setVisibleProgressComponent(false);
                MainForm.transForm.setEnableUpToAndroidButton(true);
                progressCount = 0;
                return;
            }
            thread.stop();
            MainForm.transForm.setValueTransferProgressBar(90);
            MainForm.transForm.setVisibleCancelButton(false);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US);
            Date currentDate = new Date();
            MainForm.transForm.setTextLogLabel("บันทึกค่า max visit");

            ffcInformationManager.openConnection(Service.Service.ffcInformationPath);
            FFCInformation ffcinformation = this.ffcInformationManager.getFFCInformation(Service.Service.serialDeviceConnect);
            if(ffcinformation.getdserial().equals(Service.Service.serialDeviceConnect)){
                this.ffcInformationManager.updateMaxvisit(Service.Service.serialDeviceConnect, String.valueOf(maxVisitUpdate));
                this.ffcInformationManager.updateLastupdate(Service.Service.serialDeviceConnect, dateFormat.format(currentDate).toString());
                this.ffcInformationManager.updateMaxpid(Service.Service.serialDeviceConnect,String.valueOf(maxPid));
                this.ffcInformationManager.updateMaxhcode(Service.Service.serialDeviceConnect,String.valueOf(maxhcode));
            }
            /*VisitMaxInformationManager visitMaxInformationManager = new VisitMaxInformationManager();
            visitMaxInformationManager.setFileURL("./FFC/maxvisit_information.ffc");
            ArrayList<VisitMaxInformation> list = visitMaxInformationManager.getAllInformation();
            boolean isRealy = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getSerialDevice().equals(Service.Service.serialDeviceConnect)) {
                    isRealy = true;
                }
            }
            if (!isRealy) {
                visitMaxInformationManager.insertInformation(new VisitMaxInformation(Service.Service.serialDeviceConnect, "" + maxVisitUpdate));
            } else {
                visitMaxInformationManager.upDateInformation(Service.Service.serialDeviceConnect, "" + maxVisitUpdate);
            }
            for (int i = 96; i <= 97; i++) {
                try {
                    Thread.sleep(300);
                    MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                    System.out.println("Error Thread");
                }
            }
            MainForm.transForm.setTextLogLabel("ลบข้อมูลหมู่บ้านเก่า");
            VillageBookInformationManager villageBookInformationManager = new VillageBookInformationManager();
            villageBookInformationManager.setFileURL("./FFC/village_books_information.ffc");
            ArrayList<VillageBookInformation> temp = villageBookInformationManager.getAllInformation();
            for (int i = 0; i < temp.size(); i++) {
                villageBookInformationManager.deleteInformation(Service.Service.serialDeviceConnect);
            }
            for (int i = 98; i <= 99; i++) {
                try {
                    Thread.sleep(300);
                    MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                    System.out.println("Error Thread");
                }
            }
            MainForm.transForm.setTextLogLabel("บันทึกข้อมูลหมู่บ้านใหม่");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US);
            Date currentDate = new Date();
            for (int i = 0; i < this.villageList.size(); i++) {
                villageBookInformationManager.insertInformation(new VillageBookInformation(Service.Service.serialDeviceConnect, this.villageList.get(i), dateFormat.format(currentDate).toString()));
                villageLabelText += this.villageList.get(i) + ", ";
            }*/

            //create stat xml
            XML.XMLManager xmlManager = new XML.XMLManager();
            xmlManager.ffcStatWrite();
            adbManager.setPathFileBat("./FFC/adb/adbPush.bat");
            adbManager.writeAdbBatFileCopyPush(Service.Service.serialDeviceConnect, "../FFC-Xml/FFC-Stat-population.xml", "/sdcard/Android/data/th.in.ffc/files/FFC-Stat-population.xml");
            RunAdb runAdb1 = new RunAdb("./FFC/adb/adbPush.bat");
            runAdb1.setOnAdbErrorListener(this.adblistener);
            if (!runAdb1.runAdb()) {
                MainForm.transForm.setVisibleProgressComponent(false);
                //MainForm.transForm.setEnableUpToAndroidButton(true);
                //return;
            }
            /*

            //เก็บค่า max pid
            Management.PersonCheckManager personcheckmanager = new Management.PersonCheckManager();
            personcheckmanager.setURL("./FFC/check_person.ffc");
            CheckPersonInformation checkpersoninformation = new CheckPersonInformation(Service.Service.serialDeviceConnect,String.valueOf(maxPid));
            //personcheckmanager.insertInformation(checkpersoninformation);
            //personcheckmanager.upDateInformation(Service.Service.serialDeviceConnect,String.valueOf(maxPid));
            ArrayList<CheckPersonInformation> allinformationcheckperson = personcheckmanager.getAllInformation();
            boolean checkserialAlReady = false;
            for (int i = 0; i < allinformationcheckperson.size(); i++) {
                System.out.println("Check Deviceserial : "+ allinformationcheckperson.get(i).getSerialDevice() +" == "+Service.Service.serialDeviceConnect);
                if (allinformationcheckperson.get(i).getSerialDevice().equals(Service.Service.serialDeviceConnect)) {
                    System.out.println("Device already");
                    checkserialAlReady = true;
                }
            }
            if (!checkserialAlReady) {
                personcheckmanager.insertInformation(checkpersoninformation);
            } else {
                personcheckmanager.upDateInformation(Service.Service.serialDeviceConnect,String.valueOf(maxPid));
            }*/


            for (int i = 100; i <= 101; i++) {
                try {
                    Thread.sleep(300);
                    MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                    System.out.println("Error Thread");
                }
            }
            this.ffcInformationManager.closeConnection();
            MainForm.transForm.setValueTransferProgressBar(100);
            MainForm.transForm.setTextHouseLabel(String.valueOf(convertDatabase.houseCount));
            MainForm.transForm.setTextPersonLabel(String.valueOf(convertDatabase.personCount));
            MainForm.transForm.setTextVisitLabel(String.valueOf(convertDatabase.visitCount));
            //MainForm.transForm.setTextvillagelabel(this.villageLabelText);
            if(this.villageList.isEmpty()){
                MainForm.transForm.setAllvillageLabel();
            }else{
                MainForm.transForm.setvillageLabel();
            }
            MainForm.transForm.setTextLogLabel("เสร็จสิ้น");
            MainForm.transForm.setVisibleReportDialog(true);
            Service.Service.checkworking = false;
            progressCount = 0;
            System.gc();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JhcisToAndroid.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.transForm, "Error : " +ex);
            return;
        } catch (SQLException ex) {
            Logger.getLogger(JhcisToAndroid.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.transForm, "Error : " +ex);
            return;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JhcisToAndroid.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.transForm, "Error : " +ex);
            return;
        }
    }
    
    Runnable runProgress = new Runnable() {
        @Override
        public void run(){
            for(int i=90; i<95; i++)
            {
            try {
                     Thread.sleep(3000);
                    MainForm.transForm.setValueTransferProgressBar(i);
                } catch (InterruptedException ex) {
                     System.out.println("Error Thread");
                }
            
            }
        }
    };
    
    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(MainForm.transForm.getUpToAndroidThread().isAlive()){
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
   
    private String setHouseList(){
        String houselist = "";
        for(int i=0; i<this.house2Android.size(); i++)
        {  
            if(i != this.house2Android.size()-1){
                if(this.house2Android.getElementAt(i)!=null)
                houselist += this.house2Android.getElementAt(i).toString() + "','"; 
            }  
            else{
                if(this.house2Android.getElementAt(i)!=null)
                houselist += this.house2Android.getElementAt(i).toString();
            }    
        }
        System.out.println(houselist);
        return houselist;
    }

    public void calculateProgressCount(int roundCount){
        if(roundCount != 1){
            roundCount--;
           
            calculateProgressCount(roundCount);
            progressCount += this.processPercentOfEachTable;
        }else{
            progressCount += this.processPercentOfEachTable;
        }

    }
}

