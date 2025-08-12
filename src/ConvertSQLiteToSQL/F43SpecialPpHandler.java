package ConvertSQLiteToSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class F43SpecialPpHandler {
    
    private ConnectDatabase.ConnectSQLite sqliteConnection;
    private int updateCount = 0;
    
    public F43SpecialPpHandler(ConnectDatabase.ConnectSQLite sqliteConnection) {
        this.sqliteConnection = sqliteConnection;
    }
    
    public int updateF43SpecialPp(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        this.sqliteConnection = sqliteConnection;
        if (!checkAndCreateTable()) {
            System.out.println("Cannot proceed with f43specialpp update - table creation failed.");
            return 0;
        }
        return this.updateCheckDateupdate(sqliteConnection, "f43specialpp", lastUpdate);
    }
    
    public int checkUpdateCount(ConnectDatabase.ConnectSQLite sqliteConnection, String lastUpdate) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot check f43specialpp updates - table creation failed.");
            return 0;
        }

        int updateCount = 0;
        System.out.println("Last Update: " + lastUpdate);
        Timestamp ts1 = Timestamp.valueOf(lastUpdate);
        Timestamp ts2;

        try {
            ResultSet rs = sqliteConnection.getResultSet("SELECT dateupdate FROM f43specialpp");
            while (rs.next()) {
                if (rs.getString("dateupdate") != null) {
                    ts2 = Timestamp.valueOf(rs.getString("dateupdate"));
                    if (ts1.compareTo(ts2) < 0) {
                        updateCount++;
                    }
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Table f43specialpp not found in SQLite database: " + ex.getMessage());
            return 0;
        }

        System.out.println("f43specialpp Update Count : " + updateCount);
        return updateCount;
    }
    
    public boolean updateForNewVisit(ConnectDatabase.ConnectSQLite sqliteConnection, String visitInsert, int visitMaxNew) throws SQLException {
        if (!checkAndCreateTable()) {
            System.out.println("Cannot update f43specialpp for visit - table creation failed.");
            return false;
        }
        return this.insertOtherVisit(sqliteConnection, "f43specialpp", visitMaxNew, visitInsert);
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
                                if (rsmd.getColumnLabel(i).equals("visitno") || rsmd.getColumnLabel(i).equals("visitNo")) {
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
                    System.out.println("Insert f43specialpp: " + insertData1 + insertData2);
                    stmt.executeUpdate(insertData1 + insertData2);
                }
                rss.close();
                rs.close();
                result = true;
            } else {
                System.out.println(tableName + " : No " + tableName + " Insert");
                result = false;
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
            String Query = "SELECT * FROM " + tablename + this.getQueryWhereCondition(tablename, rs);
            ResultSet rss = Service.Service.connectionSQL.getResultSet(Query);

            if (rss.next()) {
                System.out.println("Update f43specialpp data");
                this.updateDataOneRow(tablename, arrayRs);
            } else {
                System.out.println("Insert f43specialpp data");
                this.insertData(tablename, arrayRs);
            }
            count++;
        }
        return count;
    }
    
    private String getQueryWhereCondition(String tableName, ResultSet rs) throws SQLException {
        String value = "";
        if ("f43specialpp".equals(tableName)) {
            value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") 
                   + "' AND pid = '" + rs.getString("pid") 
                   + "' AND dateserv = '" + rs.getString("dateserv") 
                   + "' AND ppspecial = '" + rs.getString("ppspecial") + "'";
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
            
            System.out.println("Insert f43specialpp: " + insertQuery1 + insertQuery2);
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

            System.out.println("Update f43specialpp: " + updateQuery);
            stm.addBatch(updateQuery);
        }

        stm.executeBatch();
    }
    
    public boolean checkAndCreateTable() throws SQLException {
        try {
            String checkTableQuery = "SELECT COUNT(*) AS table_count FROM information_schema.tables "
                    + "WHERE table_schema = DATABASE() AND table_name = 'f43specialpp'";

            ResultSet rs = Service.Service.connectionSQL.getResultSet(checkTableQuery);
            int tableCount = 0;
            if (rs.next()) {
                tableCount = rs.getInt("table_count");
            }
            rs.close();

            if (tableCount == 0) {
                System.out.println("Table f43specialpp not found. Creating table...");
                if (createTable()) {
                    System.out.println("Table f43specialpp created successfully.");
                    return true;
                } else {
                    System.out.println("Failed to create table f43specialpp.");
                    return false;
                }
            } else {
                System.out.println("Table f43specialpp already exists.");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(F43SpecialPpHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error checking/creating table f43specialpp: " + ex.getMessage());
            return false;
        }
    }
    
    private boolean createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE `f43specialpp` ("
                + "`pcucodeperson` varchar(6) NOT NULL,"
                + "`pid` varchar(15) NOT NULL,"
                + "`ppspecial` varchar(2) NOT NULL,"
                + "`dateserv` date NOT NULL,"
                + "`clinic` varchar(5) DEFAULT NULL,"
                + "`provider` varchar(6) DEFAULT NULL,"
                + "`spcialppcode` varchar(2) DEFAULT NULL,"
                + "`visitno` varchar(15) DEFAULT NULL,"
                + "`dateupdate` datetime DEFAULT NULL,"
                + "PRIMARY KEY (`pcucodeperson`,`pid`,`ppspecial`,`dateserv`),"
                + "INDEX `idx_visitno` (`visitno`),"
                + "INDEX `idx_dateupdate` (`dateupdate`),"
                + "INDEX `idx_pid` (`pid`)"
                + ") ENGINE=MyISAM DEFAULT CHARSET=utf8";

        try {
            Statement stmt = Service.Service.connectionSQL.connection.createStatement();
            stmt.executeUpdate(createTableSQL);
            stmt.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(F43SpecialPpHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error creating table f43specialpp: " + ex.getMessage());
            return false;
        }
    }
    
    public int getUpdateCount() {
        return this.updateCount;
    }
    
    public void resetUpdateCount() {
        this.updateCount = 0;
    }
}