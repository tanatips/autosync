/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DeviceManager;

import FFC_Form.ConnectDeviceForm;
import FFC_Form.MainForm;
import FFC_Information.FFCInformationManager;
import Information.FFCInformation;
import Management.DeviceInformationManager;
import Service.Service;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class ConnectDeviceAdb implements Runnable{
    Thread connectDeviceThread;
    String serialDevice = "";

    FFCInformationManager ffcInformationManager;

    public void run(){
        try {
            boolean checkSerial = false;
            DeviceInformationManager deviceInformationManager = new DeviceInformationManager();
            deviceInformationManager.setFileURL("./FFC/device_information.ffc");
            this.ffcInformationManager = new FFCInformationManager();
            this.ffcInformationManager.openConnection(Service.ffcInformationPath);
            FFCInformation ffcinformation = this.ffcInformationManager.getFFCInformation(serialDevice);
            System.out.println("Show device Serial : "+this.serialDevice);
            if (this.ffcInformationManager.checkDevice(this.serialDevice) && !this.serialDevice.equals("")) {
                System.out.println("dserial : "+ffcinformation.getdserial());
                Service.serialDeviceConnect = this.serialDevice;
                checkSerial = true;
                /*MainForm.connForm.setConnection(
                deviceInformationManager.getInformation(tempVolume), true);*/
            } else {
                Service.driveDeviceConnect = null;
                Service.serialDeviceConnect = null;
                JOptionPane.showMessageDialog(MainForm.connForm, "กรุณาเชื่อมต่อ Device หรือ ลงทะเบียน Device ก่อน !!!");
                MainForm.connForm.setVisibleDeiviceListDialog(false);
                return;
            }
            System.out.println("success !!!");
            if (checkSerial == true) {
                MainForm.connForm.setConnection(ffcinformation, true);
            }
            this.ffcInformationManager.closeConnection();
            ConnectDeviceForm.connectButton.setText("Disconnect");
            //JOptionPane.showMessageDialog(MainForm.connForm, "เชื่อมต่อ Device สำเร็จ");
            MainForm.transForm.isActive();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDeviceAdb.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.transForm, "Error : " +ex);
        } catch (Exception ex) {
            Logger.getLogger(ConnectDeviceAdb.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(MainForm.transForm, "Error : " +ex);
        }
    }

            Runnable countTime = new Runnable() {
        int count = 1;
        @Override
        public void run(){

            while(connectDeviceThread.isAlive()){
                try {
                    Thread.sleep(1000);
                    MainForm.connForm.setDecryptTimeLabel(String.valueOf(count));
                    count++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(ConnectDeviceAdb.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public void getConnectDeviceThread(Thread connectThread){
        this.connectDeviceThread = connectThread;
    }

    public void setSerialConnect(String SerialDevice){
        this.serialDevice = SerialDevice;
    }
}
