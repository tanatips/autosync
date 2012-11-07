/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Export;

import java.io.FileWriter;
import ConnectDatabase.ConnectSQLite;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import au.com.bytecode.opencsv.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author PeeT
 */
public class ExportToTextFile {

    public void Export(boolean includeHeaders,char separater,String... tableName) throws IOException, SQLException{
        try {
            ConnectSQLite connect = new ConnectSQLite();
            connect.connectSQLite("./FFC/Db_tmp/mJHCIS.db");
            for (int i = 0; i < tableName.length; i++) {
                System.out.println(tableName[i]);
                String exportDataQuery = "SELECT * FROM " + tableName[i];
                System.out.println(exportDataQuery);
                ResultSet exportDataResultSet = connect.getResultSet(exportDataQuery);
                CSVWriter writer = new CSVWriter(new FileWriter(".\\FFC\\File_Temp\\Compress\\" + tableName[i] + ".csv"), separater, '\u0000', '\u0000', "\n");
                writer.writeAll(exportDataResultSet, includeHeaders);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExportToTextFile.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }


}
