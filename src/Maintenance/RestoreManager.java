/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Maintenance;

import DeviceManager.BatFileManager;

/**
 *
 * @author PeeT
 */
public class RestoreManager extends BatFileManager
{
    
    public void setPathData(String pathData) {
        super.setPathData(pathData);
    }

    public void writeBatFileRestore(String serverUsername,String serverPassword,String databaseName,String backupPath){
         String command = "mysql -u"
                + serverUsername
                + " -p"
                + serverPassword
                + " "
                + databaseName
                + " < "
                + backupPath
                +"\n"
                +"echo Restore success";

        super.writeBatFile(command);
    }




}
