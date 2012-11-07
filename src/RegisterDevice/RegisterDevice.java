/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RegisterDevice;

import FFC_Form.ConnectDeviceForm;
import FFC_Form.MainForm;
import FileManager.FileManager;
import Information.DeviceInformation;
import Management.DeviceInformationManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author PeeT
 */
public class RegisterDevice implements Runnable {
    
    String driverNewDriver;
    String deviceName;
    String deviceModel;
    DeviceInformation deviceInformation;
    @Override
    public void run()
    {
        System.out.println(driverNewDriver);
        FileManager fileManager = new FileManager();
         DeviceInformationManager deviceInformationManager =
                new DeviceInformationManager();
         deviceInformationManager.setFileURL("C:/FFC/device_information.ffc");
         //.setWaitPanelVisible(true);
        fileManager.copyFile("C:/FFC/android/", driverNewDriver+":/");
        //ConnectDeviceForm.setWaitPanelVisible(false);
        //deviceInformationManager.insertInformation(deviceInformation);
       
     // feed in your array (or convert your data to an array)
    
        JOptionPane.showMessageDialog(MainForm.connForm, "ลงทะเบียน device สำเร็จ");
    }
    
    public void setDeviceInformation(String newDriver, String deviceName, String deviceModel , DeviceInformation deviceInformation)
    {
        this.deviceInformation = deviceInformation;
        this.driverNewDriver = newDriver;
        this.deviceName = deviceName;
        this.deviceModel = deviceModel;
    }
}
