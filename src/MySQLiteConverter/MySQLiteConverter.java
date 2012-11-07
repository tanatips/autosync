package MySQLiteConverter;

import FFC_Form.MainForm;
import FFC_Form.TransferForm;
import Transfer.ConvertDatabase;
import Transfer.JhcisToAndroid;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 
 * Name:    Blaze - Piruin Panichphol
 * Career:  Software Engineer
 * Email:   piruin.p@gmail.com
 *
 *  start @ 2010,09,08 12:
 *
 * version 0.9
 * September 12, 2010
 * -clone mySQL table to SQLite table
 * -update mySQL table by SQLite table
 */
public class MySQLiteConverter {

    public Connection MySQLconnection = null;
    public Connection SQLiteconnection = null;
    private Statement MySQLstatement = null;
    private Statement SQLitestatement = null;
    private boolean LoadDriverSuccess = false;
    private boolean DBConnected = false;
    private int maxInCom = 0;
    private ArrayList<DataUpdate> dataUpdates = new ArrayList<DataUpdate>();
    private ArrayList<String> listDataUpdate;
    private String message = null;

    public MySQLiteConverter() {

        boolean MysqlSuccess;
        boolean SqliteSuccess;
        listDataUpdate = new ArrayList<String> ();
        //load MySQL driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            MysqlSuccess = true;
        } catch (Exception E) {
            System.err.println("Unable to load MySQL driver");
            E.printStackTrace();
            MysqlSuccess = false;
        }
        //load SQLite driver
        try {
            Class.forName("org.sqlite.JDBC");
            SqliteSuccess = true;
        } catch (Exception E) {
            System.err.println("Unable to load SQLite driver");
            E.printStackTrace();
            SqliteSuccess = false;
        }

        //can we load all driver?
        if (MysqlSuccess && SqliteSuccess) {
            LoadDriverSuccess = true;
            //system.out.println("Load driver success");

        } else {
            System.out.println("Error at MySQLiteConverter's Construtor");
        }
    }
    public void clearListDataUpdate(){
        listDataUpdate = new ArrayList<String>();
    }
    public ArrayList<String> getListDataUpdate(){
        return listDataUpdate;
    }
    
    public boolean openConnection(String mysqlurl, String mysqluser,
            String mysqlpasswd, String sqlitefilepath) {
        //parameter example
        //--MySQLurl
        // "jdbc:mysql://localhost:3306/mySQL[database name]"
        //--SQLiteString
        //"jdbc:sqlite:D:"+File.separator+"test"+File.separator+"test.db"
        boolean mysqlConnected;
        boolean sqliteConnected;

        if (!LoadDriverSuccess) {
            return false;
        } else {
            mysqlurl += "?useUnicode=true&characterEncoding=UTF-8";
            //connect to MySQL server
            try {
                MySQLconnection = DriverManager.getConnection(mysqlurl, mysqluser,
                        mysqlpasswd);
                mysqlConnected = true;
            } catch (SQLException exMySQL) {
                Logger.getLogger(MySQLiteConverter.class.getName()).log(
                        Level.SEVERE, null, exMySQL);
                System.err.println("Connection with MySQL server is unsuccess");
                mysqlConnected = false;
            }

            //connect to SQLite .db file
            try {
                SQLiteconnection = DriverManager.getConnection(sqlitefilepath);
                sqliteConnected = true;
            } catch (SQLException exSQLite) {
                Logger.getLogger(MySQLiteConverter.class.getName()).log(
                        Level.SEVERE, null, exSQLite);
                System.err.println("Connection with sqlite db file is unsuccess");
                sqliteConnected = false;
            }

            //is connected both database?
            if (mysqlConnected && sqliteConnected) {
                DBConnected = true;
                try {
                    MySQLstatement = MySQLconnection.createStatement();
                    SQLitestatement = SQLiteconnection.createStatement();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLiteConverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                DBConnected = false;
            }
        }
        return DBConnected;
    }

    public void closeConntion() {
        if (DBConnected) {
            try {
                SQLitestatement.close();
                SQLiteconnection.close();
                MySQLstatement.close();
                MySQLconnection.close();

            } catch (SQLException ex) {
                System.err.println(ex.toString());
            }
        }
    }
    public String getMessage(){
        return message;
    }
    public String[] mysqlListTableName() {
        try {
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, "%", types);
            rs.last();
            String[] temp = new String[rs.getRow()];
            rs.beforeFirst();
            for (int i = 0; rs.next(); i++) {
                temp[i] = rs.getString(3);
                //String tablename = rs.getString(3);
                //  System.out.println(temp[i]);
            }
            return temp;
        } catch (SQLException ex) {
            Logger.getLogger(MySQLiteConverter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ResultSet mysqlSelectAll(String tablename) {
        if (!DBConnected) {
            return null;
        } else {
            try {
                //     System.out.println("tablename.equalsIgnoreCase(visitepiappoint) :"+tablename.equalsIgnoreCase("visitepiappoint"));
                if (tablename.equalsIgnoreCase("visitepiappoint")) {
                    ResultSet rs = MySQLstatement.executeQuery("select * from " + tablename
                            + " where dateappoint <> '0000-00-00' ");
                    return rs;
                } else {
                    ResultSet rs = MySQLstatement.executeQuery("select * from " + tablename);
                    return rs;
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return null;
            }
        }
    }

    public ResultSet sqliteQuery(String SQl) {
        if (!DBConnected) {
            return null;
        } else {
            try {
                ResultSet rs = SQLitestatement.executeQuery(SQl);
                return rs;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return null;
            }
        }
    }

    public ResultSet sqliteSelectAll(String tablename, String colume, int maxIndevice) {
        if (!DBConnected) {
            return null;
        } else {
            try {
                System.out.println("select * from "
                        + tablename + " where " + colume + " > " + " '"
                        + maxIndevice + "' ");
                
                ResultSet rs = SQLitestatement.executeQuery("select * from "
                        + tablename + " where " + colume + " > " + " '"
                        + maxIndevice + "' ");
                return rs;
            } catch (SQLException ex) {
                System.out.println("sqliteSelectAll Error this "+ex.toString());
                return null;
            }
        }
    }

    public ResultSet sqliteSelectTableInVisit(String tablename) {
        if (!DBConnected) {
            return null;
        } else {
            String temp = "select * from "
                        + tablename + " where visitno in " + "( ";
            for(int i = 0; i < dataUpdates.size(); i++){
                temp +="'"+dataUpdates.get(i).dataOld+"'";
                if(i<dataUpdates.size()-1){
                    temp+=",";
                }
            }
            temp +=")";
            try {
                System.out.println(temp);
                ResultSet rs = SQLitestatement.executeQuery(temp);
                return rs;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return null;
            }
        }
    }

    public ResultSet sqliteSelectAll(String tablename) {
        if (!DBConnected) {
            return null;
        } else {
            try {
                ResultSet rs = SQLitestatement.executeQuery("select * from "
                        + tablename);
                return rs;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return null;
            }
        }
    }

    public boolean sqliteDropTable(String tablename) {
        if (!DBConnected) {
            return false;
        } else {
            try {
                SQLitestatement.execute("DROP TABLE IF EXISTS " + tablename);
                return true;
            } catch (SQLException ex) {
                System.out.println(ex.toString());
                return false;
            }
        }
    }

    public boolean sqliteListTableName() {
        return true;
    }

    public boolean mysqlConvertToSqlite(String tablename)throws SQLException {
        if (!DBConnected) {
            System.err.println("user must connect DB success "
                    + "before use this Method!");
            return false;
        } else {
            message = "กำลังแปลงฐานข้อมูล " + tablename ;
            // initialize sql command to create and insert new table
            String sql_create = "CREATE TABLE IF NOT EXISTS " + tablename
                    + " ( ";
            String sql_insert = "INSERT OR REPLACE INTO " + tablename + " ( ";
            String sql_insert_values = " VALUES ( ";
            //get all record in table
            ResultSet rs = this.mysqlSelectAll(tablename);
            ResultSetMetaData rsmd = rs.getMetaData();

            //check column for parse
            int maxcolumn = rsmd.getColumnCount();
            int[] typearray = new int[maxcolumn + 1];
            String label;
            //loop for each column of result
            for (int i = 1; i <= maxcolumn; i++) {

                //add Column Label
                label = rsmd.getColumnLabel(i);
                sql_create += label;
                sql_insert += label;

                sql_insert_values += "?";
                //add Column type
                int type = rsmd.getColumnType(i);
                switch (type) {
                    case Types.BIGINT:
                    case Types.INTEGER:
                    case Types.SMALLINT:
                    case Types.TINYINT:
                        typearray[i] = SQLiteDataType.INTEGER;
                        sql_create += " INTEGER";
                        break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                    case Types.REAL:
                        typearray[i] = SQLiteDataType.REAL;
                        sql_create += " REAL";
                        break;
                    case Types.NUMERIC:
                    case Types.DATE:
                    case Types.TIMESTAMP:
                    case Types.DECIMAL:
                    case Types.BOOLEAN:
                        typearray[i] = SQLiteDataType.NUMERIC;
                        sql_create += " NUMERIC";
                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.NCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.CLOB:
                        typearray[i] = SQLiteDataType.TEXT;
                        sql_create += " TEXT";
                        break;
                    case Types.BLOB:
                    case Types.BINARY:
                    case Types.LONGVARBINARY:
                        typearray[i] = SQLiteDataType.BLOB;
                        sql_create += " BLOB";
                        break;
                    default:
                        typearray[i] = SQLiteDataType.NULL;
                        sql_create += " NULL";
                        break;
                }
                //add PRIMARY KEY
                if (rsmd.isAutoIncrement(i) && (typearray[i] == SQLiteDataType.INTEGER)) {
                    sql_create += " PRIMARY KEY AUTOINCREMENT";
                }
                //add NOT NULL or Allow
                if (rsmd.isNullable(i) == 0) {
                    sql_create += " NOT NULL";
                }
                //end of resultset or not?
                if (i != maxcolumn) {
                    sql_create += ", ";
                    sql_insert += ", ";
                    sql_insert_values += ", ";
                } else {
                    sql_create += " );";
                    sql_insert += " )";
                    sql_insert_values += " );";
                }
            }
            System.out.println(sql_create);
            SQLitestatement.executeUpdate("drop table if exists " + tablename + ";");
            SQLitestatement.execute(sql_create);

            sql_insert += sql_insert_values;
            PreparedStatement prep = SQLiteconnection.prepareStatement(sql_insert);
            while (rs.next()) {

                for (int i = 1; i <= maxcolumn; i++) {
                    int type = typearray[i];
                    switch (type) {
                        case SQLiteDataType.INTEGER:
                            if (rs.getString(i) != null) {
                                prep.setInt(i, rs.getInt(i));
                            } else {
                                prep.setString(i, rs.getString(i));
                            }
                            break;
                        case SQLiteDataType.REAL:
                            prep.setDouble(i, rs.getDouble(i));
                            break;
                        case SQLiteDataType.BLOB:
                        case SQLiteDataType.NUMERIC:
                        case SQLiteDataType.TEXT:
                        case SQLiteDataType.NULL:
                        default:
                            prep.setString(i, rs.getString(i));
                            break;
                    }
                }

                prep.addBatch();
            }
            SQLiteconnection.setAutoCommit(false);
            prep.executeBatch();
            SQLiteconnection.setAutoCommit(true);
            //System.out.println(sql_insert_values);
            return true;
        }
    }

    public boolean mysqlConvertToSqlite(ResultSet rs)
            throws SQLException {
        if (!DBConnected) {
            System.err.println("must connect DB success "
                    + "before use this Method!");
            return false;
        } else {
            ResultSetMetaData rsmd = rs.getMetaData();
            String table_name = rsmd.getTableName(1);

            // initialize sql command to create and insert new table
            String sql_create = "CREATE TABLE IF NOT EXISTS " + table_name
                    + " ( ";
            String sql_insert = "INSERT OR REPLACE INTO " + table_name + " ( ";
            String sql_insert_values = " VALUES ( ";

            String primaky_add = "PRIMARY KEY (";
            //check column for parse
            int maxcolumn = rsmd.getColumnCount();
            int[] typearray = new int[maxcolumn + 1];
            String label;
            //loop for each column of result
            for (int i = 1; i <= maxcolumn; i++) {

                //add Column Label
                label = rsmd.getColumnLabel(i);
                sql_create += label;
                sql_insert += label;

                sql_insert_values += "?";
                //add Column type
                int type = rsmd.getColumnType(i);
                switch (type) {
                    case Types.BIGINT:
                    case Types.INTEGER:
                    case Types.SMALLINT:
                    case Types.TINYINT:
                        typearray[i] = SQLiteDataType.INTEGER;
                        sql_create += " INTEGER";
                        break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                    case Types.REAL:
                        typearray[i] = SQLiteDataType.REAL;
                        sql_create += " REAL";
                        break;
                    case Types.NUMERIC:
                    case Types.DATE:
                    case Types.TIMESTAMP:
                    case Types.DECIMAL:
                    case Types.BOOLEAN:
                        typearray[i] = SQLiteDataType.NUMERIC;
                        sql_create += " NUMERIC";
                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.NCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.CLOB:
                        typearray[i] = SQLiteDataType.TEXT;
                        sql_create += " TEXT";
                        break;
                    case Types.BLOB:
                    case Types.BINARY:
                    case Types.LONGVARBINARY:
                        typearray[i] = SQLiteDataType.BLOB;
                        sql_create += " BLOB";
                        break;
                    default:
                        typearray[i] = SQLiteDataType.NULL;
                        sql_create += " NULL";
                        break;
                }
                //add PRIMARY KEY
                if (rsmd.isAutoIncrement(i) && (typearray[i] == SQLiteDataType.INTEGER)) {
                    sql_create += " PRIMARY KEY AUTOINCREMENT";
                }
                //add NOT NULL or Allow
                if (rsmd.isNullable(i) == 0) {
                    sql_create += " NOT NULL";
                }else{
                    sql_create += " NULL";
                }

                //end of resultset or not?
                if (i != maxcolumn) {
                    sql_create += ", ";
                    sql_insert += ", ";
                    sql_insert_values += ", ";
                } else {
                    sql_create += " );";
                    sql_insert += " )";
                    sql_insert_values += " );";
                }


            }
            System.out.println(sql_create);
            MainForm.transForm.setTextLogLabel(sql_create);
            SQLitestatement.executeUpdate("drop table if exists " + table_name + ";");
            SQLitestatement.execute(sql_create);
            //MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
            

            sql_insert += sql_insert_values;
            PreparedStatement prep = SQLiteconnection.prepareStatement(sql_insert);
            while (rs.next()) {
                for (int i = 1; i <= maxcolumn; i++) {
                    //  System.out.println("colume :"+i);
                    int type = typearray[i];
                    switch (type) {
                        case SQLiteDataType.INTEGER:
                            if(rs.getString(i) == null){
                               prep.setString(i, null);
                            }else{
                               prep.setInt(i, rs.getInt(i));
                            }

                            break;
                        case SQLiteDataType.REAL:
                            prep.setDouble(i, rs.getDouble(i));
                            break;
                        case SQLiteDataType.BLOB:
                            prep.setString(i, rs.getString(i));
                            break;
                        case SQLiteDataType.NUMERIC:
                            try {
                                if (rs.getString(i) == null) {
                                    prep.setString(i, null);
                                    break;
                                } else {
                                    try {
                                        prep.setString(i, rs.getString(i));
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                        System.out.println("rs.getString(i)" + rs.getString(i));
                                    }
                                }
                            } catch (SQLException eex) {

                                if (rs.getDate(i) == null) {
                                    prep.setDate(i, null);
                                    break;
                                } else {
                                    try {
                                        prep.setDate(i, rs.getDate(i));
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                        System.out.println("rs.getString(i)" + rs.getString(i));
                                    }
                                }

                            } finally {
                                break;
                            }
                        case SQLiteDataType.TEXT:
                            prep.setString(i, rs.getString(i));
                            break;
                        case SQLiteDataType.NULL:
                            prep.setString(i, rs.getString(i));
                            break;
                        default:
                            try {
                                prep.setDate(i, rs.getDate(i));
                            } catch (SQLException ex) {
                                System.out.println("rs.getString(i)" + rs.getString(i));
                            }
                            break;
                    }
                }
                prep.addBatch();
            }
            SQLiteconnection.setAutoCommit(false);
            prep.executeBatch();
            SQLiteconnection.setAutoCommit(true);
            //System.out.println(sql_insert_values);
            return true;
        }
    }

    public void setMaxIncom(int maxCom) {
        this.maxInCom = maxCom;
    }

    public boolean sqliteSpecialSyncToMySQL(String tablename, String colume, int maxInDevice) throws SQLException {
        boolean update =false;
        if (!DBConnected) {
            return false;
        } else {
            message = "กำลังแปลงฐานข้อมูล " + tablename ;
            dataUpdates.clear();
            int tempMaxInCom = maxInCom;
            ResultSet rs = null;
           // String[] primarykey = new String[10];
            String sql_update = "INSERT INTO " + tablename + " (";
            String s = null;
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            ResultSetMetaData rsmd = null;

            //check PrimaryKeys
         //   rs = dbmd.getPrimaryKeys(null, null, tablename);
          //  int key = 1;
//            while (rs.next()) {
//                primarykey[key++] = rs.getString("COLUMN_NAME");
//            }
            //get all data
            rs = sqliteSelectAll(tablename, colume, maxInDevice);
            if(rs == null){
                System.out.println("rs = null");
            }
            rsmd = rs.getMetaData();
            //get columnLabel
            String[] columnname = getColumnLabel(rsmd);
            //get columnCount
            int maxcolumn = rsmd.getColumnCount();
            try{
            while (rs.next()) {
                int indexcolume = 2;
                for (int i = 1; i <= maxcolumn; i++) {
                    sql_update += columnname[i];
                    if (columnname[i].equals(colume)) {
                        indexcolume = i;
                    }
                    if (i < maxcolumn) {
                        sql_update += ", ";
                    } else {
                        sql_update += " ";
                    }
                }
                System.out.println("sql_update"+sql_update);
                sql_update += " ) VALUES ( ";
                for (int i = 1; i <= maxcolumn; i++) {
                    if (i == indexcolume) {
                        sql_update += " '" + (tempMaxInCom += 1) + "' ";
                        try{
                        System.out.println(rs.getInt(2)+""+ tempMaxInCom);
                        dataUpdates.add(new DataUpdate(rs.getInt(2), tempMaxInCom));
                        }catch(SQLException sex){
                            message = "เกิดข้อผิดผลาดในกระบวนการ "+tablename+":"+sex;
                            System.out.println("get Error 621 "+sex);
                        }
                    } else {
                        if (rs.getString(i) != null) {
                            sql_update += " '" + rs.getString(i) + "' ";

                        } else {
                            sql_update += rs.getString(i);
                        }
                    }
                    if (i < maxcolumn) {
                        sql_update += ", ";
                    } else {
                        sql_update += " ";
                    }

                }
                sql_update += " ); ";

                System.out.println("Maxincom :" + maxInCom + "tempMaxincom :" + tempMaxInCom + "maxinDevice :" + maxInDevice);
                System.out.println(sql_update);
                MySQLstatement.executeUpdate(sql_update);
                listDataUpdate.add(sql_update);
                message = sql_update;
                sql_update = "INSERT INTO " + tablename + " (";
                update = true;
            }


        }catch(SQLException sqlEx){
            System.out.println(sqlEx);
            return false;
        }
        }
        return update;
    }

    public boolean sqliteSpecialSyncByVisitNumberToMySQL(String tablename)throws SQLException{
        boolean update = false;
        if (!DBConnected) {
            return false;
        } else {
        for(int i = 0;i < dataUpdates.size();i++){
            System.out.println("dateupdate index>"+i+"<New>"
                    +dataUpdates.get(i).dataNew
                    +"<Old>"
                    +dataUpdates.get(i).dataOld);
        }
        message = "กำลังแปลงข้อมูล "+tablename;
            showDataUpdate();
            ResultSet rs = null;
            String[] primarykey = new String[10];
            String sql_update = "INSERT INTO " + tablename + " (";
            String s = null;
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            ResultSetMetaData rsmd = null;

            //check PrimaryKeys
            rs = dbmd.getPrimaryKeys(null, null, tablename);
            int key = 1;
            while (rs.next()) {
                primarykey[key++] = rs.getString("COLUMN_NAME");
            }
            //get all data
            rs = sqliteSelectTableInVisit(tablename);
            if(rs == null){
                System.out.println(tablename+"Nodata update");
                return false;
            }
            rsmd = rs.getMetaData();
            //get columnLabel
            String[] columnname = getColumnLabel(rsmd);
            //get columnCount
            int maxcolumn = rsmd.getColumnCount();

            while (rs.next()) {

                int indexcolume = 2;
                for (int i = 1; i <= maxcolumn; i++) {
                    sql_update += columnname[i];
                    if (i < maxcolumn) {
                        sql_update += ", ";
                    } else {
                        sql_update += " ";
                    }
                }
                sql_update += " ) VALUES ( ";
                for (int i = 1; i <= maxcolumn; i++) {
                    if (i == indexcolume) {
                        sql_update += " '" + getNewDataUpdate(rs.getInt(i)) + "' ";
                    } else {
                        if (rs.getString(i) != null) {
                            sql_update += " '" + rs.getString(i) + "' ";

                        } else {
                            sql_update += rs.getString(i);
                        }
                    }
                    if (i < maxcolumn) {
                        sql_update += ", ";
                    } else {
                        sql_update += " ";
                    }

                }
                sql_update += " ); ";

                System.out.println(sql_update);
                MySQLstatement.executeUpdate(sql_update);
                listDataUpdate.add(sql_update);
                message = sql_update;
                sql_update = "INSERT INTO " + tablename + " (";
                update= true;
            }
        }
       // dataUpdates.clear();
        return update;
    }

    public int getNewDataUpdate(int dataOld){
        for(int i = 0;i < dataUpdates.size();i++){
            if(dataOld == dataUpdates.get(i).dataOld){
                return dataUpdates.get(i).dataNew;
            }
        }
        return dataOld;
    }
    public void showDataUpdate(){
        for (DataUpdate dataUpdate : dataUpdates) {
            System.out.println("new "+dataUpdate.dataNew
                    +":"+"old "+dataUpdate.dataOld);
        }
    }
    public boolean sqliteSyncToMySQL(String tablename) throws SQLException {
        if (!DBConnected) {
            return false;
        } else {
            message = "กำลังแปลงข้อมูล "+tablename;
            ResultSet rs = null;
            String[] primarykey = new String[10];
            String sql_update = "UPDATE " + tablename + " SET ";
            String s = null;
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            ResultSetMetaData rsmd = null;

            //check PrimaryKeys
            rs = dbmd.getPrimaryKeys(null, null, tablename);
            int key = 1;
            while (rs.next()) {
                primarykey[key++] = rs.getString("COLUMN_NAME");
            }
            //get all data
            rs = sqliteSelectAll(tablename);
            rsmd = rs.getMetaData();
            //get columnLabel
            String[] columnname = getColumnLabel(rsmd);
            //get columnCount
            int maxcolumn = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= maxcolumn; i++) {
                    if (rs.getString(i) != null) {
                        sql_update += columnname[i] + " = '"
                                + rs.getString(i) + "'";

                    } else {
                        sql_update += columnname[i] + " = "
                                + rs.getString(i);
                    }

                    if (i < maxcolumn) {
                        sql_update += ", ";
                    } else {
                        sql_update += " ";
                    }
                }
                sql_update += " WHERE ";
                for (int i = 1; i < key; i++) {
                    sql_update += primarykey[i] + " = '"
                            + rs.getString(primarykey[i]) + "'";

                    if (i < key - 1) {
                        sql_update += " AND ";
                    } else {
                        sql_update += ";";
                    }
                }

                System.out.println(sql_update);
                try {
                    MySQLstatement.executeUpdate(sql_update);
                    message = sql_update;
                    listDataUpdate.add(sql_update);                    
                } catch (Exception ex) {
                    message = "เกิดข้อผิดผลาด "+tablename+":"+ex;
                    System.out.println("Exception :" + ex);
                } catch (Error eex) {
                    message = "เกิดข้อผิดผลาด "+tablename+":"+eex;
                    System.out.println("Error :" + eex);
                }
                sql_update = "UPDATE " + tablename + " SET ";
            }
        }
        return true;
    }

    public boolean sqliteSyncUpdateToMySQL(String tablename) throws SQLException {
         boolean update =false;
        if (!DBConnected) {
            return false;
        } else {
            message = "กำลังแปลงข้อมูล "+tablename;
            ResultSet rs = null;
            String[] primarykey = new String[10];
            String sql_update = "UPDATE " + tablename + " SET ";
            String s = null;
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            ResultSetMetaData rsmd = null;

            //check PrimaryKeys
            rs = dbmd.getPrimaryKeys(null, null, tablename);
            int key = 1;
            while (rs.next()) {
                primarykey[key++] = rs.getString("COLUMN_NAME");
            }
            //get all data
            rs = sqliteSelectAll(tablename);
            rsmd = rs.getMetaData();
            //get columnLabel
            String[] columnname = getColumnLabel(rsmd);
            //get columnCount
            int maxcolumn = rsmd.getColumnCount();

            while (rs.next()) {
                String temp;
                String tempSQliteUpdate = null;
                String tempSQLUpdat = null;
                String pid = null;

                if (tablename.equals("house")) {
                    System.out.println("house");
                    pid = rs.getString(2);
                    temp = "select dateupdate from "
                            + tablename + " where hcode = " + " '"
                            + rs.getString(2) + "' ";
                    System.out.println(temp);
                    tempSQliteUpdate = rs.getString(maxcolumn);

                } else {
                    temp = "select dateupdate from "
                            + tablename + " where pid = " + " '"
                            + rs.getString(2) + "' ";
                    if (tablename.equals("person")) {
                        tempSQliteUpdate = rs.getString("dateupdate");
                        System.out.println(tempSQliteUpdate);
                        pid = rs.getString(2);
                    } else {
                        if (tablename.equals("personbehavior")) {
                            System.out.println("personbehavior");
                            tempSQliteUpdate = rs.getString("dateupdate");
                            System.out.println("tempSQliteUpdate "+tempSQliteUpdate);
                            pid = rs.getString(2);
                        }
                    }

                }
                System.out.println("tempSQL"+temp);
                ResultSet rSQL = MySQLstatement.executeQuery(temp);
                rSQL.first();
                 tempSQLUpdat = rSQL.getString("dateupdate");

//                System.out.println("PID :" + pid + "tempSQLUpdat :" + tempSQLUpdat);
//                System.out.println("tempSQliteUpdate :" + tempSQliteUpdate);
                DateTime dateTimeSQl = new DateTime();
                DateTime dateTimeSQLite = new DateTime();
                try{
                    System.out.println("tempSQLUpdat"+tempSQLUpdat);
                    dateTimeSQl.createDateTime(tempSQLUpdat);
                }catch(NullPointerException ex){
                    System.out.println(ex + " : "+tempSQLUpdat);
                    return false;
                }
                try{
                dateTimeSQLite.createDateTime(tempSQliteUpdate);
                }catch(NullPointerException ex){
                    System.out.println(ex + " : "+tempSQliteUpdate);
                    return false;
                }
                int more = dateTimeSQl.qualsTime(dateTimeSQLite);
//                switch(more){
//                    case -1:
//                         System.out.println("update");
//                        break;
//                    case 1:
//                         System.out.println("No update");
//                        break;
//                    case 0:
//                        System.out.println("EQUALS");
//                        break;
//                }

                if (more == -1) {
                    update = true;
                    for (int i = 1; i <= maxcolumn; i++) {
                        if (rs.getString(i) != null) {
                            sql_update += columnname[i] + " = '"
                                    + rs.getString(i) + "'";

                        } else {
                            sql_update += columnname[i] + " = "
                                    + rs.getString(i);
                        }

                        if (i < maxcolumn) {
                            sql_update += ", ";
                        } else {
                            sql_update += " ";
                        }
                    }
                    sql_update += " WHERE ";
                    for (int i = 1; i < key; i++) {
                        sql_update += primarykey[i] + " = '"
                                + rs.getString(primarykey[i]) + "'";

                        if (i < key - 1) {
                            sql_update += " AND ";
                        } else {
                            sql_update += ";";
                        }
                    }

                    System.out.println(sql_update);
                    try {
                        
                        MySQLstatement.executeUpdate(sql_update);
                        listDataUpdate.add(sql_update);
                        message = sql_update;
                    } catch (Exception ex) {
                        message = "เกิดข้อผิดผลาด "+tablename+":"+ex;
                        System.out.println("Exception :" + ex);
                    } catch (Error eex) {
                        message = "เกิดข้อผิดผลาด "+tablename+":"+eex;
                        System.out.println("Error :" + eex);
                    }
                    sql_update = "UPDATE " + tablename + " SET ";
                }
            }
        }
        return update;
    }

    public boolean sqliteSyncUpdateORInsertToMySQL(String tablename) throws SQLException {
        boolean update = false;
        if (!DBConnected) {
            return false;
        } else {
            message = "กำลังแปลงข้อมูล "+tablename;
            ResultSet rs = null;
            String[] primarykey = new String[10];
            String sql_update = "UPDATE " + tablename + " SET ";
            String sql_insert = "INSERT INTO " + tablename + " (";
            DatabaseMetaData dbmd = MySQLconnection.getMetaData();
            ResultSetMetaData rsmd = null;
             
            //check PrimaryKeys
            rs = dbmd.getPrimaryKeys(null, null, tablename);
            int key = 1;
            while (rs.next()) {
                primarykey[key++] = rs.getString("COLUMN_NAME");
            }
            //get all data
            rs = sqliteSelectAll(tablename);
            rsmd = rs.getMetaData();
            //get columnLabel
            String[] columnname = getColumnLabel(rsmd);
            //get columnCount
            int maxcolumn = rsmd.getColumnCount();

            while (rs.next()) {
                String temp;
                String tempSQliteUpdate = null;
                String tempSQLUpdat = null;
                String pid = null;

                if (tablename.equals("house")) {
                    System.out.println("house");
                    pid = rs.getString(2);
                    temp = "select dateupdate from "
                            + tablename + " where hcode = " + " '"
                            + rs.getString(2) + "' ";
                    System.out.println(temp);
                    tempSQliteUpdate = rs.getString(maxcolumn);

                } else {
                    temp = "select dateupdate from "
                            + tablename + " where pid = " + " '"
                            + rs.getString(2) + "' ";
                    if (tablename.equals("person")) {
                        tempSQliteUpdate = rs.getString("dateupdate");
                        System.out.println(tempSQliteUpdate);
                        pid = rs.getString(2);
                    } else {
                        if (tablename.equals("personbehavior")) {
                            System.out.println("personbehavior");
                            tempSQliteUpdate = rs.getString("dateupdate");
                            System.out.println("tempSQliteUpdate "+tempSQliteUpdate);
                            pid = rs.getString(2);
                        }
                    }

                }
                System.out.println("tempSQL"+temp);

                ResultSet rSQL = MySQLstatement.executeQuery(temp);
                if(rSQL.first()){
                    System.out.println(tablename+" Data OK Update Data");
                    tempSQLUpdat = rSQL.getString("dateupdate");
                
//                System.out.println("PID :" + pid + "tempSQLUpdat :" + tempSQLUpdat);
//                System.out.println("tempSQliteUpdate :" + tempSQliteUpdate);
                DateTime dateTimeSQl = new DateTime();
                DateTime dateTimeSQLite = new DateTime();
                try{
                    System.out.println("tempSQLUpdat"+tempSQLUpdat);
                    dateTimeSQl.createDateTime(tempSQLUpdat);
                }catch(NullPointerException ex){
                    System.out.println(ex + " : "+tempSQLUpdat);
                    return false;
                }
                try{
                dateTimeSQLite.createDateTime(tempSQliteUpdate);
                }catch(NullPointerException ex){
                    System.out.println(ex + " : "+tempSQliteUpdate);
                    return false;
                }
                int more = dateTimeSQl.qualsTime(dateTimeSQLite);
//                switch(more){
//                    case -1:
//                         System.out.println("update");
//                        break;
//                    case 1:
//                         System.out.println("No update");
//                        break;
//                    case 0:
//                        System.out.println("EQUALS");
//                        break;
//                
                if (more == -1) {
                    update = true;
                    for (int i = 1; i <= maxcolumn; i++) {
                        if (rs.getString(i) != null) {
                            sql_update += columnname[i] + " = '"
                                    + rs.getString(i) + "'";

                        } else {
                            sql_update += columnname[i] + " = "
                                    + rs.getString(i);
                        }

                        if (i < maxcolumn) {
                            sql_update += ", ";
                        } else {
                            sql_update += " ";
                        }
                    }
                    sql_update += " WHERE ";
                    for (int i = 1; i < key; i++) {
                        sql_update += primarykey[i] + " = '"
                                + rs.getString(primarykey[i]) + "'";

                        if (i < key - 1) {
                            sql_update += " AND ";
                        } else {
                            sql_update += ";";
                        }
                    }

                    System.out.println(sql_update);
                    try {
                        MySQLstatement.executeUpdate(sql_update);
                        listDataUpdate.add(sql_update);
                        message = sql_insert;
                    } catch (Exception ex) {
                        message = "เกิดข้อผิดผลาด "+tablename+":"+ex+":"+sql_update;
                        System.out.println("Exception :" + ex);
                    } catch (Error eex) {
                         message = "เกิดข้อผิดผลาด "+tablename+":"+eex+":"+sql_update;
                        System.out.println("Error :" + eex);
                    }
                    sql_update = "UPDATE " + tablename + " SET ";
                }
                }else{
                    System.out.println(tablename+"No!!! Data OK Insert Data");
                int indexcolume = 2;
                for (int i = 1; i <= maxcolumn; i++) {
                    sql_insert += columnname[i];
                    if (i < maxcolumn) {
                        sql_insert += ", ";
                    } else {
                        sql_insert += " ";
                    }
                }
                System.out.println("sql_insert"+sql_insert);
                sql_insert += " ) VALUES ( ";
                for (int i = 1; i <= maxcolumn; i++) {
                        if (rs.getString(i) != null) {
                            sql_insert += " '" + rs.getString(i) + "' ";

                        } else {
                            sql_insert += rs.getString(i);

                    }
                    if (i < maxcolumn) {
                        sql_insert += ", ";
                    } else {
                        sql_insert += " ";
                    }

                }
                sql_insert += " ); ";
                System.out.println(sql_insert);

                MySQLstatement.executeUpdate(sql_insert);
                listDataUpdate.add(sql_update);
                message = sql_insert;
                sql_insert = "INSERT INTO " + tablename + " (";
                }
            }

        }
        return update;
    }

    //getter and setter method
    public String[] getColumnLabel(ResultSetMetaData rsmd) throws SQLException {
        int maxcolumn = rsmd.getColumnCount();
        String[] label = new String[maxcolumn + 1];

        for (int i = 1; i <= maxcolumn; i++) {
            label[i] = rsmd.getColumnLabel(i);
        }
        return label;
    }

    public int[] getColumnType(ResultSetMetaData rsmd) throws SQLException {
        int maxcolumn = rsmd.getColumnCount();
        int[] type = new int[maxcolumn + 1];

        for (int i = 1; i <= maxcolumn; i++) {
            type[i] = rsmd.getColumnType(i);
        }
        return type;
    }

    public int[] getColumnDisplaySize(ResultSetMetaData rsmd) throws SQLException {
        int maxcolumn = rsmd.getColumnCount();
        int[] displaysize = new int[maxcolumn + 1];

        for (int i = 1; i <= maxcolumn; i++) {
            displaysize[i] = rsmd.getColumnDisplaySize(i);
        }
        return displaysize;
    }

    public boolean isDatabaseConnected() {
        return DBConnected;
    }

    public boolean isDriverLoadSuccess() {
        return LoadDriverSuccess;
    }

    public Connection getMySQLConnection() {
        return MySQLconnection;
    }

    public Connection getSQLiteConnection() {
        return SQLiteconnection;
    }

    public Statement getMySQLsStatement() {
        return MySQLstatement;
    }

    public Statement getSQLiteStatement() {
        return SQLitestatement;
    }

    public Statement getUpdatableSQLiteStatement() throws SQLException{
        Statement state = SQLiteconnection.createStatement();
        state = SQLiteconnection.createStatement(
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE);
        return state;
    }

    public static class SQLiteDataType {

        public static final int NULL = 0;
        public static final int INTEGER = 1;
        public static final int REAL = 2;
        public static final int TEXT = 3;
        public static final int BLOB = 4;
        public static final int NUMERIC = 5;
    }

    public boolean insertDataToTable(ResultSet rs) throws SQLException{
        if (!DBConnected) {
            System.err.println("must connect DB success "
                    + "before use this Method!");
            return false;
        } else {
            ResultSetMetaData rsmd = rs.getMetaData();
            String table_name = rsmd.getTableName(1);

            // initialize sql command to create and insert new table
            String sql_insert = "INSERT OR REPLACE INTO " + table_name + " ( ";
            String sql_insert_values = " VALUES ( ";

            //check column for parse
            int maxcolumn = rsmd.getColumnCount();
            int[] typearray = new int[maxcolumn + 1];
            String label;
            //loop for each column of result
          for (int i = 1; i <= maxcolumn; i++) {

                //add Column Label
                label = rsmd.getColumnLabel(i);

                sql_insert += label;

                sql_insert_values += "?";
                //add Column type
                int type = rsmd.getColumnType(i);
                switch (type) {
                    case Types.BIGINT:
                    case Types.INTEGER:
                    case Types.SMALLINT:
                    case Types.TINYINT:
                        typearray[i] = SQLiteDataType.INTEGER;

                        break;
                    case Types.FLOAT:
                    case Types.DOUBLE:
                    case Types.REAL:
                        typearray[i] = SQLiteDataType.REAL;

                        break;
                    case Types.NUMERIC:
                    case Types.DATE:
                    case Types.TIMESTAMP:
                    case Types.DECIMAL:
                    case Types.BOOLEAN:
                        typearray[i] = SQLiteDataType.NUMERIC;

                        break;
                    case Types.CHAR:
                    case Types.VARCHAR:
                    case Types.NCHAR:
                    case Types.NVARCHAR:
                    case Types.LONGVARCHAR:
                    case Types.LONGNVARCHAR:
                    case Types.CLOB:
                        typearray[i] = SQLiteDataType.TEXT;

                        break;
                    case Types.BLOB:
                    case Types.BINARY:
                    case Types.LONGVARBINARY:
                        typearray[i] = SQLiteDataType.BLOB;

                        break;
                    default:
                        typearray[i] = SQLiteDataType.NULL;

                        break;
                }
                //end of resultset or not?
                if (i != maxcolumn) {

                    sql_insert += ", ";
                    sql_insert_values += ", ";
                } else {

                    sql_insert += " )";
                    sql_insert_values += " );";
                }
            }

            sql_insert += sql_insert_values;
            PreparedStatement prep = SQLiteconnection.prepareStatement(sql_insert);
            while (rs.next()) {
                for (int i = 1; i <= maxcolumn; i++) {
                    //  System.out.println("colume :"+i);
                    int type = typearray[i];
                    switch (type) {
                        case SQLiteDataType.INTEGER:
                            if(rs.getString(i) == null){
                               prep.setString(i, null);
                            }else{
                               prep.setInt(i, rs.getInt(i));
                            }

                            break;
                        case SQLiteDataType.REAL:
                            prep.setDouble(i, rs.getDouble(i));
                            break;
                        case SQLiteDataType.BLOB:
                            prep.setString(i, rs.getString(i));
                            break;
                        case SQLiteDataType.NUMERIC:
                            try {
                                if (rs.getString(i) == null) {
                                    prep.setString(i, null);
                                    break;
                                } else {
                                    try {
                                        prep.setString(i, rs.getString(i));
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                        System.out.println("rs.getString(i)" + rs.getString(i));
                                    }
                                }
                            } catch (SQLException eex) {

                                if (rs.getDate(i) == null) {
                                    prep.setDate(i, null);
                                    break;
                                } else {
                                    try {
                                        prep.setDate(i, rs.getDate(i));
                                    } catch (SQLException ex) {
                                        ex.printStackTrace();
                                        System.out.println("rs.getString(i)" + rs.getString(i));
                                    }
                                }

                            } finally {
                                break;
                            }
                        case SQLiteDataType.TEXT:
                            prep.setString(i, rs.getString(i));
                            break;
                        case SQLiteDataType.NULL:
                            prep.setString(i, rs.getString(i));
                            break;
                        default:
                            try {
                                prep.setDate(i, rs.getDate(i));
                            } catch (SQLException ex) {
                                System.out.println("rs.getString(i)" + rs.getString(i));
                            }
                            break;
                    }
                }
                
                prep.addBatch();
            }
            SQLiteconnection.setAutoCommit(false);
            prep.executeBatch();
            SQLiteconnection.setAutoCommit(true);
            //System.out.println(sql_insert_values);
            return true;
        }
    }
}
