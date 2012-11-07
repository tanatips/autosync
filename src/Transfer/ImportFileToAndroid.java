/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;

import CompressFile.ExtractFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class ImportFileToAndroid implements Runnable{
    String path;
    public void setPath(String path){
        this.path = path;
    }

    @Override
    public void run(){
        try {
            ExtractFile extract = new ExtractFile();
            //extract.getZipFiles(path);
            this.deleteRecord();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ImportFileToAndroid.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImportFileToAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRecord() throws ClassNotFoundException, SQLException{
        ConnectDatabase.ConnectSQLite connectSQLite= new ConnectDatabase.ConnectSQLite();
        connectSQLite.connectSQLite("./FFC/Db_tmp/mJHCIS.db");
        String sqlDelete = "DELETE FROM person";
        String sqlInsert = "LOAD DATA INFILE 'C:/FFC/File_Temp/Extract/person.csv' "+
                           "INTO TABLE person "+
                           "FIELDS TERMINATED BY ',' "+
                           "OPTIONALLY ENCLOSED BY '' "+
                           "LINES TERMINATED BY '\n'";
        try {
            Statement stm = connectSQLite.SQLiteConnection.createStatement();
            stm.execute(sqlDelete);
            stm.execute(sqlInsert);
        } catch (SQLException ex) {
            Logger.getLogger(ImportFileToAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertRecord(String tableName) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("your file"));
        String line;
        int countLine=0;
        String insertQuery1 = "INSERT INTO "+tableName+" (";
        String insertQuery2 = " VALUES (";
        while ( (line=br.readLine()) != null)
        {
                 String[] values = line.split(",");    //your seperator
                 for(int i=0;i<values.length;i++){

                 }
                 //Convert String to right type. Integer, double, date etc.

                 //Use a PeparedStatemant, it´s easier and safer
        }
        br.close();
    }
}
