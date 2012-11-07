/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDatabase;

//import DeviceManager.DeviceInformation;
//import auto_sync_v2.Main;
//import java.sql.Connection;
import java.sql.*;


/**
 *
 * @author PeeT
 */
public class ConnectSQLite {
    
    public Connection SQLiteConnection = null;
            
    
    public void connectSQLite(String targetDrive,String pathFile) throws ClassNotFoundException, SQLException
    {
        this.connect(targetDrive, pathFile);
        //return SQLiteConnection;
    }
    public void connectSQLite(String pathFile) throws ClassNotFoundException, SQLException{
        String targetDrive = "";
        this.connect(targetDrive, pathFile);
    }

    public void connect(String targetDrive,String pathFile) throws ClassNotFoundException, SQLException{
       
            Class.forName("org.sqlite.JDBC");
            SQLiteConnection = DriverManager
                    .getConnection("jdbc:sqlite:"+ targetDrive+pathFile/*":\\FFC\\DB\\mJHCIS.sdb"*/);
            if(SQLiteConnection != null)
            {
                System.out.println("Connect SQLite success !!!");
            }
            else
            {
                System.out.println("Can not Connect SQLite !!!");
            }
        
    }
    
     public ResultSet getResultSet(String query) throws SQLException
    {
        ResultSet rs = null;
        
            Statement st = SQLiteConnection.createStatement();
            rs = st.executeQuery(query);
        
        return rs;
    }
    
     public int getVisitMaxAndroid() throws SQLException
     {
         int visitMaxAndroid = 0;
         String queryVisitMax = "SELECT MAX(visitno) AS visitMaxAndroid FROM visit";
         ResultSet rs = this.getResultSet(queryVisitMax);
        
            while(rs.next())
            {
                visitMaxAndroid = rs.getInt("visitMaxAndroid");
            }
       
        return visitMaxAndroid;
     }

     public int getPidMaxAndroid() throws SQLException
     {
         int pidMaxAndroid = 0;
         String queryPidMax = "SELECT MAX(pid) AS pidMaxAndroid FROM person";
         ResultSet rs = this.getResultSet(queryPidMax);
            while(rs.next())
            {
                pidMaxAndroid = rs.getInt("pidMaxAndroid");
            }
        return pidMaxAndroid;
     }

     public int getHcodeMaxAndroid() throws SQLException
     {
            int hcodeMaxAndroid = 0;
            String queryPidMax = "SELECT MAX(hcode) AS hcodeMaxAndroid FROM house";
            ResultSet rs = this.getResultSet(queryPidMax);
            while (rs.next()) {
                hcodeMaxAndroid = rs.getInt("hcodeMaxAndroid");
            }
            return hcodeMaxAndroid;
     }

    /* public String[] SetVillageOnAndroid() throws SQLException
     {
         String[] village = new String[2];
         int count = 0;
         String villageOnAndroid = "";
         String villageTooltip = "";
         String query = "SELECT DISTINCT villcode,villname FROM (SELECT DISTINCT * FROM village JOIN house ON village.villcode = house.villcode)";
         ResultSet rs = null;
         rs = this.getResultSet(query);
            while(rs.next())
            {
                if(count<2){
                    villageOnAndroid += rs.getString("villname") + ", ";
                }else{
                    villageTooltip += rs.getString("villname")
                }
                count++;
            }
        return village;
     }*/
     
     public boolean closeConnection() throws SQLException{
        this.SQLiteConnection.close();
        if(this.SQLiteConnection.isClosed()){
            return true;
        }else{
            return false;
        }
     }
    
}
