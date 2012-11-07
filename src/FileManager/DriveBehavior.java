/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileManager;

import Service.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import javax.swing.DefaultListModel;

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
        String temp =null;
        System.out.println( Arrays.asList( File.listRoots() ) ) ;
        for(int i =0;i<14;i++){
            try {
                FileInputStream pop = new FileInputStream(drive(i)+this.pathForFineDrive);
                System.out.println("Fount Drive : "+drive(i)+this.pathForFineDrive);
                temp =drive(i);
            } catch (FileNotFoundException ex) {
                System.out.println("Not Found drive : "+drive(i)+this.pathForFineDrive);
            }
        }
        System.out.println(" getDrive() :"+temp);
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
}
