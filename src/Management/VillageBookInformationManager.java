/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import Information.VillageBookInformation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class VillageBookInformationManager extends InformationBehavior{
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
 * @param villageBookInformationManager
 */
    public void insertInformation
            (VillageBookInformation villageBookInformation){
        super.writeInformationTo(WORD_STEP
                +villageBookInformation.getSerialDevice()
                +WORD_INTERN
                +villageBookInformation.getVillageCode()
                +WORD_INTERN
                +villageBookInformation.getdateUpdate());
    }

    public void deleteInformation(String serialDevice){
        String tempOut = "";
        ArrayList<VillageBookInformation> tempInformation
                = new ArrayList<VillageBookInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempInformation.remove(i);
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut +=WORD_STEP+ tempInformation.get(i).getSerialDevice()
                    +WORD_INTERN
                    +tempInformation.get(i).getVillageCode()
                    +WORD_INTERN
                    +tempInformation.get(i).getdateUpdate();
        }
        writeInformationOnly(tempOut);
    }
    public ArrayList<String> getDeviceBook (String villageCode){
        ArrayList<String> tempOut = new ArrayList<String>();
        ArrayList<VillageBookInformation> tempInformation
                = new ArrayList<VillageBookInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i<tempInformation.size();i++){
            if(tempInformation.get(i).getVillageCode()
                    .equals(villageCode)){
                tempOut.add(tempInformation.get(i).getSerialDevice());
            }
        }
        return tempOut;
    }
    public ArrayList<String> getVillageBook(String serialDevice){
        ArrayList<String> tempOut = new ArrayList<String>();
        ArrayList<VillageBookInformation> tempInformation
                = new ArrayList<VillageBookInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i<tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempOut.add(tempInformation.get(i).getVillageCode());
            }
        }
        return tempOut;
    }
    public boolean isDeviceSerialBook(String deviceSerial){
        ArrayList<VillageBookInformation> tempInformation
                = new ArrayList<VillageBookInformation>();
        tempInformation = getAllInformation();
        for (int i = 0; i < tempInformation.size(); i++) {
            if (tempInformation.get(i).getSerialDevice().equals(deviceSerial)) {
                return true;
            }
        }
        return false;
    }
    public ArrayList<VillageBookInformation> getAllInformation(){
        ArrayList<VillageBookInformation> tempInformation
                = new ArrayList<VillageBookInformation>();
        String tempInformationReadIn = readInformationTo();
        String tempSplit[] = tempInformationReadIn.split(WORD_STEP);
        for(int i=1;i<tempSplit.length;i++){
            String tempIn = tempSplit[i];
        //    tempInformation.add();
            tempInformation.add(new VillageBookInformation(
                    tempIn.split(WORD_INTERN)[0]
                  , tempIn.split(WORD_INTERN)[1]
                  , tempIn.split(WORD_INTERN)[2]));
        }
        //System.out.println(tempInformation.get(0));
        return tempInformation;
    }
    
    public void updateDateUpDateInformation(String serialDevice)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",java.util.Locale.US);
        Date currentDate = new Date();
        ArrayList<VillageBookInformation> tempInformation = new ArrayList<VillageBookInformation>();
        tempInformation = this.getAllInformation();
        String tempOut = "";
        for(int i=0; i<tempInformation.size(); i++)
        {
            if(tempInformation.get(i).getSerialDevice().equals(serialDevice))
            {
                tempInformation.get(i).setDateUpdate(dateFormat.format(currentDate).toString());
                tempOut += super.WORD_STEP + tempInformation.get(i).getSerialDevice() + super.WORD_INTERN + tempInformation.get(i).getVillageCode() +
                          super.WORD_INTERN + tempInformation.get(i).getdateUpdate();
            }
            else
            {
                tempOut += super.WORD_STEP + tempInformation.get(i).getSerialDevice() + super.WORD_INTERN + tempInformation.get(i).getVillageCode() +
                          super.WORD_INTERN + tempInformation.get(i).getdateUpdate();
            }
        }
        super.writeInformationOnly(tempOut);
    }
    
}
