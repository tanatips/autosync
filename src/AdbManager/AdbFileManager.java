/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AdbManager;
//import DeviceManager.BatFileManager;


import DeviceManager.BatFileManager;

/**
 *
 * @author PeeT
 */
public class AdbFileManager extends BatFileManager{

    @Override
    public void setPathFileBat(String filePath){
        super.setPathData(filePath);
    }
    public void writeAdbBatFileCopyPush(String deviceSerial,String sourcePath ,String DestinationPath){
        String Command = "@echo off"
                + "\n"
                + "cd ./FFC/adb"
                + "\n"
                + "adb -s "
                + deviceSerial
                + " push "
                + sourcePath
                + " "
                + DestinationPath
                + "\n"
                + "echo success";
                
        super.writeData(Command);
    }

    public void writeAdbBatFileCopyPull(String deviceSerial,String sourcePath ,String DestinationPath){
        String Command = "@echo off"
                + "\n"
                + "cd ./FFC/adb"
                + "\n"
                + "adb -s "
                + deviceSerial
                + " pull "
                + sourcePath
                + " "
                + DestinationPath
                + "\n"
                + "echo success";

        super.writeData(Command);
    }

    public void writeBatFileRemove(String deviceSerial,String target){
        String Command = "@echo off"
                + "\n"
                + "cd ./FFC/adb"
                + "\n"
                + "adb -s "
                + deviceSerial
                + " shell rm -r "
                + target;
        super.writeData(Command);
    }
}
