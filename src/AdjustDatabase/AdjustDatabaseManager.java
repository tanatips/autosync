/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AdjustDatabase;


import java.sql.*;

/**
 *
 * @author PeeT
 */
public class AdjustDatabaseManager {
    Connection sqliteConnection;

    public AdjustDatabaseManager() throws ClassNotFoundException, SQLException{
        ConnectDatabase.ConnectSQLite connectSQLite = new ConnectDatabase.ConnectSQLite();
        connectSQLite.connectSQLite("./FFC/FFC_Information.db");
        this.sqliteConnection = connectSQLite.SQLiteConnection;
    }


    public void updateResultAdjust(String columnName,String value) throws SQLException{
        String query = "UPDATE adjust_database SET "+columnName+" = '"+value+"'";
        Statement stm = this.sqliteConnection.createStatement();
        stm.executeUpdate(query);
    }

    public void closeConnection() throws SQLException{
        this.sqliteConnection.close();
    }

    public int getvalue(String columnName) throws SQLException{
        int value = 0;
        String query = "SELECT "+columnName+" FROM adjust_database";
        Statement stm = this.sqliteConnection.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()){
            value = rs.getInt(columnName);
        }
        return value;
    }

    public int getAdjustAll() throws SQLException{
        return this.getvalue("db_version");
    }

}
