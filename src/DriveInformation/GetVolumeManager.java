/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DriveInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    public String getVolumeDrive(String drive){
        this.writeVolumeBatFile(drive);
        return this.getVolumeSerialId();
    }
//    public static void main(String[]args){
//        GetVolumeManager gs = new GetVolumeManager("C:/volume.bat");
//        System.out.println("Serial : "+gs.getVolumeDrive("D"));
//    }

}
