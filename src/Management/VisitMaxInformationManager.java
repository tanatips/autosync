/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import Information.VisitMaxInformation;
import java.util.ArrayList;

/** VisitVisitMaxInformationManager.java
@author Administrator
@version 1.3g
@see InformationBehavior
 */
public class VisitMaxInformationManager extends InformationBehavior{
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
    public void insertInformation
            (VisitMaxInformation visitMaxInformation){
        super.writeInformationTo(WORD_STEP
                +visitMaxInformation.getSerialDevice()
                +WORD_INTERN
                +visitMaxInformation.getVisitMax());
    }
    public void upDateInformation
            (String serialDevice,String VisitMaxUpDate){
        String tempOut = "";
        ArrayList<VisitMaxInformation> tempInformation
                = new ArrayList<VisitMaxInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempInformation.get(i).setVisitMax(VisitMaxUpDate);
                break;
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut +=WORD_STEP+ tempInformation.get(i).getSerialDevice()
                    +WORD_INTERN
                    +tempInformation.get(i).getVisitMax();
        }
        writeInformationOnly(tempOut);
    }
    public void deleteInformation(String serialDevice){
        String tempOut = "";
        ArrayList<VisitMaxInformation> tempInformation
                = new ArrayList<VisitMaxInformation>();
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
                    +tempInformation.get(i).getVisitMax();
        }
        writeInformationOnly(tempOut);
    }
    public int getVisitMax(String serialDevice){
        String tempOut = "";
        ArrayList<VisitMaxInformation> tempInformation
                = new ArrayList<VisitMaxInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i<tempInformation.size();i++){
            if(tempInformation.get(i).getSerialDevice()
                    .equals(serialDevice)){
                tempOut=tempInformation.get(i).getVisitMax();
                break;
            }
        }
        System.out.println(tempOut);
        System.out.println(tempOut.length());
        return Integer.parseInt(tempOut.trim());
    }
    public ArrayList<VisitMaxInformation> getAllInformation(){
        ArrayList<VisitMaxInformation> tempInformation
                = new ArrayList<VisitMaxInformation>();
        String tempInformationReadIn = readInformationTo();
        String tempSplit[] = tempInformationReadIn.split(WORD_STEP);
        for(int i=1;i<tempSplit.length;i++){
            String tempIn = tempSplit[i];
        //    tempInformation.add();
            tempInformation.add(new VisitMaxInformation
                    (tempIn.split(WORD_INTERN)[0]
                    , tempIn.split(WORD_INTERN)[1]));
        }

        return tempInformation;
    }
}
