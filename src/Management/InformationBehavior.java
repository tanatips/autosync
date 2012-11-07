/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class InformationBehavior extends Behavior{
    //Attribute

    //end attribute

    //Behaviori
    public void setURL(String url){
        super.setPathData(url);
    }
    public String getURL(){
        return super.getPathData();
    }
    public void writeInformationTo(String information){
        String tempInformation = "";
        tempInformation = super.readData();
        System.out.println(tempInformation);
        super.writeData1(tempInformation+information);
    }
    /**
     * write information to file by non save old information
     * read file be "AAAA"
     * writeInformationOnly("BBB")
     * data in file is "BBB"
     * @param String information
     */
    public void writeInformation(String information){
        String tempInformation = "";
        tempInformation = super.readData();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxx: "+tempInformation);
        //if(tempInformation.equals("")){
            super.writeData(tempInformation+information);
       // }else{
       //     super.writeData(tempInformation+"\n"+information);
        //}
    }

    public void writeInformationOnly(String information){
        super.writeData(information);
    }
    public String readInformationTo(){
        return super.readData();
    }
    //end behavior

}
