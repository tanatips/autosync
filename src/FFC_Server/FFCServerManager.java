/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FFC_Server;

import ConnectDatabase.ConnectSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class FFCServerManager {

    String driverDatabase;
    String databaseName;
    String serverName;
    String userNameDb;
    String passwordDb;
    String portNumber;

    public FFCServerManager(){
        this.serverName = /*"localhost";*/"203.185.96.254";
        this.driverDatabase = "com.mysql.jdbc.Driver";
        this.portNumber = "3306";//"80";
        this.userNameDb = "root";//"root";
        this.passwordDb = /*"1234";*/"ffc@wis";
        this.databaseName = /*"j_ubon";*/"kitalo_ffc";
    }

    public void ConnectTest(){
        try {
            ConnectSQL connect = new ConnectSQL(this.driverDatabase, this.databaseName, this.serverName, this.portNumber, this.userNameDb, this.passwordDb);
            ResultSet rs = connect.getResultSet("SELECT * FROM user limit 10");
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FFCServerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
