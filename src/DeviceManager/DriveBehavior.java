/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DeviceManager;

import FFC_Form.MainForm;
import Service.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public class DriveBehavior {

    private String pathForFineDrive;

    public DriveBehavior() {
        this.pathForFineDrive = null;
    }

    public DriveBehavior(String pathForFineDrive) {
        this.pathForFineDrive = pathForFineDrive;
    }

    public void setPathForFineDrive(String pathForFineDrive) {
        this.pathForFineDrive = pathForFineDrive;
    }

    public String fineDriveDeviceAndroid(){
        int numDevice = 0; 
        String temp =null;
        System.out.println( Arrays.asList( File.listRoots() ) ) ;
        for(int i =0;i<14;i++){
            try {
                FileInputStream pop = new FileInputStream(drive(i)+this.pathForFineDrive);
                System.out.println("Fount Drive : "+drive(i)+this.pathForFineDrive);
                numDevice++;
                Service.numDeviceConnect = numDevice;
                temp =drive(i);
                try {
                    pop.close();
                } catch (IOException ex) {
                    Logger.getLogger(DriveBehavior.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("can not close file");
                }
                
            } catch (FileNotFoundException ex) {
                System.out.println("Not Found drive : "+drive(i)+this.pathForFineDrive);
            }
        }
        System.out.println("Device Connected : " + numDevice);
        System.out.println(" getDrive() :"+temp);
        return temp;
    }

   public DefaultListModel getSerialList() throws IOException{
       DefaultListModel temp = new DefaultListModel();
       int lineCount = 1;
            Process p = Runtime.getRuntime().exec("./FFC/adb/adb.exe devices");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null){
                System.out.println(line);
                    if(lineCount == 1){
                        //skip first line
                    }else{
                        if(line.length()!=0){
                        String[] SerialTemp = line.split("\t");
                        System.out.println(SerialTemp[0]);
                        temp.addElement(SerialTemp[0]);
                        System.out.println(lineCount);
                        }
                    }
                    lineCount++;
                //temp.addElement(line);
            }
       return temp;
   }
    
    public DefaultListModel getDriveList() {
        File[] roots = File.listRoots();
        DefaultListModel temp = new DefaultListModel();
            for(int i=0;i<roots.length;i++){
                    if(!roots[i].getPath().equals("C:"+File.separator) &&
                                !roots[i].getPath().equals("D:"+File.separator)){
                        String tempw =this.getDrivePopoty(roots[i].getPath());

                        System.out.println(tempw);
                        if(tempw != null){
                        temp.addElement(tempw);
                        }
                    }
                }
        return temp;
    }
    
    public DefaultTableModel getDriveListTable(){
        int numberOfColumn = 3;
        String[] ColumnName = {"Drive","Free Space","Total Space"};
        File[] roots = File.listRoots();
        int rowcount = 0;
        String[][] data = new String[roots.length][numberOfColumn];
        
        for(int i=0;i<roots.length;i++){
            if(!roots[i].getPath().equals("C:"+File.separator) &&
                        !roots[i].getPath().equals("D:"+File.separator)){
                String tempw =this.getDrivePopotyTable(roots[i].getPath());
                System.out.println(tempw);
                if(tempw != null){
                    String[] temp = tempw.split("[,]", 3);
                    for(int j=0; j<numberOfColumn; j++){
                      data[rowcount][j] = temp[j];  
                    }
                    rowcount++;
                }
            } 
        }
        DefaultTableModel tableModel = new DefaultTableModel(data,ColumnName);
        return tableModel;
    }
    
    public String getDrivePopotyTable(String drive){

        File file = new File(drive);
        long totalSpace = file.getTotalSpace() / (1024 * 1024);
        long freeSpace = file.getFreeSpace() / (1024 * 1024);
        if(totalSpace > 0){
        return drive+","+freeSpace+" MB,"+totalSpace+" MB";
        }else{
            return null;
        }
    }

    public String getDrivePopoty(String drive){

        File file = new File(drive);
        long totalSpace = file.getTotalSpace() / (1024 * 1024);
        long freeSpace = file.getFreeSpace() / (1024 * 1024);
        if(totalSpace > 0){
        return drive+" Free space :>"+freeSpace+"<MB Total space :"+totalSpace+"MB";
        }else{
            return null;
        }
    }
    private String drive(int i){
        switch (i){
            case 0:
                return "D";
            case 1:
                return "E";
            case 2:
                return "F";
            case 3:
                return "G";
            case 4:
                return "H";
            case 5:
                return "I";
            case 6:
                return "J";
            case 7:
                return "K";
            case 8:
                return "L";
            case 9:
                return "M";
            case 10:
                return "N";
            case 11:
                return "O";
            case 12:
                return "P";
            case 13:
                return "Q";
            case 14:
                return "R";
            case 15:
                return "S";
            case 16:
                return "T";
            case 17:
                return "U";
            case 18:
                return "V";
            case 19:
                return "W";
        }
        return null;
    }

    public boolean checkDeviceExist(String deviceSerial) throws IOException{
        Process p = Runtime.getRuntime().exec("./FFC/adb/adb.exe devices");
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
            String line = "";
            int lineCount = 1;
            while ((line = in.readLine()) != null){
                System.out.println(line);
                    if(lineCount == 1){
                        //skip first line
                    }else{
                        if(line.length()!=0){
                            String[] SerialTemp = line.split("\t");
                            System.out.println(SerialTemp[0]);
                            if(SerialTemp[0] == null ? deviceSerial == null : SerialTemp[0].equals(deviceSerial)){
                                return true;
                            }

                        }
                    }
                    lineCount++;
                //temp.addElement(line);
            }
            return false;
    }
}
