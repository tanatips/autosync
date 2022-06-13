/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConvertSQLiteToSQL;


import FFC_Form.MainForm;
import FFC_Information.FFCInformationManager;
import java.util.ArrayList;
import java.sql.*;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;



/**
 *
 * @author PeeT
 */
public class ConvertSQLiteToSQL {

    String villageCode = "";
    int personUpdateCount = 0;
    int personBehaviorUpdateCount = 0;
    int visitUpdateCount = 0;
    int visitDiagUpdateCount = 0;
    int visitDrugUpdateCount = 0;
    int houseUpdateCount = 0;
    int ncdUpdateCount = 0;
    ConnectDatabase.ConnectSQLite SQLiteConnection;

    FFCInformationManager ffcInformationManager;
    
   public  ConvertSQLiteToSQL() throws ClassNotFoundException, SQLException{
       SQLiteConnection = new ConnectDatabase.ConnectSQLite();
       ffcInformationManager = new FFCInformationManager();
       ffcInformationManager.openConnection(Service.Service.ffcInformationPath);
   }
   
   public void setConnection(ConnectDatabase.ConnectSQLite connectSQLite){
       this.SQLiteConnection = connectSQLite;
      
   }


    public void closeConnection(){
        try {
            this.SQLiteConnection.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    //Personbehvior
    public int updateAndInsertCheckDateupdate(String tableName) throws SQLException
    {
        int count = 0;
        String LastUpdate = this.getLastUpdate();
        System.out.println(LastUpdate);
        Timestamp ts1 = Timestamp.valueOf(LastUpdate);
        Timestamp ts2;
        
        ArrayList<ResultSet> personAndroidUpdateRs = new ArrayList<>();
        ArrayList<String> personPidAndroidUpdateString = new ArrayList<>();
        ArrayList<ResultSet> personUpdate = new ArrayList<>();
        ArrayList<ResultSet> personInsert = new ArrayList<>();
          
       System.out.println(villageCode);
       
       ResultSet rs;
       rs = this.SQLiteConnection.getResultSet("select dateupdate,pid from " + tableName);
       while(rs.next())
       {
           if(rs.getString("dateupdate") != null)
           {
               if("personbehavior".equals(tableName)){
                    if(rs.getString("dateupdate").length() == 10){
                        String newTime = "";
                        newTime = rs.getString("dateupdate");
                        ts2 = Timestamp.valueOf(newTime + " 00:00:00");
                    }
                    else{
                        String newTime = "";
                        newTime = rs.getString("dateupdate");  
                        ts2 = Timestamp.valueOf(newTime);
                    }
                       
               }else{
                       String newTime = "";
                       newTime = rs.getString("dateupdate");
                       ts2 = Timestamp.valueOf(newTime);
                  
               }
               if(ts1.compareTo(ts2) < 0)
               {
                    personPidAndroidUpdateString.add(rs.getString("pid"));
                    personAndroidUpdateRs.add(this.SQLiteConnection.getResultSet("select * from "+ tableName +" where pid =" + rs.getString("pid")));
                    ResultSetMetaData rsmd = personAndroidUpdateRs.get(0).getMetaData();
                    //System.out.println(rsmd.getColumnCount());
                    System.out.println(rs.getString("dateupdate")+ " : " +rs.getString("pid"));
               }
           }
           else
           {
               
           }
       }
       
       Statement stmt = Service.Service.connectionSQL.connection.createStatement();
       for(int i=0; i<personPidAndroidUpdateString.size(); i++)
       {
            //System.out.println(personPidUpdateString.get(i).toString());
           ResultSet getUpdateRs = Service.Service.connectionSQL.getResultSet("select * from "+ tableName +" where pid =" + personPidAndroidUpdateString.get(i).toString());
           personUpdate.add(getUpdateRs);
         if(getUpdateRs.next()){

           }else{
                personInsert.add(personAndroidUpdateRs.get(i)); 
           }
           getUpdateRs.beforeFirst();
       }
        System.out.println(personInsert.size() + "======================================================");
        System.out.println(personPidAndroidUpdateString.size() + "======================================================");
        System.out.println(personUpdate.size()+"======================================================");

       for(int i=0; i<personPidAndroidUpdateString.size(); i++)
       {
          System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
          String queryUpdate = "UPDATE " + tableName + " SET ";

          while(personUpdate.get(i).next() && personAndroidUpdateRs.get(i).next()){
              if(personUpdate.get(i).getString("pid").equals(personAndroidUpdateRs.get(i).getString("pid"))){
                System.out.println(personUpdate.get(i).getString("pid") + " = " + personAndroidUpdateRs.get(i).getString("pid"));
                if(personUpdate.get(i).getString("dateupdate") != null)
                {
                    if("personbehavior".equals(tableName))
                    {
                        if(personUpdate.get(i).getString("dateupdate").length()== 10)
                        {
                            ts1 = Timestamp.valueOf(personUpdate.get(i).getString("dateupdate")+ " 00:00:00");
                        }
                        else
                        {
                            ts1 = Timestamp.valueOf(personUpdate.get(i).getString("dateupdate"));
                        }
                        
                        if(personAndroidUpdateRs.get(i).getString("dateupdate").length()== 10)
                        {
                            ts2 = Timestamp.valueOf(personAndroidUpdateRs.get(i).getString("dateupdate")+ " 00:00:00");
                        }
                        else
                        {
                            ts2 = Timestamp.valueOf(personAndroidUpdateRs.get(i).getString("dateupdate"));
                        }
                        
                    }
                    else
                    {
                        ts1 = Timestamp.valueOf(personUpdate.get(i).getString("dateupdate"));
                        ts2 = Timestamp.valueOf(personAndroidUpdateRs.get(i).getString("dateupdate"));
                    }

                    if(ts1.compareTo(ts2) < 0)
                    {
                        ResultSetMetaData rsmd = personUpdate.get(i).getMetaData();
                        //System.out.println(rsmd.getColumnCount());
                        for(int j=1; j<=rsmd.getColumnCount(); j++)
                        {
                            if(j != rsmd.getColumnCount())
                            {
                                if(personAndroidUpdateRs.get(i).getString(j) != null)
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = '" + personAndroidUpdateRs.get(i).getString(j) + "', ";
                                }
                                else
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = " + personAndroidUpdateRs.get(i).getString(j) + ", ";
                                }
                            }
                            else
                            {
                                if(personAndroidUpdateRs.get(i).getString(j) != null)
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = '" + personAndroidUpdateRs.get(i).getString(j) + "' WHERE pid = " + personPidAndroidUpdateString.get(i);
                                    System.out.println(queryUpdate);
                                    stmt.executeUpdate(queryUpdate);
                                }
                                else
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = " + personAndroidUpdateRs.get(i).getString(j) + " WHERE pid = " + personPidAndroidUpdateString.get(i);
                                    System.out.println(queryUpdate);
                                    stmt.executeUpdate(queryUpdate);
                                }
                            }
                        }
                        count++;
                    }
                }else{
                    ResultSetMetaData rsmd = personUpdate.get(i).getMetaData();
                    //System.out.println(rsmd.getColumnCount());
                    for(int j=1; j<=rsmd.getColumnCount(); j++)
                    {
                        if(j != rsmd.getColumnCount())
                            {
                                if(personAndroidUpdateRs.get(i).getString(j) != null)
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = '" + personAndroidUpdateRs.get(i).getString(j) + "', ";
                                }
                                else
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = " + personAndroidUpdateRs.get(i).getString(j) + ", ";
                                }
                            }
                            else
                            {
                                if(personAndroidUpdateRs.get(i).getString(j) != null)
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = '" + personAndroidUpdateRs.get(i).getString(j) + "' WHERE pid = " + personPidAndroidUpdateString.get(i);
                                    //System.out.println(queryUpdate);
                                    stmt.executeUpdate(queryUpdate);
                                }
                                else
                                {
                                    queryUpdate += rsmd.getColumnName(j) + " = " + personAndroidUpdateRs.get(i).getString(j) + " WHERE pid = " + personPidAndroidUpdateString.get(i);
                                    //System.out.println(queryUpdate);
                                    stmt.executeUpdate(queryUpdate);
                                    
                                }
                            }
                    }
                    count++;
                }
            }else{
           }
          }
        }
        if(!personInsert.isEmpty()){
            this.InsertData(tableName, personInsert);
            count+=personInsert.size();
        }
        System.out.println("ENDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        return count;
    }

    public int updateCheckDateupdate(String tablename) throws SQLException{
        int count = 0;
        String lastUpdate = this.getLastUpdate();
        //System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        //System.out.println(lastUpdate);
        String sqliteQuery = "SELECT * FROM "+tablename+" WHERE dateupdate > '"+lastUpdate+"'";
        ResultSet rs = this.SQLiteConnection.getResultSet(sqliteQuery);
        while(rs.next()){
            ArrayList<ResultSet> arrayRs = new ArrayList<>();
            arrayRs.add(rs);
            String Query = "SELECT * FROM "+tablename+this.getQueryWhereCondition(tablename, rs);
            ResultSet rss = Service.Service.connectionSQL.getResultSet(Query);
            if(rss.next()){
                System.out.println("Update data");
                this.updateDataOneRow(tablename, arrayRs);
            }else{
                System.out.println("Insert data");
                this.InsertData(tablename, arrayRs);
            }
            count++;
        }
        return count;
    }


    public int updateCheckDateupdateNDC(String tablename) throws SQLException{
        int count = 0;
        String lastUpdate = this.getLastUpdate();
        String sqliteQuery ;
        //if(tablename.equals("ncd_person_ncd_screen")){
            sqliteQuery = "SELECT * FROM "+tablename+" WHERE dateupdate > '"+lastUpdate+"'";
        //}
        // else{
         //   sqliteQuery = "SELECT * FROM "+tablename+" WHERE d_update > '"+lastUpdate+"'";
        //}

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        System.out.println(lastUpdate);
        System.out.println(sqliteQuery);
        ResultSet rs = this.SQLiteConnection.getResultSet(sqliteQuery);
        while(rs.next()){
            ArrayList<ResultSet> arrayRs = new ArrayList<>();
            arrayRs.add(rs);
            String Query = "SELECT * FROM "+tablename+this.getQueryWhereCondition(tablename, rs);
           // String pid = rs.getString("pid");
            System.out.println(Query);
            ResultSet rss = Service.Service.connectionSQL.getResultSet(Query);
            if(rss.next()){
                System.out.println("Update data");
                this.updateDataOneRow(tablename, arrayRs);

                String pidThisRow = rs.getString("pid");
                System.out.println("0000000000000000000000000000 PID :::::::::::::  "+pidThisRow);
                if(tablename.equals("ncd_person_ncd_screen")){
                    updateOtherNCD("ncd_person",lastUpdate,pidThisRow);
                    updateOtherNCD("ncd_person_ncd",lastUpdate,pidThisRow);
                    updateOtherNCD("ncd_person_ncd_hist",lastUpdate,pidThisRow);
                    updateOtherNCD("ncd_person_ncd_hist_detail",lastUpdate,pidThisRow);
                    ncdUpdateCount++;
                }

            }else{
                System.out.println("Insert data");
                //this.InsertData(tablename, arrayRs);
                this.InsertNCD("ncd_person",rs);
                this.InsertNCD("ncd_person_ncd",rs);
                this.InsertNCD("ncd_person_ncd_hist",rs);
                this.InsertNCD("ncd_person_ncd_hist_detail",rs);
                System.out.println("finish detail");
                this.InsertNCD("ncd_person_ncd_screen",rs);

            }
            count++;
        }
        return count;
    }

    ////////////////////////
    public void updateOtherNCD(String tablename,String lastUpdate,String pid) throws SQLException{
        int count = 0;
        //String lastUpdate = this.getLastUpdate();
        String sqliteQuery ;
        sqliteQuery = "SELECT * FROM "+tablename+" WHERE pid = '"+pid+"'";
        //System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        //System.out.println(lastUpdate);
        //System.out.println(sqliteQuery);
        ResultSet rs = this.SQLiteConnection.getResultSet(sqliteQuery);
        if(tablename.equals("ncd_person_ncd_hist_detail")){
            String sqliteQuery2 = "SELECT * FROM ncd_person_ncd_screen  WHERE pid = '"+pid+"'";
            ResultSet rs2 = this.SQLiteConnection.getResultSet(sqliteQuery2);

            Statement stm1 = Service.Service.connectionSQL.connection.createStatement();
            String deleteQuery = "DELETE FROM "+tablename+this.getQueryWhereCondition(tablename, rs2);
            System.out.println(deleteQuery);
            stm1.executeUpdate(deleteQuery);
        }
        while(rs.next()){
            ArrayList<ResultSet> arrayRs = new ArrayList<>();
            arrayRs.add(rs);
            if(tablename.equals("ncd_person_ncd_hist_detail")){               
                this.InsertData(tablename, arrayRs);
            }
            else{
                String Query = "SELECT * FROM "+tablename+this.getQueryWhereCondition(tablename, rs);
                // String pid = rs.getString("pid");
                System.out.println(Query);
                ResultSet rss = Service.Service.connectionSQL.getResultSet(Query);
                if(rss.next()){
                    System.out.println("Update data");
                    this.updateDataOneRow(tablename, arrayRs);

                }else{
                    System.out.println("Insert data");
                    this.InsertData(tablename, arrayRs);
                }
                count++;
            }

        }
        return ;
    }

     private void InsertNCD(String tableName,ResultSet rs) throws SQLException{
         String sqliteQuery ;
         sqliteQuery = "SELECT * FROM "+tableName+this.getQueryWhereCondition(tableName, rs);
         ResultSet rss = this.SQLiteConnection.getResultSet(sqliteQuery);
         ArrayList<ResultSet> arrayRs = new ArrayList<>();

         int x = 1 ;
         Statement stm = Service.Service.connectionSQL.connection.createStatement();
         while(rss.next()){
            System.out.println("|||||||||||||||||||||||||||||||||||||||======== "+x);
            //arrayRs.add(rss);




                String insertQuery1 = "INSERT INTO " + tableName + " (";
                String insertQuery2 = " VALUES (";
                ResultSetMetaData rsmd = rss.getMetaData();
                int ColumnCount = rsmd.getColumnCount();
                //System.out.print("ColumnCount = "+ColumnCount);
                for (int i = 1; i <= ColumnCount; i++) {
                        insertQuery1 += rsmd.getColumnLabel(i);

                    if (rss.getString(i) != null) {
                        insertQuery2 += "'" + rss.getString(i) + "'";
                    } else {
                        insertQuery2 += rss.getString(i);
                    }
                    if (i < ColumnCount) {
                        insertQuery1 += ",";
                        insertQuery2 += ",";
                    } else {
                        insertQuery1 += ")";
                        insertQuery2 += ")";
                    break;
                    }
                    //System.out.print(" "+i);
                }
                //System.out.println(insertQuery1);
                //System.out.println(insertQuery2);
                System.out.println("ccccccccccccccccccccccccxxx  "+tableName);
                System.out.println(insertQuery1+insertQuery2);
                stm.executeUpdate(insertQuery1 + insertQuery2);
                x++;
                }






           // this.InsertData(tableName, arrayRs);
            
            System.out.println("Endddddd");
         }
    

    //////////////////////

    public void updatePerson() throws SQLException{
        int pidmax = Integer.parseInt(this.ffcInformationManager.getMaxpid(Service.Service.serialDeviceConnect));
        String lastUpdate = this.getLastUpdate();

        String sqliteQuery = "SELECT person.* FROM person WHERE person.dateupdate > '"+lastUpdate+"'";
        ResultSet rs = this.SQLiteConnection.getResultSet(sqliteQuery);

        int countloop = 0;
        while(rs.next()){
            ArrayList<ResultSet> personUpdate = new ArrayList<>();
            //ArrayList<ResultSet> personInsert = new ArrayList<ResultSet>();
            
            String Query = "SELECT person.* FROM person"+this.getQueryWhereCondition("person", rs);
            ResultSet rss = this.SQLiteConnection.getResultSet(Query);
            if(rs.getInt("pid") > pidmax){
                int lastPid = Service.Service.connectionSQL.getLastNum("person", "pid");
                int newPid = lastPid+1;
                System.out.println("Insert Person : "+rs.getString("pid")+" >> "+newPid);
                this.insertSpecificColumn("person", rss, String.valueOf(newPid), "pid");
                this.syncPidAndroidGroup(rs.getString("pid"), String.valueOf(newPid));
                this.personUpdateCount++;
            }else{
                personUpdate.add(rss);
                System.out.println("Update Person : "+rss.getString("pid"));
                this.updateData("person", personUpdate);
                this.personUpdateCount++;
            }
            countloop++;
        }
    }

    public int updateAndInsertHouse() throws SQLException,Exception{
        int countUpdate=0;
        int hcodemax = Integer.parseInt(this.ffcInformationManager.getMaxhcode(Service.Service.serialDeviceConnect));
        String lastUpdate = this.getLastUpdate();

        String sqliteQuery = "SELECT house.* FROM house WHERE house.dateupdate > '"+lastUpdate+"'";
        ResultSet rs = this.SQLiteConnection.getResultSet(sqliteQuery);

        int countloop = 0;
        while(rs.next()){
            ArrayList<ResultSet> houseUpdate = new ArrayList<>();

            String Query = "SELECT house.* FROM house"+this.getQueryWhereCondition("house", rs);
            ResultSet rss = this.SQLiteConnection.getResultSet(Query);
            if(rs.getInt("hcode") > hcodemax){
                //personInsert.add(rss);
                int lasthcode = Service.Service.connectionSQL.getLastNum("house", "hcode");
                int newhcode = lasthcode+1;
                System.out.println("Insert house : "+rs.getString("hcode")+" >> "+newhcode);
                this.insertSpecificColumn("house", rss, String.valueOf(newhcode), "hcode");
                this.syncHcodeAndroidGroup(rs.getString("hcode"), String.valueOf(newhcode));
                ArrayList<String> houseUpdateString = new ArrayList<>();
                houseUpdateString.add(String.valueOf(newhcode));
                this.UpdateHouseGroupTables("houseanimal", houseUpdateString);
                this.UpdateHouseGroupTables("housegenusculex", houseUpdateString);
                this.UpdateHouseGroupTables("housevesselwater", houseUpdateString);
                countUpdate++;
            }else{
                ArrayList<String> houseUpdateString = new ArrayList<>();
                houseUpdateString.add(rss.getString("hcode"));
                houseUpdate.add(rss);
                System.out.println("Update hcode : "+rss.getString("hcode"));
                this.updateData("house", houseUpdate);
                this.UpdateHouseGroupTables("houseanimal", houseUpdateString);
                this.UpdateHouseGroupTables("housegenusculex", houseUpdateString);
                this.UpdateHouseGroupTables("housevesselwater", houseUpdateString);
                countUpdate++;
            }
            countloop++;
        }
        return countUpdate;
    }

    

    public String getLastUpdate() throws SQLException{
        return this.ffcInformationManager.getLastupdate(Service.Service.serialDeviceConnect);
    }

    //อัพเดท visit
    public void insertVisit(String tableName) throws SQLException
    {
        int visitMax = Integer.parseInt(this.ffcInformationManager.getMaxvisit(Service.Service.serialDeviceConnect));
        int visitMaxServer = 0;
        
        
        ArrayList<ResultSet> visitNoInsertRs = new ArrayList<>();
        
        String queryVisitMax = "SELECT MAX(visitno) AS visitMaxServer FROM visit";
        ResultSet rs1 = Service.Service.connectionSQL.getResultSet(queryVisitMax);
        while(rs1.next())
        {
            visitMaxServer = Integer.parseInt(rs1.getString("visitMaxServer"));
            System.out.println(visitMaxServer);
        }
        rs1.close();
        int visitMaxNew = visitMaxServer;
        String query = "Select * From "+ tableName +" WHERE visitno > " + visitMax;
        
        ResultSet rs = this.SQLiteConnection.getResultSet(query);
        Statement stmt = Service.Service.connectionSQL.connection.createStatement();
        int visitNoColumnIndex = 2;
      
        while(rs.next())
        {   
            String insertData1 = "INSERT INTO "+tableName+" (";
            String insertData2 = " VALUES (";
            ResultSetMetaData rsmd = rs.getMetaData();
          
//                String queryInsert = "SELECT * FROM "+tableName+" WHERE visitno = "+rs.getString("visitno");
//                visitNoInsertRs.add(this.SQLiteConnection.getResultSet(queryInsert));
                for(int i=1; i<=rsmd.getColumnCount(); i++)
                {
                    if(i != rsmd.getColumnCount())
                    {
                          insertData1 +=rsmd.getColumnLabel(i) + ",";
                          
                          if(rs.getString(i) != null)
                          {
                              if(i == visitNoColumnIndex)
                              {
                                  visitMaxNew = visitMaxNew + 1;
                                  insertData2 += "'" + visitMaxNew + "',";
                              }
                              else
                              {
                                  insertData2 += "'" + rs.getString(i) + "',";
                              }
                          }                
                          else
                          {
                              insertData2 += rs.getString(i) + ",";
                          }
                    }
                    else
                    {
                        if(rs.getString(i) != null)
                            insertData2 += "'" + rs.getString(i) + "')";
                        else
                            insertData2 += rs.getString(i) + ")";
                        insertData1 += rsmd.getColumnLabel(i)+ ")";
                        
                    }
                }
                
                stmt.executeUpdate(insertData1 + insertData2);
               
                visitUpdateCount++;
               
                insertOtherVisit("visitdiag",visitMaxNew,rs.getString("visitno"));
                insertOtherVisit("visitdrug",visitMaxNew,rs.getString("visitno"));

                this.insertVisitGroup(rs.getString("visitno"),visitMaxNew);
//         
                System.out.println("======================================================================");
        }
        rs.close();
        stmt.close();
        MainForm.transForm.setValueTransferProgressBar(100);
        
    }


    //อัพเดท visit อื่นๆ
    public boolean insertOtherVisit(String tableName,int visitMaxNew,String visitInsert) throws SQLException
    {
        String query = "Select * From "+ tableName+" WHERE visitno = "+ visitInsert; 
        ResultSet rs = this.SQLiteConnection.getResultSet(query);
        Statement stmt = Service.Service.connectionSQL.connection.createStatement();
        boolean result = false;
        if(rs.next())
        {
           ResultSet rss = this.SQLiteConnection.getResultSet(query);
            while(rss.next())
            {   
                String insertData1 = "INSERT INTO "+tableName+" (";
                String insertData2 = " VALUES (";
                ResultSetMetaData rsmd = rss.getMetaData();
                for(int i=1; i<=rsmd.getColumnCount(); i++)
                {
                    if(i != rsmd.getColumnCount())
                        {
                              insertData1 +=rsmd.getColumnLabel(i) + ",";

                              if(rss.getString(i) != null)
                              {
                                  if(rsmd.getColumnLabel(i).equals("visitno") || rsmd.getColumnLabel(i).equals("visitNo"))
                                  {
                                      insertData2 += "'" + visitMaxNew + "',";
                                  }
                                  else
                                  {
                                      insertData2 += "'" + rss.getString(i) + "',";
                                  }
                              }                
                              else
                              {
                                  insertData2 += rss.getString(i) + ",";
                              }
                        }
                        else
                        {
                            if(rss.getString(i) != null)
                                insertData2 += "'" + rss.getString(i) + "')";
                            else
                                insertData2 += rss.getString(i) + ")";
                                insertData1 += rsmd.getColumnLabel(i)+ ")";

                        }
                }
                switch (tableName) {
                    case "visitdiag":
                        visitDiagUpdateCount++;
                        break;
                    case "visitdrug":
                        visitDrugUpdateCount++;
                        break;
                }
                //System.out.println(insertData1);
                System.out.println(insertData1 + insertData2);
                stmt.executeUpdate(insertData1 + insertData2);
                
            }
            rss.close();
            rs.close();
           result = true;
        }
        else
        {
            System.out.println(tableName + " : No "+tableName+" Insert");
            result = false;
        }
        return result;
    }
    
    public void printUpdateCount()
    {
        System.out.println(this.personUpdateCount);
        System.out.println(this.personBehaviorUpdateCount);
        System.out.println(this.visitUpdateCount);
        System.out.println(this.visitDiagUpdateCount);
        System.out.println(this.visitDrugUpdateCount);
        System.out.println(this.houseUpdateCount);
    }
    
    public String getPersonUpdateCount()
    {
        return String.valueOf(this.personUpdateCount);
    }
    
    public String getPersonBehaviorUpdateCount()
    {
        return String.valueOf(this.personBehaviorUpdateCount);
    }
    
    public String getVisitUpdateCount()
    {
        return String.valueOf(this.visitUpdateCount);
    }
    public String getNcdUpdateCount()
    {
        return String.valueOf(this.ncdUpdateCount);
    }
    public String getVisitdiagUpdateCount()
    {
        return String.valueOf(this.visitDiagUpdateCount);
    }
    
    public String getVisitDrugUpdateCount()
    {
        return String.valueOf(this.visitDrugUpdateCount);
    }

    public String gethouseUpdateCount(){
        return String.valueOf(this.houseUpdateCount);
    }
    
    public ArrayList<String> checkUpdate() throws ClassNotFoundException, SQLException{
           
            Service.Service.SQLiteConnection.connectSQLite("./FFC/Db_tmp/mJHCIS.db");
            ArrayList<String> listUpdate = new ArrayList<>();
            listUpdate.add(String.valueOf(this.checkUpdatePerson(Service.Service.SQLiteConnection, "person")));
            listUpdate.add(String.valueOf(this.checkUpdatePerson(Service.Service.SQLiteConnection, "personbehavior")));
            listUpdate.add(String.valueOf(this.sumGisUpdateCount(Service.Service.SQLiteConnection)));
            listUpdate.add(String.valueOf(this.checkUpdatevisit(Service.Service.SQLiteConnection, "visit")));
            listUpdate.add(String.valueOf(this.checkUpdatevisit(Service.Service.SQLiteConnection, "visitdiag")));
            listUpdate.add(String.valueOf(this.checkUpdatevisit(Service.Service.SQLiteConnection, "visitdrug")));
            listUpdate.add(String.valueOf(this.checkUpdatePerson(Service.Service.SQLiteConnection, "persondeath")));
            String countNCD = String.valueOf(this.checkUpdateNCD(Service.Service.SQLiteConnection, "ncd_person_ncd_screen"));
            
            listUpdate.add(countNCD);

            if (Service.Service.SQLiteConnection.closeConnection()) {
                System.out.println("Connection Close");
            } else {
                System.out.println("Connection is alive");
            }
            return listUpdate;
    }
    
    public int checkUpdatePerson(ConnectDatabase.ConnectSQLite connection,String tableName) throws SQLException{
        int updateCount = 0;
        String LastUpdate = getLastUpdate();
        System.out.println(LastUpdate);
        Timestamp ts1 = Timestamp.valueOf(LastUpdate);
        Timestamp ts2;
        
        ResultSet rs;
        rs = connection.getResultSet("SELECT dateupdate FROM " + tableName);
       while(rs.next())
       {
           if(rs.getString("dateupdate") != null)
           {    //System.out.println(rs.getString("dateupdate").substring(0, 1));
                if("personbehavior".equals(tableName)){
                    if(rs.getString("dateupdate").length() == 10){
                            ts2 = Timestamp.valueOf(rs.getString("dateupdate") + " 00:00:00");
                    }
                    else{
                            ts2 = Timestamp.valueOf(rs.getString("dateupdate"));
                    }
               }else{
                            ts2 = Timestamp.valueOf(rs.getString("dateupdate"));
               }
                   //System.out.println(ts1.toString()+" : "+ts2.toString());
                if(ts1.compareTo(ts2) < 0)
               {
                    updateCount++;
               }  
           }
           else
           {
               
           }
       }
        System.out.println(tableName +" Update Count : "+updateCount);
       return updateCount;
    }
    
    public int checkUpdatevisit(ConnectDatabase.ConnectSQLite connection,String tableName) throws SQLException{
        int updateCount = 0;
        // visitMaxManager = new VisitMaxInformationManager();
        //visitMaxManager.setFileURL("./FFC/maxvisit_information.ffc");
        int visitMax = Integer.parseInt(this.ffcInformationManager.getMaxvisit(Service.Service.serialDeviceConnect));
         String query = "Select COUNT(*) AS updateCount From "+ tableName +" WHERE visitno > " + visitMax;
         ResultSet rs = connection.getResultSet(query);
         while(rs.next()){
             updateCount = Integer.parseInt(rs.getString("updateCount"));
         }
         return updateCount;
    }

    //check update NCD
    public int checkUpdateNCD(ConnectDatabase.ConnectSQLite connection,String tableName) throws SQLException{
        int updateCount = 0;
        String lastUpdate = this.getLastUpdate();
        String sqliteQuery = "SELECT * FROM "+tableName+" WHERE dateupdate > '"+lastUpdate+"'";
        System.out.println(sqliteQuery);
        ResultSet rs = connection.getResultSet(sqliteQuery);
        while(rs.next()){
             updateCount++;
        }
         return updateCount;











    }




    public void InsertWithNewPid(String tableName, ArrayList<ResultSet> personInsert) {
        String[] table = {"personbehavior"};
        int lastPid = 0;
        int newPid;
        ResultSet rs = Service.Service.connectionSQL.getResultSet("SELECT MAX(pid) AS lastPid FROM "+ tableName);
        try {
            while (rs.next()) {
                lastPid = rs.getInt("lastPid");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
            return;
        }
        newPid = lastPid+1;
        for (ResultSet person : personInsert) {
            try {
                this.insertSpecificColumn(tableName, person, String.valueOf(newPid), "pid");
                this.updateNewPidOtherTable(table, String.valueOf(newPid), String.valueOf(lastPid), "pid");

                
            } catch (SQLException ex) {
                Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getMessage());
            }
            if (tableName.equals("person")) {
                this.personUpdateCount++;
            } else {
                this.personBehaviorUpdateCount++;
            }
            newPid++;
        }
    }


    private void InsertData(String tableName,ArrayList<ResultSet> dataInsert) throws SQLException{
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        
        for (ResultSet data : dataInsert) {
            String insertQuery1 = "INSERT INTO " + tableName + " (";
            String insertQuery2 = " VALUES (";
            ResultSetMetaData rsmd = data.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            //System.out.print("ColumnCount = "+ColumnCount);
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
                //System.out.print(" "+i);
            }
            //System.out.println(insertQuery1);
            //System.out.println(insertQuery2);
            System.out.println("ccccccccccccccccccccccccxxx  "+tableName);
            System.out.println(insertQuery1+insertQuery2);
            stm.executeUpdate(insertQuery1 + insertQuery2);
            }
    }

    private boolean InsertDataResultSet(String tableName,ResultSet dataInsert){
        try {
            Statement stm;
            stm = Service.Service.connectionSQL.connection.createStatement();
            //System.out.print("ColumnCount = "+ColumnCount);
            while (dataInsert.next()) {
                String insertQuery1 = "INSERT INTO " + tableName + " (";
                String insertQuery2 = " VALUES (";
                ResultSetMetaData rsmd = dataInsert.getMetaData();
                int ColumnCount = rsmd.getColumnCount();
                //System.out.println("Insert Riskcode : "+data.getString("ancriskcode"));
                for (int i = 1; i <= ColumnCount; i++) {
                    insertQuery1 += rsmd.getColumnLabel(i);
                    if (dataInsert.getString(i) != null) {
                        insertQuery2 += "'" + dataInsert.getString(i) + "'";
                    } else {
                        insertQuery2 += dataInsert.getString(i);
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
                System.out.println(insertQuery1 + insertQuery2);
                stm.executeUpdate(insertQuery1 + insertQuery2);
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void updateData(String tableName, ArrayList<ResultSet> data) throws SQLException{
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        for(ResultSet dataUpdate : data){
            
            ResultSetMetaData rsmd = dataUpdate.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            int rowCount = 0;
            while(dataUpdate.next()){
                String updateQuery = "UPDATE " + tableName + " SET ";
                for (int i = 1; i <= ColumnCount; i++) {
                    updateQuery += rsmd.getColumnLabel(i)+" = ";

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
            rowCount++;
            System.out.println(updateQuery);
            stm.addBatch(updateQuery);
            }
            System.out.println("Row = "+ rowCount);
        }
        
        stm.executeBatch();
        //String updateQuery2 = "";
    }

    public void updateDataOneRow(String tableName, ArrayList<ResultSet> data) throws SQLException{
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        for(ResultSet dataUpdate : data){
            ResultSetMetaData rsmd = dataUpdate.getMetaData();
            int ColumnCount = rsmd.getColumnCount();
            int rowCount = 0;

            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println("ColumnCount ======= "+ColumnCount);
            String updateQuery = "UPDATE " + tableName + " SET ";
            for (int i = 1; i <= ColumnCount; i++) {
                updateQuery += rsmd.getColumnLabel(i)+" = ";

                if (dataUpdate.getString(i) != null) {
                    //System.out.println("11111111111111111111111111111111111111111111111111111111111111");
                    updateQuery += "'" + dataUpdate.getString(i) + "'";
                } else {
                    //System.out.println("222222222222222222222222222222222222222222222222222222222222222");
                    updateQuery += dataUpdate.getString(i);
                }
                if (i < ColumnCount) {
                    System.out.println("3333333333333333333333333333333333333333333333333333");
                    updateQuery += ",";

                } else {
                    System.out.println("4444444444444444444444444444444444444444444444444444444");
                        updateQuery += this.getQueryWhereCondition(tableName, dataUpdate);
                        break;
                }
            }
            rowCount++;

            System.out.println("Updateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            System.out.println(updateQuery);
            stm.addBatch(updateQuery);

            System.out.println("Row = "+ rowCount);
        }

        stm.executeBatch();
        
        //String row_pid = data.getString("pid");
        //String updateQuery2 = "";
    }

    //update housevesselwater, houseanimal, housegenusculex
    public void UpdateHouseGroupTables(String tableName, ArrayList<String> hcode) throws SQLException{
        Statement stm = Service.Service.connectionSQL.connection.createStatement();
        for(int i=0;i<hcode.size();i++){
        String query = "SELECT * FROM "+ tableName +" WHERE hcode = "+hcode.get(i);
        System.out.println(query);
        ResultSet rsSQLite = this.SQLiteConnection.getResultSet(query);
        stm.executeUpdate("DELETE FROM " +tableName+ " WHERE hcode = "+hcode.get(i));
        while(rsSQLite.next()){
            System.out.println("===========Find House=============");
            ArrayList<ResultSet> rsUpdateSQLite = new ArrayList<>();
            rsUpdateSQLite.add(rsSQLite);
            this.InsertData(tableName, rsUpdateSQLite);
        }
      }
    }

    
    public void updateFamilyplan(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitfp", visitMaxNew, visitInsert)){
            Service.Service.updateCount.familyPlanCount++;
        }
    }
    
    public void updateCcbp(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitlabcancer", visitMaxNew, visitInsert)){
            Service.Service.updateCount.ccbpCount++;
        }
    }
    
    public void updatePg(String visitInsert, int visitMaxNew) throws SQLException{
        this.insertVisitAncRisk(visitInsert);
        this.updateCheckDateupdate("visitancpregnancy");
        if(this.insertOtherVisit("visitanc", visitMaxNew, visitInsert)){
            Service.Service.updateCount.pgCount++;
        }
    }
    
    public void updateLabBlood(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitlabblood", visitMaxNew, visitInsert)){
            Service.Service.updateCount.labBloodCount++;
        }
    }
    
    public void updateApg(String visitInsert, int visitMaxNew) throws SQLException{
        boolean check1 =false;
        boolean check2 =false;
        if(this.insertOtherVisit("visitancdeliver", visitMaxNew, visitInsert)){
            check1 = true;
        }
        if(this.insertOtherVisit("visitancmothercare", visitMaxNew, visitInsert)){
            check2 = true;
        }
        if(check1||check2){
           Service.Service.updateCount.apgCount++; 
        }

    }
    
    public void updateBabypg(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitbabycare", visitMaxNew, visitInsert)){
            Service.Service.updateCount.babypgCount++;
        }
    }
    
    public void updateNutrition(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitnutrition", visitMaxNew, visitInsert)){
            Service.Service.updateCount.nutritionCount++;
        }
    }
    
    public void updateEpi(String visitInsert, int visitMaxNew) throws SQLException{
        this.insertOtherVisit("visitepiappoint", visitMaxNew, visitInsert);
        if(this.insertOtherVisit("visitepi", visitMaxNew, visitInsert)){
            Service.Service.updateCount.epiCount++;
        }
    }
    
    public void updateDentalcheck(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitdentalcheck", visitMaxNew, visitInsert)){
            Service.Service.updateCount.mouthCount++;
        }
    }
    
    public void updatePersongrow() throws SQLException{
        int persongrowCount = 0;
        persongrowCount = this.updateVillageOtherTable("persongrow", "datesurvey");
        Service.Service.updateCount.persongrow = persongrowCount;
        
    }

    
    public void updateWomen() throws SQLException{
        Service.Service.updateCount.womanCount = this.updateCheckDateupdate("women");
    }
    
    
    public void update506addr(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitdiag506address", visitMaxNew, visitInsert)){
            Service.Service.updateCount.Addr506Count++;
        }
    }

    public void updateHvisit(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visithomehealthindividual", visitMaxNew, visitInsert)){
            Service.Service.updateCount.hvisitCount++;
        }
    }

    public void updateVisitScreenSpecialDisease(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("visitscreenspecialdisease", visitMaxNew, visitInsert)){
            Service.Service.updateCount.specialDiseaseCount++;
        }
    }


     public void updateOldter(String visitInsert, int visitMaxNew) throws SQLException{
        if(this.insertOtherVisit("ffc_visitoldter", visitMaxNew, visitInsert)){
            Service.Service.updateCount.oldCount++;
        }
    }

    public void updateDental(String visitInsert, int visitMaxnew) throws SQLException{
        boolean check1 =false;
        boolean check2 =false;
        if(this.insertOtherVisit("visitdrugdental", visitMaxnew, visitInsert)){
            check1 = true;
        }
        if(this.insertOtherVisit("visitdrugdentaldiag", visitMaxnew, visitInsert)){
            check2 = true;
        }
        if(check1||check2){
            Service.Service.updateCount.dentalCount++;
        }
    }

    public void updateVisitdiagAppoi(String visitInsert, int visitMaxnew) throws SQLException{
        this.insertOtherVisit("visitdiagappoint", visitMaxnew, visitInsert);
    }

   //paeng
    public void updateVisiNcdPersonNcdScreen(String visitInsert, int visitMaxnew) throws SQLException{
        this.insertOtherVisit("ncd_person_ncd_screen ", visitMaxnew, visitInsert);
    }

    public void updatePersonbehavior() throws SQLException{
        Service.Service.updateCount.personbehaviorCount = this.updateCheckDateupdate("personbehavior");
    }

    public void updatePersondeath() throws SQLException{
        Service.Service.updateCount.persondeathCount = this.updateCheckDateupdate("persondeath");
    }
    
    public void updatePersonunableGroup() throws SQLException{
        this.updatepersonunable("personunable");
        Service.Service.updateCount.personunableCount = this.updateCheckDateupdate("personunable1type");
        this.updatepersonunable("personunable2prob");
        this.updatepersonunable("personunable3need");
        this.updatepersonunable("personunable4help");
       }

    //by paeng ncd
//    public void updatePersonNcd() throws SQLException{
//        Service.Service.updateCount.ncd_person = this.updateCheckDateupdateNDC("ncd_person");
//    }
//
//    public void updatePersonNcpNcd() throws SQLException{
//        Service.Service.updateCount.ncp_person_ncd = this.updateCheckDateupdateNDC("ncd_person_ncd");
//    }
//
//    public void updatePersonNcpNcdHist() throws SQLException{
//        Service.Service.updateCount.ncd_person_ncd_hist = this.updateCheckDateupdateNDC("ncd_person_ncd_hist");
//    }
//
//    public void updatePersonNcpNcdHistDetail() throws SQLException{
//        Service.Service.updateCount.ncd_person_ncd_hist_detail = this.updateCheckDateupdateNDC("ncd_person_ncd_hist_detail");
//    }

    public void updatePersonNcpNcdScreen() throws SQLException{
        Service.Service.updateCount.ncd_person_ncd_screen = this.updateCheckDateupdateNDC("ncd_person_ncd_screen");
        //System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"+Service.Service.updateCount.ncd_person_ncd_screen);
    }


    public class UpdateCountSet{
        public int personCount = 0;
        public int personbehaviorCount = 0;
        public int persondeathCount = 0;
        public int personunableCount = 0;

        public int houseCount = 0;
        public int familyPlanCount = 0;
        public int ccbpCount = 0;
        public int pgCount = 0;
        public int apgCount = 0;
        public int babypgCount = 0;
        public int babyCount = 0;
        public int womanCount = 0;
        public int oldCount = 0;
        public int Addr506Count = 0;
        public int hvisitCount = 0;
        public int unableCount = 0;
        public int villbusinessCount = 0;
        public int villschoolCount = 0;
        public int villtempleCount = 0;
        public int poiCount = 0;
        public int hospitalCount = 0;
        public int villwaterCount = 0;
        public int specialDiseaseCount = 0;
        public int dentalCount = 0;
        public int labBloodCount = 0;
        public int nutritionCount = 0;
        public int epiCount = 0;
        public int persongrow = 0;
        public int mouthCount = 0; 

        //by paeng for NCD
        public int ncd_person_ncd_screen = 0;


        public void UpdateCountSet(){

        }

        public void clearValue(){
            this.personCount = 0;
            this.personbehaviorCount = 0;
            this.persondeathCount = 0;
            this.personunableCount = 0;

            this.Addr506Count = 0;
            this.apgCount = 0;
            this.babyCount = 0;
            this.babypgCount = 0;
            this.ccbpCount = 0;
            this.familyPlanCount = 0;
            this.houseCount = 0;
            this.hvisitCount = 0;
            this.oldCount = 0;
            this.pgCount = 0;
            this.womanCount = 0;
            this.unableCount = 0;
            this.villbusinessCount = 0;
            this.villschoolCount = 0;
            this.villtempleCount = 0;
            this.specialDiseaseCount = 0;
            this.dentalCount = 0;
            this.labBloodCount = 0;
            this.epiCount = 0;
            this.nutritionCount = 0;
            this.persongrow = 0;
            this.mouthCount = 0;

            //by paeng for NCD
           
            this.ncd_person_ncd_screen = 0;
        }
    }

    public UpdateCountSet getUpdateCountSet(){
        UpdateCountSet updatecountset = new UpdateCountSet();
        return updatecountset;
    }

    public boolean insertVisitHomeHealth(String tableName,int visitMaxNew,String visitInsert) throws SQLException{
        String query = "Select * From "+ tableName+ " WHERE visitno = "+ visitInsert; 
        ResultSet rs = this.SQLiteConnection.getResultSet(query);
        if(rs.next()){
            System.out.println("Findddddddd Inserttttttttt Homehealthhh");
            
            ResultSet rss = this.SQLiteConnection.getResultSet(query);
          
            this.insertSpecificColumn(tableName, rss, Integer.toString(visitMaxNew), "visitno");
           
            ResultSet rsss = this.SQLiteConnection.getResultSet(query);
            while(rsss.next()){
                System.out.println("homehealthtyperss : " + rsss.getString("homehealthtype"));
                switch (rsss.getString("homehealthtype")) {
                    case "901":
                        Service.Service.updateCount.oldCount++;
                        break;
                    case "701":
                        Service.Service.updateCount.unableCount++;
                        break;
                    default:
                        Service.Service.updateCount.hvisitCount++;
                        break;
                }
            }
            return true;
        }else{
            return false;
        }
    }

    public void insertSpecificColumn(String tableName, ResultSet dataInsert, String newId, String columnName) throws SQLException{
        Statement stmt = Service.Service.connectionSQL.connection.createStatement();
        String columnNameSelect = columnName;
        while(dataInsert.next())
            {
                String insertData1 = "INSERT INTO "+tableName+" (";
                String insertData2 = " VALUES (";
                ResultSetMetaData rsmd = dataInsert.getMetaData();
                for(int i=1; i<=rsmd.getColumnCount(); i++)
                {
                    if(i != rsmd.getColumnCount())
                        {
                              insertData1 +=rsmd.getColumnLabel(i) + ",";
                              if(dataInsert.getString(i) != null)
                              {
                                  if(rsmd.getColumnName(i).equals(columnNameSelect))
                                  {
                                      insertData2 += "'" + newId + "',";
                                  }
                                  else
                                  {
                                      insertData2 += "'" + dataInsert.getString(i) + "',";
                                  }
                              }
                              else
                              {
                                  insertData2 += dataInsert.getString(i) + ",";
                              }
                        }
                        else
                        {
                            if(dataInsert.getString(i) != null)
                                insertData2 += "'" + dataInsert.getString(i) + "')";
                            else
                                insertData2 += dataInsert.getString(i) + ")";
                                insertData1 += rsmd.getColumnLabel(i)+ ")";

                        }
                }
                //System.out.println(insertData1);
                //System.out.println(insertData2);
                System.out.println(insertData1 + insertData2);
                stmt.executeUpdate(insertData1 + insertData2);
            }
        }

       public void updateNewPidOtherTable(String[] tableName, String newPid, String oldPid, String columnUpdate) throws SQLException{
           Statement stmt = Service.Service.connectionSQL.connection.createStatement();
           int tableAmount = tableName.length;

           for(int i=0; i< tableAmount; i++){
               String query = "UPDATE "+ tableName[i] + " SET "+ columnUpdate + " = '" + newPid +"'" + " WHERE " + columnUpdate + "='" + oldPid +"'";
               System.out.println(query);
               stmt.addBatch(query);  
           }
           stmt.executeBatch();
       }

       public int updateVillageOtherTable(String tableName,String fieldCheck) throws SQLException{
           //ArrayList<ResultSet> newUpdate = new ArrayList<ResultSet>();
           int countUpdate = 0;
           String lastUpdate = this.getLastUpdate();

           Statement stmt = Service.Service.connectionSQL.connection.createStatement();
           String queryNewUpdate = "SELECT * FROM "+tableName+" WHERE "+fieldCheck+" > '"+lastUpdate+"'";
           System.out.println(queryNewUpdate);
           ResultSet NewUpdateRs = this.SQLiteConnection.getResultSet(queryNewUpdate);
           ArrayList<ResultSet> arrayListUpdate = new ArrayList<>();
           ArrayList<ResultSet> arrayListInsert = new ArrayList<>();
            while(NewUpdateRs.next()){
                    String query = "SELECT * FROM "+tableName+this.getQueryWhereCondition(tableName, NewUpdateRs);
                    System.out.println(query);
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                        ResultSet rss = this.SQLiteConnection.getResultSet(query);
                        arrayListUpdate.add(rss);
                        countUpdate++;
                    }else{
                        ResultSet rss = this.SQLiteConnection.getResultSet(query);
                        arrayListInsert.add(rss);
                        countUpdate++;
                    }
            }
           this.updateData(tableName, arrayListUpdate);
           this.InsertData(tableName, arrayListInsert);
           System.out.println(tableName+ " Update Count : "+ countUpdate);
           return countUpdate;
       }

       private String getQueryWhereCondition(String tableName, ResultSet rs) throws SQLException{
           System.out.println("table name :::::::::::::::: "+tableName);
           String value = "";
        switch (tableName) {
            case "housevesselwater":
                value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND hcode = '" + rs.getString("hcode") + "' AND vessel ='" + rs.getString("vessel") + "'";
                break;
            case "housegenoculex":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND hcode = '" + rs.getString("hcode") + "' AND datesurvey ='" + rs.getString("datesurvey") + "'";
                break;
            case "houseanimal":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND hcode = '" + rs.getString("hcode") + "' AND animaltype ='" + rs.getString("animaltype") + "'";
                break;
            case "villagebusiness":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND businessno = '" + rs.getString("businessno") + "'";
                break;
            case "villageschool":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND schoolno = '" + rs.getString("schoolno") + "'";
                break;
            case "villagetemple":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND templeno = '" + rs.getString("templeno") + "'";
                break;
            case "villagewater":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND waterno = '" + rs.getString("waterno") + "'";
                break;
            case "house":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND hcode = '" + rs.getString("hcode") + "'";
                break;
            case "ffc_poi":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND poino = '" + rs.getString("poino") + "'";
                break;
            case "ffc_hospital":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND villcode = '" + rs.getString("villcode") + "' AND hospitalno = '" + rs.getString("hospitalno") + "'";
                break;
            case "visitancrisk":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid") + "' AND pregno = '" + rs.getString("pregno") + "' AND ancriskcode = '" + rs.getString("ancriskcode") + "'";
                break;
            case "person":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid") + "'";
                break;
            case "visitancpregnancy":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid") + "' AND pregno = '" + rs.getString("pregno") + "'";
                break;
            case "persongrow":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid") + "' AND growcode = '" + rs.getString("growcode") + "'";
                break;
            case "personbehavior":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "women":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "personunable1type":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid")+"' AND typecode = '" + rs.getString("typecode") + "'";
                break;
            case "persondeath":
                 value = " WHERE pcucodeperson ='" + rs.getString("pcucodeperson") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "ncd_person":
                 value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "ncd_person_ncd":
                value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "ncd_person_ncd_hist":
                value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "ncd_person_ncd_hist_detail":
                value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND pid = '" + rs.getString("pid")+"'";
                break;
            case "ncd_person_ncd_screen":
            {
                value = " WHERE pcucode ='" + rs.getString("pcucode") + "' AND pid = '" + rs.getString("pid")+"' AND no = '"+rs.getString("no")+"'";
                System.out.println("insite where");
                break;
            }
        }
           return value;
       }

       public void insertVisitGroup(String visitInsert, int visitMaxNew) throws SQLException{
           this.updateFamilyplan(visitInsert, visitMaxNew);
           this.update506addr(visitInsert, visitMaxNew);
           this.updateApg(visitInsert, visitMaxNew);
           this.updateBabypg(visitInsert, visitMaxNew);
           this.updateCcbp(visitInsert, visitMaxNew);
           this.updateHvisit(visitInsert, visitMaxNew);
           this.updatePg(visitInsert, visitMaxNew);
           this.updateVisitScreenSpecialDisease(visitInsert, visitMaxNew);
           this.updateDental(visitInsert, visitMaxNew);
           this.updateLabBlood(visitInsert, visitMaxNew);
           this.updateOldter(visitInsert, visitMaxNew);
           this.updateEpi(visitInsert, visitMaxNew);
           this.updateDentalcheck(visitInsert, visitMaxNew);
           this.updateNutrition(visitInsert, visitMaxNew);
           this.updateVisitdiagAppoi(visitInsert, visitMaxNew);
           //this.updateVisiNcdPersonNcdScreen(visitInsert, visitMaxNew);
       }

       public void updateOtherGroup() throws SQLException, Exception{
           this.updatePerson();
           this.updatePersongrow();
           this.updatePersonunableGroup();
           this.updateWomen();
       }

       private boolean insertVisitAncRisk(String visitno) throws SQLException{
           System.out.println("Insert Visitancrisk");
           String query = "SELECT distinct visitancrisk.* FROM (SELECT pcucodeperson,pid,pregno FROM visitanc WHERE visitno = '"+visitno+"')t1 "
                   + "JOIN visitancrisk ON t1.pcucodeperson = visitancrisk.pcucodeperson AND t1.pid = visitancrisk.pid AND t1.pregno = visitancrisk.pregno";
           System.out.println(query);
           ResultSet rs = Service.Service.SQLiteConnection.getResultSet(query);
           try {
               
               int count = 0;
               boolean checkupdate = false;
                while(rs.next()){
                    ArrayList<ResultSet> data = new ArrayList<>();
                    //System.out.println("Ancriskcode :"+rs.getString("ancriskcode"));
                    System.out.println("count round :"+ count++);
                    String query1 = "SELECT visitancrisk.pid FROM visitancrisk " + this.getQueryWhereCondition("visitancrisk", rs);
                    System.out.println(query1);
                    ResultSet rss = Service.Service.connectionSQL.getResultSet(query1);
                    
                    if(rss.next()){
                        System.out.println("Find Data");
                        //data.add(rs);
                    }else{
                        System.out.println("Not Find Add Data");
                        System.out.println(rs.getString("ancriskcode"));
                        data.add(rs);
                    }
                   this.InsertData("visitancrisk", data);
                   
                }
                return true;
           } catch (SQLException ex) {
                Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.getErrorCode());
                return false;
           }
       }

       public int checkUpdateGisGroup(ConnectDatabase.ConnectSQLite connection,String tableName) throws SQLException{
           int countUpdate = 0;
           String lastUpdate = this.getLastUpdate();
           Statement stmt = Service.Service.connectionSQL.connection.createStatement();
           String queryNewUpdate = "SELECT * FROM "+tableName+" WHERE dateupdate > '"+lastUpdate+"'";
           System.out.println(queryNewUpdate);
           ResultSet NewUpdateRs = connection.getResultSet(queryNewUpdate);
           if(connection.SQLiteConnection.isClosed()){
               System.out.println("closeeeee");
           }else{
               System.out.println("opennnnnn");
           }
            while(NewUpdateRs.next()){
                    String query = "SELECT * FROM "+tableName+this.getQueryWhereCondition(tableName, NewUpdateRs);
                    System.out.println(query);
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next()){
                        countUpdate++;
                    }else{
                        countUpdate++;
                    }
            }
           System.out.println(tableName+ " Update Count : "+ countUpdate);
           return countUpdate;
       }
       
       public int sumGisUpdateCount(ConnectDatabase.ConnectSQLite connection){
        int count=0;
        try {
            count +=checkUpdateGisGroup(connection,"villagebusiness");
            count +=checkUpdateGisGroup(connection,"villagetemple");
            count +=checkUpdateGisGroup(connection,"villagewater");
            count +=checkUpdateGisGroup(connection,"villageschool");
            count +=checkUpdateGisGroup(connection,"ffc_poi");
            count +=checkUpdateGisGroup(connection,"ffc_hospital");
            count +=checkUpdateGisGroup(connection,"house");
        } catch (SQLException ex) {
            Logger.getLogger(ConvertSQLiteToSQL.class.getName()).log(Level.SEVERE, null, ex);

        }
        return count;
       }

       public void addCount(String tableName){
        switch (tableName) {
            case "women":
                Service.Service.updateCount.womanCount++;
                break;
            case "ffc_visitspecialperson":
                personUpdateCount++;
                break;
        }
       }

       public void insertVisitCount() throws SQLException{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.US);
           Date d = new Date();
           String insertQuery = "INSERT INTO ffc_androidvisit (datesurvey,amount) VALUES ('"+sdf.format(d)+"','"+this.visitUpdateCount+"')";
           System.out.println(insertQuery);
           Statement stm = Service.Service.connectionSQL.connection.createStatement();
           stm.executeUpdate(insertQuery);
           
       }

       public void syncPidAndroid(String tableName,String oldPid,String newPid) throws SQLException{
           String query = "UPDATE "+tableName+" SET pid = '"+newPid+"' WHERE pid = '"+oldPid+"'";
           Statement stm = this.SQLiteConnection.SQLiteConnection.createStatement();
           stm.executeUpdate(query);
       }

       public void syncHcodeAndroid(String tableName,String oldHcode,String newHcode) throws SQLException{
           String query = "UPDATE "+tableName+" SET hcode = '"+newHcode+"' WHERE hcode = '"+oldHcode+"'";
           Statement stm = this.SQLiteConnection.SQLiteConnection.createStatement();
           stm.executeUpdate(query);
       }

       private void syncPidAndroidGroup(String oldPid,String newPid) throws SQLException{
           this.syncPidAndroid("visit", oldPid, newPid);
           this.syncPidAndroid("women", oldPid, newPid);
           this.syncPidAndroid("house", oldPid, newPid);
           this.syncPidAndroid("personbehavior", oldPid, newPid);
           //this.syncPidAndroid("personaddresscontract", oldPid, newPid);
           this.syncPidAndroid("persontype", oldPid, newPid);
           this.syncPidAndroid("personchronic", oldPid, newPid);
           this.syncPidAndroid("personchronicfamily", oldPid, newPid);
           this.syncPidAndroid("personhabit", oldPid, newPid);
           this.syncPidAndroid("persongrow", oldPid, newPid);
           this.syncPidAndroid("personunable", oldPid, newPid);
           this.syncPidAndroid("persondeath", oldPid, newPid);
       }

       private void syncHcodeAndroidGroup(String oldPid,String newPid) throws SQLException{
           this.syncHcodeAndroid("houseanimal", oldPid, newPid);
           this.syncHcodeAndroid("housegenusculex", oldPid, newPid);
           this.syncHcodeAndroid("housevesselwater", oldPid, newPid);
       }

       private void updatepersonunable(String tablename) throws SQLException{
           Statement stm1 = Service.Service.connectionSQL.connection.createStatement();
           String selectquery = "SElECT * FROM "+tablename;
           ResultSet rs1 = Service.Service.connectionSQL.getResultSet(selectquery);
           String deletequery = "DELETE FROM "+tablename;
           System.out.println(deletequery);
           stm1.executeUpdate(deletequery);
           String selectallQuery = "SELECT * FROM "+tablename;
           Statement stm2 = this.SQLiteConnection.SQLiteConnection.createStatement();
           ResultSet rs = stm2.executeQuery(selectallQuery);
           if(!this.InsertDataResultSet(tablename, rs)){
               stm1.executeUpdate(deletequery);
               this.InsertDataResultSet(tablename, rs1);
           }
       }

       

       public void updateGisGroup() throws SQLException, Exception{
           Service.Service.updateCount.houseCount = this.updateAndInsertHouse();
           Service.Service.updateCount.villbusinessCount = this.updateVillageOtherTable("villagebusiness", "dateupdate");
           Service.Service.updateCount.villschoolCount = this.updateVillageOtherTable("villageschool", "dateupdate");
           Service.Service.updateCount.villtempleCount = this.updateVillageOtherTable("villagetemple", "dateupdate");
           Service.Service.updateCount.villwaterCount = this.updateVillageOtherTable("villagewater", "dateupdate");
           Service.Service.updateCount.hospitalCount = this.updateVillageOtherTable("ffc_hospital", "dateupdate");
           Service.Service.updateCount.poiCount = this.updateVillageOtherTable("ffc_poi", "dateupdate");
       }
       
    }
