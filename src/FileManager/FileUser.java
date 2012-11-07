/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import User.UserData;

/**
 *
 * @author pi
 */
public class FileUser extends FileBehavior{
    
     public void setPathFile(String pathFileSettingFFCDataBase){
        setPathData(pathFileSettingFFCDataBase);
    }
    public void writeDriverDataBase(UserData userData){
        writeData(userData.getName()
                +"!"
                +userData.getLastName()
                +"!"
                +userData.getPossition()
                +"!"
                +userData.getOrganizations()
                +"!"
                +userData.getAddress()
                +"!");
    }

    public UserData readDriverDataBase(){
        if(readData().length()<4){
            return null;
        }
        String []temp= readData().split("!");
        if(temp.length == 5){
        return new UserData(temp[0], temp[1], temp[2], temp[3], temp[4]);
        }
        return null;
    }
    
}
