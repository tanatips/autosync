/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FFC_Information;

import java.sql.*;

/**
 *
 * @author PeeT
 */
public class FFCInformationManager {
    Connection sqliteConnection;

    public void openConnection(String dbPath) throws ClassNotFoundException, SQLException{
        ConnectDatabase.ConnectSQLite connectSQLite = new ConnectDatabase.ConnectSQLite();
        connectSQLite.connectSQLite(dbPath);
        this.sqliteConnection = connectSQLite.SQLiteConnection;
    }

    public void closeConnection() throws SQLException{
        this.sqliteConnection.close();
    }
    /*public void FFCInformationManager(){
        ConnectDatabase.ConnectSQLite connectSQLite = new ConnectDatabase.ConnectSQLite();
        connectSQLite.connectSQLite(Service.Service.ffcInformationPath);
        this.sqliteConnection = connectSQLite.SQLiteConnection;
    }*/

    public void InsertNewDevice(String deviceSerial,String deviceName,String deviceModel) throws SQLException{
        String insertQuery = "INSERT INTO device_information (dserial,dname,dmodel) VALUES (?,?,?)";
        PreparedStatement pstm = this.sqliteConnection.prepareStatement(insertQuery);
        pstm.setString(1, deviceSerial);
        pstm.setString(2, deviceName);
        pstm.setString(3, deviceModel);
        pstm.executeUpdate();
    }

    public Information.FFCInformation getFFCInformation(String deviceSerial) throws SQLException{
        String dserial = "";
        String devicename = "";
        String devicemodel = "";
        String maxvisit = "";
        String dateupdate = "";
        String maxpid = "";
        String maxhcode = "";
        String query = "SELECT * FROM device_information WHERE dserial = ?";
        PreparedStatement pstm = this.sqliteConnection.prepareStatement(query);
        pstm.setString(1, deviceSerial);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            dserial = rs.getString("dserial");
            devicename = rs.getString("dname");
            devicemodel = rs.getString("dmodel");
            maxvisit = rs.getString("maxvisit");
            dateupdate = rs.getString("lastupdate");
            maxpid = rs.getString("maxpid");
            maxhcode = rs.getString("maxhcode");
        }
        Information.FFCInformation ffcInformation = new Information.FFCInformation(dserial,devicename,devicemodel,maxvisit,dateupdate,maxpid,maxhcode);
        return ffcInformation;
    }

    private void updateInformation(String tableName,String fieldname,String value,String deviceSerial) throws SQLException{
        String sqlUpdate = "UPDATE "+tableName+" SET "+fieldname+" = ? WHERE dserial = ?";
        PreparedStatement pstm = this.sqliteConnection.prepareStatement(sqlUpdate);
        //pstm.setString(1, tableName);
        //pstm.setString(2, fieldname);
        pstm.setString(1, value);
        pstm.setString(2, deviceSerial);
        System.out.println(pstm.toString());
        pstm.executeUpdate();
    }

    public void deleteInformation(String deviceSerial) throws SQLException{
        String query = "DELETE FROM device_information WHERE dserial = ?";
        PreparedStatement pstm = this.sqliteConnection.prepareStatement(query);
        pstm.setString(1, deviceSerial);
        pstm.executeUpdate();
    }

    public void updateMaxvisit(String deviceSerial,String visitno) throws SQLException{
        this.updateInformation("device_information", "maxvisit", visitno, deviceSerial);
    }

    public void updateLastupdate(String deviceSerial,String date) throws SQLException{
        this.updateInformation("device_Information", "lastupdate", date, deviceSerial);
    }

    public void updateMaxpid(String deviceSerial,String pid) throws SQLException{
        this.updateInformation("device_information", "maxpid", pid, deviceSerial);
    }

    public void updateMaxhcode(String deviceSerial,String hcode) throws SQLException{
        this.updateInformation("device_information", "maxhcode", hcode, deviceSerial);
    }

    private String getInformation(String deviceSerial, String fieldname) throws SQLException{
        String value = "";
        String query = "SELECT "+fieldname+" FROM device_information WHERE dserial = '"+deviceSerial+"'";
        //PreparedStatement pstm = this.sqliteConnection.prepareStatement(query);
        //pstm.setString(1, fieldname);
        //pstm.setString(2, deviceSerial);
        //System.out.println(pstm);
        Statement stm = this.sqliteConnection.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()){
            value = rs.getString(fieldname);
        }
        System.out.println("Print Value : "+value);
        return value;
    }

    public String getMaxvisit(String deviceserial) throws SQLException{
        return this.getInformation(deviceserial, "maxvisit");
    }

    public String getLastupdate(String deviceserial) throws SQLException{
        return this.getInformation(deviceserial, "lastupdate");
    }

    public String getMaxpid(String deviceserial) throws SQLException{
        return this.getInformation(deviceserial, "maxpid");
    }

    public String getMaxhcode(String deviceserial) throws SQLException{
        return this.getInformation(deviceserial, "maxhcode");
    }

    public boolean checkDevice(String serial) throws SQLException{
        String query = "SELECT dserial FROM device_information WHERE dserial = '"+serial+"'";
        Statement stm = this.sqliteConnection.createStatement();
        ResultSet rs = stm.executeQuery(query);
        while(rs.next()){
            return true;
        }
        return false;
    }

}
