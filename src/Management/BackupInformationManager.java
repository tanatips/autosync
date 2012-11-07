/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;


import Information.BackupInformation;
import java.util.ArrayList;

/**
 *
 * @author PeeT
 */
public class BackupInformationManager extends InformationBehavior{
        public void setFileURL(String url){
        setURL(url);
    }
/**
 * insert visitMaxInformation in data information
 * @param visitMaxInformation
 */

    public void insertInformation(BackupInformation backupInformation){
        super.writeInformationTo(WORD_STEP
                + backupInformation.getdatabaseName()
                +WORD_INTERN
                + backupInformation.getBackupName()
                +WORD_INTERN
                + backupInformation.getDateBackup().toString()
                +WORD_INTERN
                + backupInformation.getBackupPath());
    }

    public void deleteInformation(String backupName){
        String tempOut = "";
        ArrayList<BackupInformation> tempInformation
                = new ArrayList<BackupInformation>();
        tempInformation = getAllInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getBackupName()
                    .equals(backupName)){
                tempInformation.remove(i);
                break;
            }
        }
        for(int i=0;i< tempInformation.size();i++){
            tempOut +=WORD_STEP
                    + tempInformation.get(i).getdatabaseName()
                    +WORD_INTERN
                    +tempInformation.get(i).getBackupName()
                    +WORD_INTERN
                    +tempInformation.get(i).getDateBackup()
                    +WORD_INTERN
                    +tempInformation.get(i).getBackupPath();

        }
        writeInformationOnly(tempOut);
    }

    public BackupInformation getInformation(String backupName){
        ArrayList<BackupInformation> tempInformation
                = new ArrayList<BackupInformation>();
        tempInformation = getAllInformation();
        BackupInformation backupInformation =new BackupInformation();
        for(int i =0;i < tempInformation.size();i++){
            if(tempInformation.get(i).getBackupName()
                    .equals(backupName)){
                backupInformation = tempInformation.get(i);
                break;
            }
        }
        return backupInformation;
    }

    public ArrayList<BackupInformation> getAllInformation(){
        ArrayList<BackupInformation> tempInformation
                = new ArrayList<BackupInformation>();
        String tempInformationReadIn = readInformationTo();
        String tempSplit[] = tempInformationReadIn.split(WORD_STEP);
        for(int i=1;i<tempSplit.length;i++){
            String tempIn = tempSplit[i];
        //    tempInformation.add();
            tempInformation.add(new BackupInformation(
                    tempIn.split(WORD_INTERN)[0]
                    , tempIn.split(WORD_INTERN)[1]
                    ,tempIn.split(WORD_INTERN)[2]
                     ,tempIn.split(WORD_INTERN)[3]));
        }

        return tempInformation;
    }
}
