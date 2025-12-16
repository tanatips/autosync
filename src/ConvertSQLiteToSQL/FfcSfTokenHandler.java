package ConvertSQLiteToSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FfcSfTokenHandler {
    
    private ConnectDatabase.ConnectSQLite sqliteConnection;
    private int updateCount = 0;
    
    public FfcSfTokenHandler(ConnectDatabase.ConnectSQLite sqliteConnection) {
        this.sqliteConnection = sqliteConnection;
    }
    
    public int updateFfcSfToken(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        this.sqliteConnection = sqliteConnection;
        if (!checkAndCreateTable()) {
            System.out.println("Cannot proceed with ffc_sf_token update - table creation failed.");
            return 0;
        }
        return this.updateCheckDateupdate(sqliteConnection, "ffc_sf_token", lastUpdate);
    }
    
    public int checkUpdateCount(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot check ffc_sf_token updates - table creation failed.");
            return 0;
        }

        int updateCount = 0;
        System.out.println("Last Update: " + lastUpdate);
        Timestamp ts1 = Timestamp.valueOf(lastUpdate);
        Timestamp ts2;

        try {
            ResultSet rs = sqliteConnection.getResultSet("SELECT updated_date FROM ffc_sf_token");
            while (rs.next()) {
                if (rs.getString("updated_date") != null) {
                    ts2 = Timestamp.valueOf(rs.getString("updated_date"));
                    if (ts1.compareTo(ts2) < 0) {
                        updateCount++;
                    }
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Table ffc_sf_token not found in SQLite database: " + ex.getMessage());
            return 0;
        }

        System.out.println("ffc_sf_token Update Count : " + updateCount);
        return updateCount;
    }
    
    private int updateCheckDateupdate(ConnectDatabase.ConnectSQLite sqliteConnection, String tablename, String lastUpdate) throws SQLException {
        int count = 0;
        try {
            String truncateQuery = "TRUNCATE TABLE " + tablename;
            Statement truncateStmt = Service.Service.connectionSQL.connection.createStatement();
            truncateStmt.executeUpdate(truncateQuery);
            truncateStmt.close();
            System.out.println("Truncated all data from " + tablename);
        } catch (SQLException ex) {
            System.out.println("Error truncating table " + tablename + ": " + ex.getMessage());
            // หากใช้ TRUNCATE ไม่ได้ ให้ลอง DELETE แทน
            try {
                String deleteQuery = "DELETE FROM " + tablename;
                Statement deleteStmt = Service.Service.connectionSQL.connection.createStatement();
                int deletedRows = deleteStmt.executeUpdate(deleteQuery);
                deleteStmt.close();
                System.out.println("Deleted " + deletedRows + " rows from " + tablename + " (using DELETE instead of TRUNCATE)");
            } catch (SQLException ex2) {
                System.out.println("Error deleting data from " + tablename + ": " + ex2.getMessage());
                throw ex2;
            }
        }
        
        // Query ข้อมูลทั้งหมดจาก SQLite
            String sqliteQuery = "SELECT * FROM " + tablename;
            ResultSet rs = sqliteConnection.getResultSet(sqliteQuery);

            // Insert ข้อมูลใหม่ทั้งหมด
            while (rs.next()) {
                ArrayList<ResultSet> arrayRs = new ArrayList<>();
                arrayRs.add(rs);

                System.out.println("Insert " + tablename + " data (row " + (count + 1) + ")");
                this.insertData(tablename, arrayRs);
                count++;
            }

            rs.close();
            System.out.println("Total " + count + " records inserted into " + tablename);
          return count;
    }
    
    private String getQueryWhereCondition(String tableName, ResultSet rs) throws SQLException {
        String value = "";
        if ("ffc_sf_token".equals(tableName)) {
            value = " WHERE id = " + rs.getInt("id");
        }
        return value;
    }
    
    private void insertData(String tableName, ArrayList<ResultSet> dataInsert) throws SQLException {
        Statement stm = Service.Service.connectionSQL.connection.createStatement();

        for (ResultSet data : dataInsert) {
            String insertQuery1 = "INSERT INTO " + tableName + " (";
            String insertQuery2 = " VALUES (";
            ResultSetMetaData rsmd = data.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            
            for (int i = 1; i <= ColumnCount; i++) {
                String columnName = rsmd.getColumnLabel(i);
                
                // Skip auto increment id field for insert
                if ("id".equals(columnName)) {
                    continue;
                }
                
                insertQuery1 += columnName;

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
            
            System.out.println("Insert ffc_sf_token: " + insertQuery1 + insertQuery2);
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
                String columnName = rsmd.getColumnLabel(i);
                
                // Skip id field for update SET clause
                if ("id".equals(columnName)) {
                    continue;
                }
                
                updateQuery += columnName + " = ";

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

            System.out.println("Update ffc_sf_token: " + updateQuery);
            stm.addBatch(updateQuery);
        }

        stm.executeBatch();
    }
    
    public boolean checkAndCreateTable() throws SQLException {
        try {
            String checkTableQuery = "SELECT COUNT(*) AS table_count FROM information_schema.tables "
                    + "WHERE table_schema = DATABASE() AND table_name = 'ffc_sf_token'";

            ResultSet rs = Service.Service.connectionSQL.getResultSet(checkTableQuery);
            int tableCount = 0;
            if (rs.next()) {
                tableCount = rs.getInt("table_count");
            }
            rs.close();

            if (tableCount == 0) {
                System.out.println("Table ffc_sf_token not found. Creating table...");
                if (createTable()) {
                    System.out.println("Table ffc_sf_token created successfully.");
                    return true;
                } else {
                    System.out.println("Failed to create table ffc_sf_token.");
                    return false;
                }
            } else {
                System.out.println("Table ffc_sf_token already exists.");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfTokenHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error checking/creating table ffc_sf_token: " + ex.getMessage());
            return false;
        }
    }
    
    private boolean createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE `ffc_sf_token` ("
                + "`id` int NOT NULL AUTO_INCREMENT,"
                + "`token_auth` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,"
                + "`token_claim` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,"
                + "`created_date` datetime DEFAULT NULL,"
                + "`updated_date` datetime DEFAULT NULL,"
                + "PRIMARY KEY (`id`)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";

        try {
            Statement stmt = Service.Service.connectionSQL.connection.createStatement();
            stmt.executeUpdate(createTableSQL);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfTokenHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating table ffc_sf_token: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * Insert new token record
     * @param tokenAuth Authentication token
     * @param tokenClaim Claim token
     * @return true if successful, false otherwise
     */
    public boolean insertToken(String tokenAuth, String tokenClaim) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot insert token - table creation failed.");
            return false;
        }
        
        String insertSQL = "INSERT INTO ffc_sf_token (token_auth, token_claim, created_date, updated_date) "
                + "VALUES (?, ?, NOW(), NOW())";
        
        try {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(insertSQL);
            pstmt.setString(1, tokenAuth);
            pstmt.setString(2, tokenClaim);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Token inserted successfully");
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfTokenHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error inserting token: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * Update existing token by id
     * @param id Token ID
     * @param tokenAuth Authentication token
     * @param tokenClaim Claim token
     * @return true if successful, false otherwise
     */
    public boolean updateToken(int id, String tokenAuth, String tokenClaim) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot update token - table creation failed.");
            return false;
        }
        
        String updateSQL = "UPDATE ffc_sf_token SET token_auth = ?, token_claim = ?, updated_date = NOW() WHERE id = ?";
        
        try {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(updateSQL);
            pstmt.setString(1, tokenAuth);
            pstmt.setString(2, tokenClaim);
            pstmt.setInt(3, id);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Token updated successfully for ID: " + id);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfTokenHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error updating token: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * Get the latest token record
     * @return ResultSet containing the latest token
     */
    public ResultSet getLatestToken() throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot get token - table creation failed.");
            return null;
        }
        
        String query = "SELECT * FROM ffc_sf_token ORDER BY updated_date DESC, id DESC LIMIT 1";
        return Service.Service.connectionSQL.getResultSet(query);
    }
    
    /**
     * Delete token by id
     * @param id Token ID
     * @return true if successful, false otherwise
     */
    public boolean deleteToken(int id) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot delete token - table creation failed.");
            return false;
        }
        
        String deleteSQL = "DELETE FROM ffc_sf_token WHERE id = ?";
        
        try {
            PreparedStatement pstmt = Service.Service.connectionSQL.connection.prepareStatement(deleteSQL);
            pstmt.setInt(1, id);
            
            int result = pstmt.executeUpdate();
            pstmt.close();
            
            System.out.println("Token deleted successfully for ID: " + id);
            return result > 0;
        } catch (SQLException ex) {
            Logger.getLogger(FfcSfTokenHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error deleting token: " + ex.getMessage());
            return false;
        }
    }
    
    /**
     * Update token for new visit
     * @param sqliteConnection SQLite connection
     * @param visitInsert Visit number to search for
     * @param visitMaxNew New visit number to use
     * @return true if successful, false otherwise
     */
    public boolean updateForNewVisit(ConnectDatabase.ConnectSQLite sqliteConnection, String visitInsert, int visitMaxNew) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot update ffc_sf_token for visit - table creation failed.");
            return false;
        }
        // หมายเหตุ: ffc_sf_token table ไม่มี visitno field ดังนั้นจึงไม่จำเป็นต้องทำอะไร
        // แต่ implement method นี้เพื่อให้สอดคล้องกับ pattern ของ handler อื่นๆ
        return true;
    }
    
    public int getUpdateCount() {
        return this.updateCount;
    }
    
    public void resetUpdateCount() {
        this.updateCount = 0;
    }
}