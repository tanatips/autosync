/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import Information.CheckPersonInformation;
import Information.VisitMaxInformation;
import java.util.ArrayList;

/** VisitVisitMaxInformationManager.java
@author Administrator
@version 1.3g
@see InformationBehavior
 */
public class PersonCheckManager extends InformationBehavior{
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
    String word_intern = ",";
    

    public void insertInformation
            (CheckPersonInformation checkPersonInformation){
        super.writeInformation("DeviceSerial="+checkPersonInformation.getSerialDevice()+",MaxPid="+checkPersonInformation.getpidMax());
    }
    public void upDateInformation
            (String serialDevice,String VisitMaxUpDate){
        String tempOut = "";
        ArrayList<CheckPersonInformation> tempInformation
                = new ArrayList<CheckPersonInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            System.out.println("serial : "+tempInformation.get(i).getSerialDevice()+",pid : "+tempInformation.get(i).getpidMax());
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempInformation.get(i).setpidMax(VisitMaxUpDate);
                break;
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut += ("DeviceSerial="+ tempInformation.get(i).getSerialDevice()
                    +",MaxPid="
                    +tempInformation.get(i).getpidMax()+"\n");
        }
        super.writeInformationOnly(tempOut);
    }
    public void deleteInformation(String serialDevice){
        String tempOut = "";
        ArrayList<CheckPersonInformation> tempInformation
                = new ArrayList<CheckPersonInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempInformation.remove(i);
                break;
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut +=WORD_STEP+ tempInformation.get(i).getSerialDevice()
                    +WORD_INTERN
                    +tempInformation.get(i).getpidMax();
        }
        writeInformationOnly(tempOut);
    }
    public int getPidMax(String serialDevice){
        System.out.println("Device Serial : "+serialDevice);
        String tempOut = "";
        ArrayList<CheckPersonInformation> tempInformation
                = new ArrayList<CheckPersonInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i<tempInformation.size();i++){
            System.out.println(tempInformation.get(i).getSerialDevice() +" : "+ serialDevice );
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempOut=tempInformation.get(i).getpidMax();
                break;
            }
        }
        return Integer.parseInt(tempOut);
    }
    public ArrayList<CheckPersonInformation> getAllInformation(){
        ArrayList<CheckPersonInformation> tempInformation
                = new ArrayList<CheckPersonInformation>();
        ArrayList<String> tempInformationReadIn = super.readDataLine();
        
        for(int i=0;i<tempInformationReadIn.size();i++){
            String tempSplit[] = tempInformationReadIn.get(i).split(",");
            String tempserial = tempSplit[0];
            String tempmaxpid = tempSplit[1];
        //    tempInformation.add();
            tempInformation.add(new CheckPersonInformation
                    (tempserial.split("=")[1]
                    , tempmaxpid.split("=")[1]));
        }

        return tempInformation;
    }
}
