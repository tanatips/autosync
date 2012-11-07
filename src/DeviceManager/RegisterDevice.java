/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DeviceManager;


import AdbManager.RunAdb;
import FFC_Form.MainForm;
import FileManager.FileManager;
import DeviceManager.DeviceInformation;
import ErrorManager.ErrorManagement;
import FFC_Information.FFCInformationManager;
import Management.DeviceInformationManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author PeeT
 */
public class RegisterDevice implements Runnable {
    
    String driverNewDriver;
    String deviceName;
    String deviceModel;
    DeviceInformation deviceInformation;
    Thread registerThread;
    String deviceSerial;
    FFCInformationManager ffcInformationManager;

    
    @Override
    public void run()
    {
        try {
            MainForm.connForm.setVisibleComponentOfRegisterDevice(true);
            Thread timerThread = new Thread(countTime);
            timerThread.start();
            System.out.println(driverNewDriver);
            //เขียนคำสั่งลงใน adb.bat
            /*AdbManager.AdbFileManager adbManager = new AdbManager.AdbFileManager();
            adbManager.setPathFileBat("./FFC/adb/adbPush.bat");
            adbManager.writeAdbBatFileCopyPush(this.deviceSerial,"../android/FFC", "/sdcard/FFC");
            MainForm.connForm.setProgerssLabel("คัดลอกฐานข้อมูล...");
            Thread thread = new Thread(runProgress);
            thread.start();
            RunAdb runAdb = new RunAdb("./FFC/adb/adbPush.bat");
            if(!runAdb.runAdb()){
            return;
            }
            thread.stop();*/
            ffcInformationManager = new FFCInformationManager();
            ffcInformationManager.openConnection(Service.Service.ffcInformationPath);
            MainForm.connForm.setProgerssLabel("บันทึกข้อมูลอุปกรณ์...");
            MainForm.connForm.setValuesRegisterProgressbar(100);
            /*DeviceInformationManager deviceInformationManager =
            new DeviceInformationManager();
            //Set Device Information File Path
            deviceInformationManager.setFileURL("./FFC/device_information.ffc");
            //write device information
            deviceInformationManager.insertInformation(deviceInformation);*/
            this.ffcInformationManager.InsertNewDevice(deviceSerial, deviceName, deviceModel);
            this.ffcInformationManager.closeConnection();
            timerThread.stop();
            MainForm.connForm.setProgerssLabel("ลงทะเบียนอุปกรณ์เสร็จสิ้น");
            MainForm.connForm.showMessagedialog("ลงทะเบียนอุปกรณ์สำเร็จ");
            MainForm.connForm.setVisibleComponentOfRegisterDevice(false);
            MainForm.connForm.setSelectConnectTabPane();
            //ConnectDeviceForm.connectTabPane.setSelectedIndex(1);
        } catch (SQLException ex) {
            ErrorManagement errormanagement = new ErrorManagement();
            errormanagement.errorReport(MainForm.connForm, ex.getMessage());
            Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ErrorManagement errormanagement = new ErrorManagement();
            errormanagement.errorReport(MainForm.connForm, ex.getMessage());
            Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setDeviceInformationMount(String newDriver, String deviceName, String deviceModel , DeviceInformation deviceInformation)
    {
        this.deviceInformation = deviceInformation;
        this.driverNewDriver = newDriver;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
    }

    public void setDeviceInformationAdb(String deviceSerial,String deviceName, String deviceModel , DeviceInformation deviceInformation)
    {
        this.deviceSerial = deviceSerial;
        this.deviceInformation = deviceInformation;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
    }
    
    Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){
            while(registerThread.isAlive()){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
                }
                    MainForm.connForm.settimeLabel(String.valueOf(count));
                    count++;               
            }
        }   
    };
    
    Runnable runProgress = new Runnable() {
        @Override
        public void run(){
            for(int i=0; i<100; i+=5)
            {
            try {
                     Thread.sleep(500);
                    MainForm.connForm.setValuesRegisterProgressbar(i);
                } catch (InterruptedException ex) {
                     System.out.println("Error Thread");
                }
            
            }
        }
    };
    
    public void getRegisterThread(Thread registerThread){
        this.registerThread = registerThread;
    }

   /* Runnable runAdb = new Runnable() {
        @Override
        public void run(){

            //long t0 = System.currentTimeMillis();
            try {
                System.out.println();
            Process p = Runtime.getRuntime().exec("./FFC/adb/adb.bat");
            InputStream in = p.getErrorStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
            String line = "";
                while ((line = buff.readLine())!=null){
                System.out.println(line);
                }
                System.out.println("Exit code = "+p.waitFor());
        }   catch (InterruptedException ex) {
                Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(RegisterDevice.class.getName()).log(Level.SEVERE, null, ex);
            //JOptionPane.showMessageDialog(MainForm.maintenForm, "การคัดลอกฐานข้อมูลล้มเหลว !!!");
            return;
        }
            //long t1 = System.currentTimeMillis();
            //System.out.println("Time = "+(t1-t0)+"Millisecond" );
        }
    };*/
}
