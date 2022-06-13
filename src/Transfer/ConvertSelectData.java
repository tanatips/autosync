/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;

import ConnectDatabase.SqlQuery;
import FFC_Form.MainForm;
import MySQLiteConverter.MySQLiteConverter;
import auto_sync_v2.Main;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class ConvertSelectData extends ConvertDatabase{
    
   private String convertData;
   private int selectIndexType;
   private ResultSet personResultSet;
   private ResultSet personbehaviorResultSet;
   private ResultSet houseResultSet;
   private ResultSet personChronicResultSet;
   private ResultSet visitResultSet;
   private ResultSet visitDiagResultSet;
   private ResultSet visitDrugResultSet;
   
   public int convertMySQLtoSQLiteOtherType() throws SQLException, UnsupportedEncodingException {
      int maxVisitDevice = 0;
     
      checkTypeData();
      System.out.println("JhcisToAndroid.getMaxInVillageWillInDevice()");
      System.out.println("Service.maxIndevice"+maxVisitDevice);
     
      System.out.println("Service.maxIndevice"+maxVisitDevice);
      System.out.println("-----------------ConVert-------------------");
      //สร้าง object สำหรับการ sync ข้อมูลระหว่าง MySQL กับ SQLite
      MySQLiteConverter mslc = new MySQLiteConverter();
      //สร้างโฟลเดอร์ปลายทางสำหรับ sqlite file "D:\test\"
      File test = new File(super.comDrive +super.comTempDatabase);
      System.out.println(" File test :"+super.comDrive +super.comTempDatabase);
      if (!test.exists()) {
          try {
              test.mkdirs();
          } catch (ExceptionInInitializerError ex) {
              ex.printStackTrace();
          }
      }
        //url สำหรับเชื่อมต่อ sqlite  "D:\test\rJHCIS.db"
      String sqliteurl = "jdbc:sqlite:"+super.comDrive
              + super.comTempDatabase+ "mJHCIS.db";
      System.out.println("sqliteurl :"+"jdbc:sqlite:"+super.comDrive
              + super.comTempDatabase+ "mJHCIS.db");
        //url สำหรับเชื่อมต่อ mySQL
      String mysqlurl = "jdbc:mysql://"+super.driverDataBase.getServer()+":"+super.driverDataBase.getPort()+"/"+super.driverDataBase.getServerName();
        // String mysqlurl = "jdbc:mysql://localhost:3333/jhcisdb";
        //เปิดการเชื่อมต่อกับ  MySQL และ SQLite
      mslc.openConnection(mysqlurl, super.driverDataBase.getUserName(), super.driverDataBase.getPassword(), sqliteurl);
        //convert ข้อมูลจาก MySQL -> SQLite ทั้ง database       
      String[] table = mslc.mysqlListTableName();
      if (table != null) {
          int count = table.length;
          System.out.println("All table in Mysql is "+ count);
          for (int i = 0; i < count; i++) {
              if (!table[i].equalsIgnoreCase("visitepi")) {
                  System.out.println(table[i]+"index"+i);
                  if(table[i].equals("cdisease")               ||table[i].equals("cdistrict")
                        ||table[i].equals("persontype")     ||table[i].equals("village")
                        ||table[i].equals("cancrisk")       ||table[i].equals("canimaltype")
                        ||table[i].equals("chabit")         ||table[i].equals("crightgroup")
                        ||table[i].equals("cwatertype")     ||table[i].equals("cwaterowner")
                        ||table[i].equals("cclinic")        ||table[i].equals("coccupa")
                        ||table[i].equals("cpersonincomplete")||table[i].equals("cpersonneed")
                        ||table[i].equals("cpersonproblem")||table[i].equals("chospital")
                        ||table[i].equals("chousevesselwater")||table[i].equals("cscreenotherdisease")
                        ||table[i].equals("houseanimal")
                        ||table[i].equals("housevesselwater")||table[i].equals("personhabit")
                        ||table[i].equals("persontemplemem")
                        ||table[i].equals("user")           ||table[i].equals("cnation")
                        ||table[i].equals("cpersontype")    ||table[i].equals("cprovince")
                        ||table[i].equals("cright")         ||table[i].equals("cstatus")
                        ||table[i].equals("csubdistrict")   ||table[i].equals("cfamilyposition")
                        ||table[i].equals("person")||table[i].equals("house")
                        ||table[i].equals("personbehavior")||table[i].equals("personchronic")
                        ||table[i].equals("personchronicfamily")||table[i].equals("persondeath")
                        ||table[i].equals("visit")||table[i].equals("cdrug")
                        ||table[i].equals("visitdiag")||table[i].equals("visitdrug")
                        ||table[i].equals("ceducation")||table[i].equals("cdiseasechronic")
                        ||table[i].equals("sysdoctorexplain")
                        ||table[i].equals("sysdrugdose")
                        ||table[i].equals("syshealthsuggest")){
                      if(!table[i].equals("house")&&!table[i].equals("person")
                              &&!table[i].equals("personbehavior")&&!table[i].equals("personchronic")
                              &&!table[i].equals("personchronicfamily")&&!table[i].equals("persondeath")
                              &&!table[i].equals("visit")&&!table[i].equals("visitdiag")
                              &&!table[i].equals("visitdrug")){
                          // mslc.mysqlConvertToSqlite(table[i]);
                          java.sql.Statement stm = mslc.MySQLconnection.createStatement();
                          ResultSet rs = stm.executeQuery("select * from "+table[i]);
                          mslc.mysqlConvertToSqlite(rs);
                          rs.close();
                          JhcisToAndroid.progressCount+=2;
                      }else{
                          if(table[i].equals("person")){
                              //personCount = countVillageInformation(personResultSet);
                              System.out.println("====================== " + personCount + " =====================");
                              this.personResultSet.beforeFirst();
                              mslc.mysqlConvertToSqlite(this.personResultSet);
                              JhcisToAndroid.progressCount+=2;
                          }else
                              if(table[i].equals("house")){
                                  //houseCount = countVillageInformation(houseResultSet);
                                  System.out.println("====================== " + houseCount + " =====================");
                                  this.personResultSet.beforeFirst();
                                  mslc.mysqlConvertToSqlite(this.houseResultSet);
                                  JhcisToAndroid.progressCount+=2;
                              }else
                                  if(table[i].equals("personbehavior")){
                                      mslc.mysqlConvertToSqlite(this.personbehaviorResultSet);
                                      JhcisToAndroid.progressCount+=2;
                                  }else
                                      if(table[i].equals("personchronic")){
                                          mslc.mysqlConvertToSqlite(this.personChronicResultSet);
                                          JhcisToAndroid.progressCount+=2;
                                      }else
                                          if(table[i].equals("personchronicfamily")){
                                            /*  java.sql.Statement stm = mslc.getMySQLsStatement();
                                              ResultSet rs = stm.executeQuery(sqlPersonchronicfamily);
                                              mslc.mysqlConvertToSqlite(rs);*/
                                              //mslc.mysqlConvertToSqlite(this.houseResultSet);
                                              JhcisToAndroid.progressCount+=2;
                                          }else
                                              if(table[i].equals("persondeath")){
                                                 /* java.sql.Statement stm = mslc.getMySQLsStatement();
                                                  ResultSet rs = stm.executeQuery(sqlPersondeath);
                                                  mslc.mysqlConvertToSqlite(rs);*/
                                                  JhcisToAndroid.progressCount+=2;
                                              }
                                              else
                                                  if(table[i].equals("visit")){
                                                      //visitCount = countVillageInformation(this.visitResultSet);
                                                      System.out.println("====================== " + visitCount + " =====================");
                                                      this.visitResultSet.beforeFirst();
                                                      mslc.mysqlConvertToSqlite(this.visitResultSet);
                                                      JhcisToAndroid.progressCount+=2;
                                                  }else{
                                                      if(table[i].equals("visitdiag")){
                                                          //visitCount = countVillageInformation(this.visitDiagResultSet);
                                                          System.out.println("====================== " + visitCount + " =====================");
                                                          this.visitDiagResultSet.beforeFirst();
                                                          mslc.mysqlConvertToSqlite(this.visitDiagResultSet);
                                                          JhcisToAndroid.progressCount+=2;
                                                      }else{
                                                      if(table[i].equals("visitdrug")){
                                                          //visitCount = countVillageInformation(this.visitDrugResultSet);
                                                          System.out.println("====================== " + visitCount + " =====================");
                                                          this.visitDrugResultSet.beforeFirst();
                                                          mslc.mysqlConvertToSqlite(this.visitDrugResultSet);
                                                          JhcisToAndroid.progressCount+=2;
                                                      }
                                                  }
                                              }
                      }
                  }
              } else {
                  continue;
              }
              
          }
      }
      mslc.closeConntion();
      System.out.println("Enddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
      return maxVisitDevice;
  }
   
  private void checkTypeData()
    {
        SqlQuery sqlQuery = new SqlQuery();
        
        
        switch(this.selectIndexType)
        {
            case 1 :      
                break;
            case 2 :
                break;
            case 3 : try {
                        //sqlQuery.getChronicQuery().initChronicVariable("01", "103");
                        this.personResultSet = sqlQuery.getChronicQuery().getResultSetPersonChronicQuery();
                        this.personbehaviorResultSet = sqlQuery.getChronicQuery().getResultSetPersonBehaviorChronicQuery();
                        this.houseResultSet = sqlQuery.getChronicQuery().getResultSetHouseChronicQuery();
                        this.personChronicResultSet = sqlQuery.getChronicQuery().getResultSetPersonChronicChronicQuery();
                        this.visitResultSet = sqlQuery.getChronicQuery().getResultSetVisitChronicQuery();
                        this.visitDiagResultSet = sqlQuery.getChronicQuery().getResultSetVisitDiagChronicQuery();
                        this.visitDrugResultSet = sqlQuery.getChronicQuery().getResultSetVisitDrugChronicQuery();
                     } catch (SQLException ex) {
                        Logger.getLogger(ConvertDatabase.class.getName()).log(Level.SEVERE, null, ex);
                     }
                break;
        }
    }
  
  public void setSelectIndexType(int index)
  {
      this.selectIndexType = index;
  }
  
  public void getMaxvisitIndevice()
  {
      //String query = "SELECT MAX(visitno) FROM " + this.visitResultSet;
      
      //while()
    //return ;  
  }
  
  public void setConvertData(ArrayList<String> convertData)
  {
      for(int i=0; i< convertData.size(); i++)
      {
          if(i != convertData.size()-1)
            this.convertData += convertData.get(i) + ",";
          else
            this.convertData += convertData.get(i);
      }
  }
}
