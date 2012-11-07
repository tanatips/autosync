/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AdbManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author PeeT
 */
public class AdbCommand {
    public ArrayList<String> getFileListAndroid(String DirectoryPath){
        ArrayList<String> arrayList = new ArrayList<String>();
        String line = null;

        try {
            Process p = Runtime.getRuntime().exec("./FFC/adb/adb.exe -s "+Service.Service.serialDeviceConnect+" shell ls "+ DirectoryPath);
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
             System.out.println(p);

            while ((line = in.readLine()) != null) {
                if(line.length() == 0){

                }else{
                    arrayList.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String fileName : arrayList){
            System.out.println(fileName);
        }
        return arrayList;

    }

    public boolean checkFileOnAndroidExist(String SerialDevice,String filePath,String fileName){
            ArrayList<String> arrayList = new ArrayList<String>();
            String line = null;
            try {
            String command = "./FFC/adb/adb.exe -s "+SerialDevice+" shell ls -a "+filePath;
                System.out.println(command);
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
             System.out.println(p);

            while ((line = in.readLine()) != null) {
                arrayList.add(line);
            }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(int i =0;i<arrayList.size();i++){
                if(arrayList.get(i).toString() == null ? fileName == null : arrayList.get(i).toString().equals(fileName)){
                    return true;
                }
            }
            return false;
        }

    public boolean createDirectoryOnAndroid(String serialDevice,String directoryName) throws IOException{

        Process p = Runtime.getRuntime().exec("./FFC/adb/adb.exe -s "+Service.Service.serialDeviceConnect+" shell mkdir "+directoryName);
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(p.getInputStream()));
        System.out.println(p);
        if(checkFileOnAndroidExist(serialDevice,directoryName,"")){
            return true;
        }
        return false;
    }
}
