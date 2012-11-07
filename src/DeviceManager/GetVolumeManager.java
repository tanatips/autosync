/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DeviceManager;

import FFC_Form.MainForm;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class GetVolumeManager extends BatFileManager{

    public GetVolumeManager(String path) {
        setPathFileBat(path);
    }

    private void writeVolumeBatFile(String driveForGet){
        super.writeBatFile("@echo off"
                +super.NEW_LINE
                +"cd\\"
                +super.NEW_LINE
                +driveForGet
                +":"
                +super.NEW_LINE
                +"vol");
    }

    private String getVolumeSerialId() {
        String line = null;

        try {
            Process p = Runtime.getRuntime().exec(getPathFileBat());
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
             System.out.println(p);

            int count = 0;
            while ((line = in.readLine()) != null) {
                count++;
                if (count<2)
                    continue;
                if (count>2)
                    break;
                 System.out.println("line"+line);
                System.out.println("line.substring"+line.substring( line.lastIndexOf(" ")+1 ));
                return line.substring( line.lastIndexOf(" ")+1 );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "X";
    }

    private ArrayList getDeviceSerial() throws IOException {
        String line = null;
        int lineCount = 1;
        
        ArrayList<String> deviceList = new ArrayList<String>();
        Process p = Runtime.getRuntime().exec("./FFC/adb/adb.exe devices");
        BufferedReader in = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));
         System.out.println(p);
        while ((line = in.readLine()) != null) {
            if(line == null ? "unknown" != null : !line.equals("unknown"))
            {
                if(lineCount == 1){

                }else{
                    String[] SerialTemp = line.split("\t");
                    System.out.println(SerialTemp[0]);
                    deviceList.add(SerialTemp[0]);
                }
                lineCount++;
            }
        }
        return deviceList;
    }

    public String getVolumeDrive(String drive){
        this.writeVolumeBatFile(drive);
        return this.getVolumeSerialId();
    }

    public ArrayList getSerialDrive() throws IOException{
        return this.getDeviceSerial();
    }
//    public static void main(String[]args){
//        GetVolumeManager gs = new GetVolumeManager("C:/volume.bat");
//        System.out.println("Serial : "+gs.getVolumeDrive("D"));
//    }

}
