/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Maintenance;
//package DeviceManager;
import DeviceManager.BatFileManager;
import FileManager.FileManager;
/**
 *
 * @author PeeT
 */
public class BackupManager extends BatFileManager
{
    
    public void setPathData(String pathData) {
        super.setPathData(pathData);
    }


    protected void writeBatFileBackup(String databaseName,String backupPath,String serverUsername,String serverPassword) {
        String command = "mysqldump -u"
                + serverUsername
                + " -p"
                + serverPassword
                + " "
                + databaseName
                + " > "
                + backupPath
                +"\n"
                +"echo Backup success";

        super.writeBatFile(command);
    }

    protected boolean deleteBackup(String backupPath){
        FileManager fileManager = new FileManager();
        boolean result = fileManager.deleteDirectory(backupPath);
        return result;
    }



}
