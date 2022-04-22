/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectDatabase;
import FFC_Form.ConfigServerForm;
import FFC_Form.MainForm;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author PeeT
 */
public class ConnectSQL {
    
    public Connection connection;
    
    public ConnectSQL()
    {
        connection = connectSQL();
    }
    
    public static Connection connectSQL()
    {
        Connection connect = null;
        try
            {
                String driverDatabase = Service.Service.driverDatabase.getDriverName();
                String databaseName = Service.Service.driverDatabase.getServerName();
                String serverName = Service.Service.driverDatabase.getServer();
                String portNumber = Service.Service.driverDatabase.getPort();
                String userNameDb = Service.Service.driverDatabase.getUserName();
                String passwordDb = Service.Service.driverDatabase.getPassword();
                Class.forName(driverDatabase); //"com.mysql.jdbc.Driver"
                String url = "jdbc:mysql://"+serverName+":"+portNumber+"/"+databaseName+"?characterEncoding=utf8";
                //String url = "jdbc:mysql://localhost:3333/jhcisdb";
                connect = DriverManager.getConnection(url,userNameDb,passwordDb);
                System.out.println("Connect Status : "+connect);
                if(connect == null)
                {
                    System.out.println("Can't connect database.");
                }
                else
                {
                    System.out.println("Connect database success.");
                }
            }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog( new ConfigServerForm(), "การเชื่อมต่อล้มเหลว\nกรุณาตรวจสอบการตั้งค่าการเชื่อมต่อ","Error",JOptionPane.ERROR_MESSAGE);
            e.getStackTrace();
            System.out.println(e.getStackTrace());
        }
        return connect;
    }

    public ConnectSQL(String driverDatabase,String databaseName,String serverName,String portNumber,String userNameDb,String passwordDb)
    {
        try
            {
                Class.forName(driverDatabase); //"com.mysql.jdbc.Driver"
                String url = "jdbc:mysql://"+serverName+":"+portNumber+"/"+databaseName;
                //String url = "jdbc:mysql://localhost:3333/jhcisdb";
                this.connection = DriverManager.getConnection(url,userNameDb,passwordDb);
   
            }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog( new ConfigServerForm(), "การเชื่อมต่อล้มเหลว\nกรุณาตรวจสอบการตั้งค่าการเชื่อมต่อ","Error",JOptionPane.ERROR_MESSAGE);
            e.getStackTrace();
            System.out.println(e.getStackTrace());
        }
    }
    
    public ResultSet getResultSet(String query)
    {
        ResultSet rs = null;
        try
        {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return rs;
    }
    
    public String[] mysqlListTableName() {
        try {
            DatabaseMetaData dbmd = this.connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            rs.last();
            String[] temp = new String[rs.getRow()];
            rs.beforeFirst();
            for (int i = 0; rs.next(); i++) {
                temp[i] = rs.getString(3);
            }
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public ResultSet getResultSetHouseChronic(int selectIndex, String villcode){
        String diagChronic = "";
        switch(selectIndex)
        {
            case 0 : 
                break;
            case 1 :
                break;
            case 2 :
                break;
            case 3 :
                break;
            case 4 : diagChronic = "01";
                break;
            case 5 : diagChronic = "02";
                break;
            case 6 : diagChronic = "03";
                break;
            case 7 : diagChronic = "04";
                break;
            case 8 : diagChronic = "05";
                break;
            case 9 : diagChronic = "06";
                break; 
            case 10 : diagChronic = "07";
                break;
            case 11 : diagChronic = "08";
                break;
            case 12 : diagChronic = "09";
                break;
            case 13 : diagChronic = "10";
                break;
            case 14 : diagChronic = "11";
                break;
            case 15 : diagChronic = "12";
                break;
            case 16 : diagChronic = "13";
                break;
            case 17 : diagChronic = "14";
                break;
            case 18 : diagChronic = "15";
                break;
            case 19 : diagChronic = "16";
                break;
                
        }
        
        ResultSet rs = null;
        try
        {
            Statement st = connection.createStatement();
            if(selectIndex == 1){
                String AncQuery = "SELECT DISTINCT house.hno FROM person,house,(SELECT DISTINCT visitanc.pid,visitanc.pcucodeperson FROM visitanc WHERE visitanc.pid NOT IN (SELECT persondeath.pid FROM persondeath))t1 "
                + "WHERE person.pid = t1.pid AND person.pcucodeperson = t1.pcucodeperson AND t1.pcucodeperson = house.pcucode "
                + "AND house.hcode = person.hcode AND house.villcode = '" + villcode + "' ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC";
                rs = st.executeQuery(AncQuery);
            }
            if(selectIndex == 2){
                
            }
            if(selectIndex == 3){
                /*String Query = "SELECT DISTINCT house.hno FROM person,house WHERE person.rightcode = '74' AND person.pid NOT IN (SELECT persondeath.pid FROM persondeath)"
                        + "AND person.hcode = house.hcode AND house.villcode = '" + villcode + "' ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC";*/
                String Query = "SELECT DISTINCT t1.hno FROM (SELECT person.*,house.hno FROM person,house WHERE house.villcode = '"+villcode+"' AND house.hcode= person.hcode)t1 "+
                               "JOIN (SELECT personunable.* FROM personunable WHERE personunable.pid NOT in (SELECT persondeath.pid FROM persondeath))t2 ON t1.pid = t2.pid AND t1.pcucodeperson = t2.pcucodeperson "+
                               "ORDER BY char_length(substr(t1.hno,1,locate('/',concat(t1.hno,'/'))-1)), t1.hno ASC";
                rs = st.executeQuery(Query);
            }
            if(selectIndex > 3){
                String chronicQuery = "SELECT DISTINCT house.hno FROM house,(SELECT person.hcode,person.pid FROM personchronic,_tmpicd10tmv4,person "
                + "WHERE _tmpicd10tmv4.diag_chronic = "+ diagChronic +" AND _tmpicd10tmv4.diagcode = personchronic.chroniccode "
                + "AND personchronic.pid = person.pid)t1 WHERE t1.pid NOT IN (SELECT persondeath.pid FROM persondeath) AND t1.hcode = house.hcode AND house.villcode = '" + villcode + "' ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC";
                rs = st.executeQuery(chronicQuery);
            }
        }
        catch(Exception e)
        {
            e.getStackTrace();
        }
        return rs;
    }
    
    public ResultSet getSearchQuery(String[] name , String villcode){
        String firstName = name[0].toString();
        String lastName = "";
        if(name.length != 1)
        {
            lastName = name[1].toString();
        }
        
        ResultSet rs = null;
        //String query = "SELECT DISTINCT house.* FROM person,house WHERE person.hcode = house.hcode AND villcode IN ('"+villcode+"') AND person.fname Like '%"+ firstName +"%' "
        //        + "AND person.lname LIKE '%"+ lastName +"%' ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC ";
        String query = "SELECT house.hno,t1.fname,t1.lname FROM house JOIN (SELECT person.fname,person.lname,person.hcode FROM person "
                + "WHERE person.fname Like '%"+ firstName +"%' AND person.lname LIKE '%"+ lastName +"%')t1 ON t1.hcode = house.hcode AND villcode IN ('"+villcode+"') "
                + "ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getSearchQuery(String personId, String villcode){
        ResultSet rs = null;
        //String query = "SELECT DISTINCT house.* FROM person,house WHERE idcard LIKE '%"+personId+"%' "
        //        + "AND person.hcode = house.hcode AND house.villcode IN ('"+villcode+"') ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC ";
        String query = "SELECT house.hno,t1.fname,t1.lname FROM house JOIN (SELECT person.fname,person.lname,person.hcode FROM person "
                + "WHERE person.idcard Like '%"+ personId +"%')t1 ON t1.hcode = house.hcode AND villcode IN ('"+villcode+"') "
                + "ORDER BY char_length(substr(house.hno,1,locate('/',concat(house.hno,'/'))-1)), house.hno ASC ";
        try {
            //Statement stm = this.connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(query);
           // pstmt.setString( 1, personId);
           // pstmt.setString( 2, villcode);
            
            rs = pstmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public ResultSet getRiskQuery(boolean excercise,boolean alcohol,boolean smoking,String villcode)
    {
        String query = "";
        if(excercise == true && alcohol == true && smoking == true){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE ciga > '0' OR wisky > '0' OR exercise = '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == true && alcohol == true && smoking == false){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE wisky > '0' OR exercise = '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == true && alcohol == false && smoking == true){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE ciga > '0' OR exercise = '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == true && alcohol == false && smoking == false){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE exercise = '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == false && alcohol == true && smoking == true){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE ciga > '0' OR wisky > '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == false && alcohol == true && smoking == false){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE wisky > '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        if(excercise == false && alcohol == false && smoking == true){
            query = "SELECT DISTINCT t2.hno FROM (SELECT personbehavior.pid FROM personbehavior WHERE ciga > '0')t1,"
                    + "(SELECT person.pid,house.hno FROM person,house WHERE house.villcode IN ('"+villcode+"') AND person.hcode = house.hcode)t2 "
                    + "WHERE t1.pid = t2.pid ORDER BY char_length(substr(t2.hno,1,locate('/',concat(t2.hno,'/'))-1)), t2.hno ASC";
        }
        
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);  
            rs = pstmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
        
    }
    
    public ResultSet getPersonInformation(String hno, String villcode){
        String query = "SELECT person.hcode,person.prename,person.fname,person.lname FROM person,(SELECT house.hno,house.villcode,house.hcode FROM house)t1 WHERE t1.hno = '"+hno+"' AND t1.villcode IN ('"+villcode+"') AND t1.hcode = person.hcode";
        ResultSet rs = null;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);  
            rs = pstmt.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public int getLastNum(String tableName, String columnName){
        int lastNum = 0;
        try {
            String query = "SELECT MAX("+columnName+") as lastNumber FROM "+tableName;
            System.out.println(query);
            ResultSet rs = this.getResultSet(query);
            while(rs.next()){
                lastNum = rs.getInt("lastNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lastNum;
    }

    public void closeConnection() throws SQLException{
        this.connection.close();
    }

    public void createConnection(){
        connection = connectSQL();
    }


    public boolean checkExistTable(String tableName) throws SQLException{
        DatabaseMetaData dbmd = this.connection.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = dbmd.getTables(null, null, "%", types);
        rs.last();
        //String[] temp = new String[rs.getRow()];
        rs.beforeFirst();
        for (int i = 0; rs.next(); i++) {
            if(rs.getString(3).equals(tableName)){
                //System.out.println(rs.getString(3)+" : true");
                return true;
            }else{
                //System.out.println(rs.getString(3)+" : false");
            }
            //String tablename = rs.getString(3);
            //  System.out.println(temp[i]);
        }
        return false;
    }

    public boolean checkExistColumn(String tablename,String columnname) throws SQLException{
        ResultSet rsColumns = null;
        DatabaseMetaData meta = this.connection.getMetaData();
        rsColumns = meta.getColumns(null, null, tablename, null);
        while(rsColumns.next()){
            //System.out.println(rsColumns.getString("COLUMN_NAME"));
            if(rsColumns.getString("COLUMN_NAME").equals(columnname)){
                    return true;
                }
                else{
                    
                }
        }
        return false;
    }

    public boolean checkColumnLen(String tablename,String columnname,int len) throws SQLException{
        ResultSet rsColumns = null;
        DatabaseMetaData meta = this.connection.getMetaData();
        rsColumns = meta.getColumns(null, null, tablename, null);
        while(rsColumns.next()){
            if(rsColumns.getString("COLUMN_NAME").equals(columnname)){
                if(rsColumns.getInt("COLUMN_SIZE")== len){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    }

    public String getHosName() throws SQLException{
        String query = "SELECT hosname FROM chospital JOIN (SELECT pcucode FROM village LIMIT 1)t1 ON chospital.hoscode = t1.pcucode";
        ResultSet rs = this.getResultSet(query);
        String hosname = "";
        while(rs.next()){
             hosname = rs.getString("hosname");
        }
        return hosname;
    }
}
