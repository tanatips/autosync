/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

import java.util.Date;

/**
 *
 * @author PeeT
 */
public class BackupInformation {

    private String databaseName;
    private String backupName;
    private String backupPath;
    private String dateBackup;

    public BackupInformation(){

    }

    public BackupInformation(String databaseName,String backupName,String dateBackup,String backupPath){
        this.databaseName = databaseName;
        this.backupName = backupName;
        this.backupPath = backupPath;
        this.dateBackup = dateBackup;
    }

    public String getdatabaseName(){
        return this.databaseName;
    }

    public String getBackupName(){
        return this.backupName;
    }

    public String getBackupPath(){
        return this.backupPath;
    }

    public String getDateBackup(){
        return this.dateBackup;
    }
}
