package ConvertSQLiteToSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FfcSfCounselingSignatureHandler {
    
    private ConnectDatabase.ConnectSQLite sqliteConnection;
    private int updateCount = 0;
    private String tablename = "ffc_sf_counseling_signature";
    
    public FfcSfCounselingSignatureHandler(ConnectDatabase.ConnectSQLite sqliteConnection) {
        this.sqliteConnection = sqliteConnection;
    }
    
    public int updateFfcSfCounselingSignature(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        this.sqliteConnection = sqliteConnection;
        if (!checkAndCreateTable()) {
            System.out.println("Cannot proceed with " + this.tablename + " update - table creation failed.");
            return 0;
        }
        return this.updateCheckDateupdate(sqliteConnection, this.tablename, lastUpdate);
    }
    
    public int checkUpdateCount(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        if (!checkAndCreateTable()) return 0;
        
        int updateCount = 0;
        Timestamp ts1 = Timestamp.valueOf(lastUpdate);
        
        try {
            ResultSet rs = sqliteConnection.getResultSet("SELECT dateupdate FROM " + tablename);
            while (rs.next()) {
                if (rs.getString("dateupdate") != null) {
                    Timestamp ts2 = Timestamp.valueOf(rs.getString("dateupdate"));
                    if (ts1.compareTo(ts2) < 0) updateCount++;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Table " + tablename + " not found in SQLite database: " + ex.getMessage());
            return 0;
        }
        return updateCount;
    }
    
    public boolean updateForNewVisit(ConnectDatabase.ConnectSQLite sqliteConnection, String visitInsert, int visitMaxNew) throws SQLException {
        if (!checkAndCreateTable()) return false;
        return this.insertOtherVisit(sqliteConnection, tablename, visitMaxNew, visitInsert);
    }
    
    private boolean insertOtherVisit(ConnectDatabase.ConnectSQLite sqliteConnection, String tableName, int visitMaxNew, String visitInsert) throws SQLException {
        try {
            String query = "SELECT * FROM " + tableName + " WHERE visitno = " + visitInsert;
            ResultSet rs = sqliteConnection.getResultSet(query);
            Statement stmt = Service.Service.connectionSQL.connection.createStatement();
            boolean result = false;

            if (rs.next()) {
                ResultSet rss = sqliteConnection.getResultSet(query);
                while (rss.next()) {
                    String insertData1 = "INSERT INTO " + tableName + " (";
                    String insertData2 = " VALUES (";
                    ResultSetMetaData rsmd = rss.getMetaData();

                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (i != rsmd.getColumnCount()) {
                            insertData1 += rsmd.getColumnLabel(i) + ",";
                            if (rss.getString(i) != null) {
                                if (rsmd.getColumnLabel(i).equals("visitno")) {
                                    insertData2 += "'" + visitMaxNew + "',";
                                } else {
                                    insertData2 += "'" + rss.getString(i) + "',";
                                }
                            } else {
                                insertData2 += rss.getString(i) + ",";
                            }
                        } else {
                            if (rss.getString(i) != null) {
                                insertData2 += "'" + rss.getString(i) + "')";
                            } else {
                                insertData2 += rss.getString(i) + ")";
                            }
                            insertData1 += rsmd.getColumnLabel(i) + ")";
                        }
                    }
                    updateCount++;
                    System.out.println("Insert " + this.tablename + ": " + insertData1 + insertData2);
                    stmt.executeUpdate(insertData1 + insertData2);
                }
                rss.close();
                rs.close();
                result = true;
            }
            stmt.close();
            return result;
        } catch (SQLException ex) {
            System.out.println("Error accessing " + tableName + " in SQLite: " + ex.getMessage());
            return false;
        }
    }
    
    private int updateCheckDateupdate(ConnectDatabase.ConnectSQLite sqliteConnection, String tablename, String lastUpdate) throws SQLException {
        int count = 0;
        String sqliteQuery = "SELECT * FROM " + tablename + " WHERE dateupdate > '" + lastUpdate + "'";
        ResultSet rs = sqliteConnection.getResultSet(sqliteQuery);

        while (rs.next()) {
            ArrayList<ResultSet> arrayRs = new ArrayList<>();
            arrayRs.add(rs);
            String Query = "SELECT * FROM " + tablename + getQueryWhereCondition(tablename, rs);
            ResultSet rss = Service.Service.connectionSQL.getResultSet(Query);

            if (rss.next()) {
                System.out.println("Update " + this.tablename + " data");
                this.updateDataOneRow(tablename, arrayRs);
            } else {
                System.out.println("Insert " + this.tablename + " data");
                this.insertData(tablename, arrayRs);
            }
            count++;
        }
        return count;
    }
    
    private String getQueryWhereCondition(String tableName, ResultSet rs) throws SQLException {
        if (this.tablename.equals(tableName)) {
            return " WHERE visitno ='" + rs.getString("visitno") 
                   + "' AND person_id = '" + rs.getString("person_id") + "'";
        }
        return "";
    }
    
    private void insertData(String tableName, ArrayList<ResultSet> dataInsert) throws SQLException {
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        for (ResultSet data : dataInsert) {
            String insertQuery1 = "INSERT INTO " + tableName + " (";
            String insertQuery2 = " VALUES (";
            ResultSetMetaData rsmd = data.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            
            for (int i = 1; i <= ColumnCount; i++) {
                insertQuery1 += rsmd.getColumnLabel(i);
                if (data.getString(i) != null) {
                    insertQuery2 += "'" + data.getString(i) + "'";
                } else {
                    insertQuery2 += data.getString(i);
                }
                if (i < ColumnCount) {
                    insertQuery1 += ",";
                    insertQuery2 += ",";
                } else {
                    insertQuery1 += ")";
                    insertQuery2 += ")";
                    break;
                }
            }
            System.out.println("Insert " + this.tablename + ": " + insertQuery1 + insertQuery2);
            stm.executeUpdate(insertQuery1 + insertQuery2);
        }
    }
    
    private void updateDataOneRow(String tableName, ArrayList<ResultSet> data) throws SQLException {
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        for (ResultSet dataUpdate : data) {
            ResultSetMetaData rsmd = dataUpdate.getMetaData();
            int ColumnCount = rsmd.getColumnCount();

            String updateQuery = "UPDATE " + tableName + " SET ";
            for (int i = 1; i <= ColumnCount; i++) {
                updateQuery += rsmd.getColumnLabel(i) + " = ";
                if (dataUpdate.getString(i) != null) {
                    updateQuery += "'" + dataUpdate.getString(i) + "'";
                } else {
                    updateQuery += dataUpdate.getString(i);
                }
                if (i < ColumnCount) {
                    updateQuery += ",";
                } else {
                    updateQuery += this.getQueryWhereCondition(tableName, dataUpdate);
                    break;
                }
            }
            System.out.println("Update " + this.tablename + ": " + updateQuery);
            stm.addBatch(updateQuery);
        }
        stm.executeBatch();
    }
    
    public boolean checkAndCreateTable() throws SQLException {
        try {
            String checkTableQuery = "SELECT COUNT(*) AS table_count FROM information_schema.tables "
                    + "WHERE table_schema = DATABASE() AND table_name = '" + this.tablename + "'";

            ResultSet rs = Service.Service.connectionSQL.getResultSet(checkTableQuery);
            int tableCount = 0;
            if (rs.next()) {
                tableCount = rs.getInt("table_count");
            }
            rs.close();

            if (tableCount == 0) {
                System.out.println("Table " + this.tablename + " not found. Creating table...");
                return createTable();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfCounselingSignatureHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private boolean createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE `ffc_sf_counseling_signature` ("
                + "`id` int(11) NOT NULL auto_increment,"
                + "`visitno` varchar(20) NOT NULL,"
                + "`person_id` varchar(20) NOT NULL,"
                + "`counseling_type` int(11) NOT NULL,"
                + "`detail` text DEFAULT NULL,"
                + "`referral_detail` text DEFAULT NULL,"
                + "`patient_signature` longblob DEFAULT NULL,"
                + "`provider_signature` longblob DEFAULT NULL,"
                + "`created_by` varchar(50) DEFAULT NULL,"
                + "`created_date` datetime DEFAULT NULL,"
                + "`updated_by` varchar(50) DEFAULT NULL,"
                + "`updated_date` datetime DEFAULT NULL,"
                + "`pcucode` varchar(10) DEFAULT NULL,"
                + "`update_status` varchar(20) DEFAULT NULL,"
                + "`dateupdate` datetime DEFAULT NULL,"
                + "PRIMARY KEY (`id`),"
                + "INDEX `idx_visitno` (`visitno`),"
                + "INDEX `idx_person_id` (`person_id`),"
                + "INDEX `idx_pcucode` (`pcucode`)"
                + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

        try {
            Statement stmt = Service.Service.connectionSQL.connection.createStatement();
            stmt.executeUpdate(createTableSQL);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfCounselingSignatureHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int getUpdateCount() { return this.updateCount; }
    public void resetUpdateCount() { this.updateCount = 0; }
}