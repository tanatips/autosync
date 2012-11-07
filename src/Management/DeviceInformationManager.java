/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import DeviceManager.DeviceInformation;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class DeviceInformationManager extends InformationBehavior{
//------------------------------------------------------------------------------
//Atribute hear



//------------------------------------------------------------------------------
//Behavior hear
/**
 * Set the URL path file
 * @param url
 */
    public void setFileURL(String url){
        setURL(url);
    }
/**
 * insert visitMaxInformation in data information
 * @param visitMaxInformation
 */
    
    public void insertInformation(DeviceInformation deviceInformation){
        super.writeInformationTo(WORD_STEP
                +deviceInformation.getSerialDevice()
                +WORD_INTERN
                +deviceInformation.getNameDevice()
                +WORD_INTERN
                +deviceInformation.getModelDevice());
    }
    
    public void deleteInformation(String serialDevice){
        String tempOut = "";
        ArrayList<DeviceInformation> tempInformation
                = new ArrayList<DeviceInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempInformation.remove(i);
                break;
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut +=WORD_STEP
                    + tempInformation.get(i).getSerialDevice()
                    +WORD_INTERN
                    +tempInformation.get(i).getNameDevice()
                    +WORD_INTERN
                    +tempInformation.get(i).getModelDevice();
        }
        writeInformationOnly(tempOut);
    }
    
    public DeviceInformation getInformation(String serialDevice){
        ArrayList<DeviceInformation> tempInformation
                = new ArrayList<DeviceInformation>();
        tempInformation = getAllInformation();
        DeviceInformation deviceInformation =new DeviceInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                deviceInformation = tempInformation.get(i);
                break;
            }
        }
        return deviceInformation;
    }
    
    public boolean isSerialRegisted(String serialDevice){
        ArrayList<DeviceInformation> tempInformation
                = new ArrayList<DeviceInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                return true;
            }
        }
       return false;
    }
    
    public ArrayList<DeviceInformation> getAllInformation(){
        ArrayList<DeviceInformation> tempInformation
                = new ArrayList<DeviceInformation>();
        String tempInformationReadIn = readInformationTo();
        String tempSplit[] = tempInformationReadIn.split(WORD_STEP);
        for(int i=1;i<tempSplit.length;i++){
            String tempIn = tempSplit[i];
        //    tempInformation.add();
            tempInformation.add(new DeviceInformation(
                    tempIn.split(WORD_INTERN)[0]
                    , tempIn.split(WORD_INTERN)[1]
                    ,tempIn.split(WORD_INTERN)[2]));
        }

        return tempInformation;
    }
}
