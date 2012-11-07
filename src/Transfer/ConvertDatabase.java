/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;

/**
 *
 * @author Administrator
 */
import ConnectDatabase.DriverDataBase;
import ConnectDatabase.SqlQuery;
import FFC_Form.MainForm;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import MySQLiteConverter.MySQLiteConverter;
import Security.MdManager;

public class ConvertDatabase {
    //  private static String []villNo;

    public static int count = 0;
    static boolean person;
    static boolean personbehavioir;
    static boolean house;
    static boolean visit;
    static boolean visitdiag;
    static boolean visitdrug;
    int personCount;
    int houseCount;
    int visitCount;
    //private ArrayList<String> houseList = new ArrayList<String>();
    //private String houseList = "";
   // private String villageList = "";
    private ArrayList<String> houseList = new ArrayList<String>();
    private ArrayList<String> villageList = new ArrayList<String>();
    public DriverDataBase driverDataBase;
    public String comDrive;
    public String comTempDatabase;
    public String androidDrive;
    public String androidDatabase;

// edit 14/04/2011 start
    private void reset() {

        person = false;
        personbehavioir = false;
        house = false;
        visit = false;
        visitdiag = false;
        visitdrug = false;


        driverDataBase = null;

        comDrive = null;
        comTempDatabase = null;
        androidDrive = null;
        androidDatabase = null;
    }
    //edit end

    public void initConverter(String comDrive, String comTmpDatabase,
            String androidDrive, String androidDatabase, DriverDataBase driverDataBase) {
        reset();
        this.comDrive = comDrive;
        this.comTempDatabase = comTmpDatabase;
        this.androidDrive = androidDrive;
        this.androidDatabase = androidDatabase;
        this.driverDataBase = driverDataBase;

    }

    public void setAndroidDatabase(String androidDatabase) {
        this.androidDatabase = androidDatabase;
    }

    public void setAndroidDrive(String androidDrive) {
        this.androidDrive = androidDrive;
    }

    public void setComDrive(String comDrive) {
        this.comDrive = comDrive;
    }

    public void setComTempDatabase(String comTempDatabase) {
        this.comTempDatabase = comTempDatabase;
    }

    public void setDriverDataBase(DriverDataBase driverDataBase) {
        this.driverDataBase = driverDataBase;
    }

    /*public void setHouseAndVillageList(String village, String house) {
        this.houseList = house;
        this.villageList = village;
    }*/

    public void setHouseAndVillageList(ArrayList<String> village, ArrayList<ArrayList<String>> house){
        this.villageList = village;
        this.houseList = this.setHouseString(house);
    }

    public int convertMySQLtoSQLite() throws SQLException, UnsupportedEncodingException {
        JhcisToAndroid jhcisToAndroid = new JhcisToAndroid();
        System.out.println(this.houseList);
        System.out.println(this.villageList);
        SqlQuery sql = new SqlQuery();
        SqlQuery.Query query = sql.getQuery();
        //query.initValues(this.houseList, this.villageList);
        int maxVisitDevice = 0;
        System.out.println("JhcisToAndroid.getMaxInVillageWillInDevice()");
        System.out.println("Service.maxIndevice" + maxVisitDevice);
        //maxVisitDevice = this.getMaxVillageInDevice();
        System.out.println("Service.maxIndevice" + maxVisitDevice);
        System.out.println("-----------------ConVert-------------------");
        //สร้าง object สำหรับการ sync ข้อมูลระหว่าง MySQL กับ SQLite
        MySQLiteConverter mslc = new MySQLiteConverter();
        //สร้างโฟลเดอร์ปลายทางสำหรับ sqlite file "D:\test\"
        File test = new File(comDrive + comTempDatabase);
        System.out.println(" File test :" + comDrive + comTempDatabase);
        if (!test.exists()) {
            try {
                test.mkdirs();
            } catch (ExceptionInInitializerError ex) {
                ex.printStackTrace();
            }
        }
        //url สำหรับเชื่อมต่อ sqlite  "D:\test\rJHCIS.db"
        String sqliteurl = "jdbc:sqlite:" + comDrive
                + comTempDatabase + "mJHCIS.db";
        System.out.println("sqliteurl :" + "jdbc:sqlite:" + comDrive
                + comTempDatabase + "mJHCIS.db");
        //url สำหรับเชื่อมต่อ mySQL
        String mysqlurl = "jdbc:mysql://" + driverDataBase.getServer() + ":" + driverDataBase.getPort() + "/" + driverDataBase.getServerName();
        // String mysqlurl = "jdbc:mysql://localhost:3333/jhcisdb";
        //เปิดการเชื่อมต่อกับ  MySQL และ SQLite
        mslc.openConnection(mysqlurl, driverDataBase.getUserName(), driverDataBase.getPassword(), sqliteurl);
        //convert ข้อมูลจาก MySQL -> SQLite ทั้ง database       
        String[] table = mslc.mysqlListTableName();
        if (table != null) {
            int count = table.length;
            System.out.println("All table in Mysql is " + count);
            for (int i = 0; i < count; i++) {
               // if (!table[i].equalsIgnoreCase("visitepi")) {
                    System.out.println(table[i] + "index" + i);
                           if(
                              table[i].equals("cdisease")         ||table[i].equals("cdistrict")
                            ||table[i].equals("persontype")       ||table[i].equals("village")
                            ||table[i].equals("cancrisk")         ||table[i].equals("canimaltype")
                            ||table[i].equals("chabit")           ||table[i].equals("crightgroup")
                            ||table[i].equals("cwatertype")       ||table[i].equals("cwaterowner")
                            ||table[i].equals("cclinic")          ||table[i].equals("coccupa")
                            ||table[i].equals("cpersonincomplete")||table[i].equals("cpersonneed")
                            ||table[i].equals("cpersonproblem")   ||table[i].equals("chospital")
                            ||table[i].equals("chousevesselwater")||table[i].equals("cscreenotherdisease")
                            ||table[i].equals("houseanimal")      ||table[i].equals("housevesselwater")
                            ||table[i].equals("personhabit")      ||table[i].equals("persontemplemem")
                            ||table[i].equals("user")             ||table[i].equals("cnation")
                            ||table[i].equals("cpersontype")      ||table[i].equals("cprovince")
                            ||table[i].equals("cright")           ||table[i].equals("cstatus")
                            ||table[i].equals("csubdistrict")     ||table[i].equals("cfamilyposition")
                            ||table[i].equals("person")           ||table[i].equals("house")
                            ||table[i].equals("personbehavior")   ||table[i].equals("personchronic")
                            ||table[i].equals("personchronicfamily")||table[i].equals("persondeath")
                            ||table[i].equals("visit")            ||table[i].equals("cdrug")
                            ||table[i].equals("visitdiag")        ||table[i].equals("visitdrug")
                            ||table[i].equals("ceducation")       ||table[i].equals("cdiseasechronic")
                            ||table[i].equals("sysdoctorexplain") ||table[i].equals("sysdrugdose")
                            ||table[i].equals("syshealthsuggest") ||table[i].equals("syssymtom")
                            ||table[i].equals("sysdiseasehit")    ||table[i].equals("sysvitalsign")

                      // *** Add NEW table to android 2012-01-06 for FFC+ Only
                            ||table[i].equals("sysdrughits")         || table[i].equals("ctitle")
                            ||table[i].equals("cgrow")               || table[i].equals("chomehealthtype")
                            ||table[i].equals("syshomehealth1")      || table[i].equals("syshomehealth2")
                            ||table[i].equals("syshomehealth3")      || table[i].equals("chealthperformance")

                            ||table[i].equals("visitanc")            ||table[i].equals("visitancdeliver")
                            ||table[i].equals("visitancdeliverchild")||table[i].equals("visitancmothercare")
                            ||table[i].equals("visitancpregnancy")   ||table[i].equals("visitancrisk")
                            ||table[i].equals("visitbabycare")       ||table[i].equals("visitclinic")
                            ||table[i].equals("visitcompsocialright")||table[i].equals("visitcounseling")
                            ||table[i].equals("visitdentalcheck")
                            ||table[i].equals("visitdiag506address") ||table[i].equals("visitepi")
                            ||table[i].equals("visitepiappoint")     ||table[i].equals("visitfp")
                            ||table[i].equals("visithealthcheck")    ||table[i].equals("visithomehealthindividual")
                            ||table[i].equals("visitlabblood")       ||table[i].equals("visitlabcancer")
                            ||table[i].equals("visitlabchcyhembmsse")||table[i].equals("visitlabsugarblood")
                            ||table[i].equals("visitnutrition")      ||table[i].equals("women")

                            ||table[i].equals("ncd_person")          ||table[i].equals("ncd_person_ncd")
                            ||table[i].equals("ncd_person_ncd_hist") ||table[i].equals("ncd_person_ncd_hist_detail")
                            ||table[i].equals("ncd_person_ncd_screen")

                            ||table[i].equals("villagebusiness")       ||table[i].equals("villageschool")
                            ||table[i].equals("villagetemple")         ||table[i].equals("villagewater")
                            ||table[i].equals("villagecommunity")      ||table[i].equals("housegenusculex")
                        // ** Bus review for Map View 2012-05-09
                            ||table[i].equals("personunable")      ||table[i].equals("cdisease506")
                            ||table[i].equals("cschoolclass")      ||table[i].equals("cschooldepend")
                            ||table[i].equals("cpersonincomplete")  ||table[i].equals("cpersonproblem")
                            ||table[i].equals("ffc_poi")            ||table[i].equals("ffc_hospital")
                            ||table[i].equals("cbusiness")          ||table[i].equals("user_tab")
                         // ** Add 2012-05-24
                            ||table[i].equals("creligion")
                         // ** for check province code
                            ||table[i].equals("personaddresscontact")
                            ||table[i].equals("ffc_cpoitype")

                        //  ** Add 2012-07-16
                            ||table[i].equals("persongrow") || table[i].equals("visitdiagappoint")
                       // ** Add 2012-07-17
                            ||table[i].equals("ffc_visitoldter") || table[i].equals("ffc_visitspecialperson")
                            ||table[i].equals("visitscreenspecialdisease") || table[i].equals("cpersonhelp")

                          // ** Add 2012-08-24
                            ||table[i].equals("visitepiappoint")
                            ||table[i].equals("personunable1type") || table[i].equals("personunable2prob")
                            ||table[i].equals("personunable3need") || table[i].equals("personunable4help")
                            ||table[i].equals("cpersonincomplete") || table[i].equals("cpersonproblem")
                            ||table[i].equals("cpersonneed")

                         // ** Add 2012-09-06
                            ||table[i].equals("sysdrugformula") || table[i].equals("sysdrugformuladetail")
                            ||table[i].equals("sysdrugformuladetaildiag")

                         // ** Add 2012-09-12
                            ||table[i].equals("cdentalproject") ||table[i].equals("visitdrugdental")
                            ||table[i].equals("visitdrugdentaldiag")

                         // ** Add 2012-10-09
                            ||table[i].equals("cfamilyrelation")

                            ){
                                if (!table[i].equals("house") && !table[i].equals("person")
                                && !table[i].equals("personbehavior") && !table[i].equals("personchronic")
                                && !table[i].equals("visit") && !table[i].equals("visitdiag")
                                && !table[i].equals("visitdrug")&& !table[i].equals("cdisease")
                                && !table[i].equals("visitfp") && !table[i].equals("visitlabcancer")
                                && !table[i].equals("visitpregnancy") /*&& !table[i].equals("visitancrisk")*/
                                && !table[i].equals("visitlabblood") && !table[i].equals("visitancdeliver")
                                && !table[i].equals("visitancmothercare") && !table[i].equals("visitbabycare")
                                && !table[i].equals("visitnutrition") && !table[i].equals("visitepi") 
                                && !table[i].equals("visitdiagappoint") && !table[i].equals("visitdentalcheck")
                                && !table[i].equals("visitdiag506address") && !table[i].equals("visithomehealthindividual")
                                && !table[i].equals("visitscreenspecialdisease") && !table[i].equals("visitanc")
                                && !table[i].equals("visitepiappoint")
                                ) {
                            // mslc.mysqlConvertToSqlite(table[i]);
                                java.sql.Statement stm = mslc.getMySQLsStatement();
                                ResultSet rs = stm.executeQuery("select * from " + table[i]);
                                mslc.mysqlConvertToSqlite(rs);
                                rs.close();
                                jhcisToAndroid.calculateProgressCount(1);
                                MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
                        } else {
                                    if(!table[i].equals("cdisease")){
                                        for(int j=0; j<this.villageList.size(); j++){
                                            if(j==0){
                                                System.out.println("houseList : "+houseList.get(j));
                                                ResultSet rs = query.getRsQuery(table[i],this.villageList.get(j),this.houseList.get(j));
                                                countVillageInformation(table[i],rs);
                                                System.out.println(table[i]);

                                                rs.beforeFirst();
                                                mslc.mysqlConvertToSqlite(rs);
                                                System.out.println("rowCount : "+ rs.getRow());
                                                rs.close();
                                            }else{
                                                System.out.println("houseList : "+houseList.get(j));
                                                ResultSet rs = query.getRsQuery(table[i],this.villageList.get(j),this.houseList.get(j));
                                                countVillageInformation(table[i],rs);

                                                rs.beforeFirst();
                                                mslc.insertDataToTable(rs);
                                                rs.close();
                                            }
                                        }
                               }else{
                                    ResultSet rs = query.getcdisease();
                                    rs.beforeFirst();
                                    mslc.mysqlConvertToSqlite(rs);
                                    rs.close();
                               }
                                jhcisToAndroid.calculateProgressCount(1);
                                MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
                        }
                    }
               /* } else {
                    continue;
                }*/

            }
        }
        mslc.closeConntion();
        System.out.println("Enddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        return maxVisitDevice;
    }


    public int convertMySQLtoSQLiteAll() throws SQLException, UnsupportedEncodingException {
        JhcisToAndroid jhcisToAndroid = new JhcisToAndroid();
        System.out.println(this.houseList);
        System.out.println(this.villageList);
        SqlQuery sql = new SqlQuery();
        SqlQuery.Query query = sql.getQuery();
        //query.initValues(this.houseList, this.villageList);
        int maxVisitDevice = 0;
        System.out.println("JhcisToAndroid.getMaxInVillageWillInDevice()");
        System.out.println("Service.maxIndevice" + maxVisitDevice);
        //maxVisitDevice = this.getMaxVillageInDevice();
        System.out.println("Service.maxIndevice" + maxVisitDevice);
        System.out.println("-----------------ConVert-------------------");
        //สร้าง object สำหรับการ sync ข้อมูลระหว่าง MySQL กับ SQLite
        MySQLiteConverter mslc = new MySQLiteConverter();
        //สร้างโฟลเดอร์ปลายทางสำหรับ sqlite file "D:\test\"
        File test = new File(comDrive + comTempDatabase);
        System.out.println(" File test :" + comDrive + comTempDatabase);
        if (!test.exists()) {
            try {
                test.mkdirs();
            } catch (ExceptionInInitializerError ex) {
                ex.printStackTrace();
            }
        }
        //url สำหรับเชื่อมต่อ sqlite  "D:\test\rJHCIS.db"
        String sqliteurl = "jdbc:sqlite:" + comDrive
                + comTempDatabase + "mJHCIS.db";
        System.out.println("sqliteurl :" + "jdbc:sqlite:" + comDrive
                + comTempDatabase + "mJHCIS.db");
        //url สำหรับเชื่อมต่อ mySQL
        String mysqlurl = "jdbc:mysql://" + driverDataBase.getServer() + ":" + driverDataBase.getPort() + "/" + driverDataBase.getServerName();
        // String mysqlurl = "jdbc:mysql://localhost:3333/jhcisdb";
        //เปิดการเชื่อมต่อกับ  MySQL และ SQLite
        mslc.openConnection(mysqlurl, driverDataBase.getUserName(), driverDataBase.getPassword(), sqliteurl);
        //convert ข้อมูลจาก MySQL -> SQLite ทั้ง database
        String[] table = mslc.mysqlListTableName();
        if (table != null) {
            int count = table.length;
            System.out.println("All table in Mysql is " + count);
            for (int i = 0; i < count; i++) {
               // if (!table[i].equalsIgnoreCase("visitepi")) {
                    System.out.println(table[i] + "index" + i);
                           if(
                              table[i].equals("cdisease")         ||table[i].equals("cdistrict")
                            ||table[i].equals("persontype")       ||table[i].equals("village")
                            ||table[i].equals("cancrisk")         ||table[i].equals("canimaltype")
                            ||table[i].equals("chabit")           ||table[i].equals("crightgroup")
                            ||table[i].equals("cwatertype")       ||table[i].equals("cwaterowner")
                            ||table[i].equals("cclinic")          ||table[i].equals("coccupa")
                            ||table[i].equals("cpersonincomplete")||table[i].equals("cpersonneed")
                            ||table[i].equals("cpersonproblem")   ||table[i].equals("chospital")
                            ||table[i].equals("chousevesselwater")||table[i].equals("cscreenotherdisease")
                            ||table[i].equals("houseanimal")      ||table[i].equals("housevesselwater")
                            ||table[i].equals("personhabit")      ||table[i].equals("persontemplemem")
                            ||table[i].equals("user")             ||table[i].equals("cnation")
                            ||table[i].equals("cpersontype")      ||table[i].equals("cprovince")
                            ||table[i].equals("cright")           ||table[i].equals("cstatus")
                            ||table[i].equals("csubdistrict")     ||table[i].equals("cfamilyposition")
                            ||table[i].equals("person")           ||table[i].equals("house")
                            ||table[i].equals("personbehavior")   ||table[i].equals("personchronic")
                            ||table[i].equals("personchronicfamily")||table[i].equals("persondeath")
                            ||table[i].equals("visit")            ||table[i].equals("cdrug")
                            ||table[i].equals("visitdiag")        ||table[i].equals("visitdrug")
                            ||table[i].equals("ceducation")       ||table[i].equals("cdiseasechronic")
                            ||table[i].equals("sysdoctorexplain") ||table[i].equals("sysdrugdose")
                            ||table[i].equals("syshealthsuggest") ||table[i].equals("syssymtom")
                            ||table[i].equals("sysdiseasehit")    ||table[i].equals("sysvitalsign")

                      // *** Add NEW table to android 2012-01-06 for FFC+ Only
                            ||table[i].equals("sysdrughits")         || table[i].equals("ctitle")
                            ||table[i].equals("cgrow")               || table[i].equals("chomehealthtype")
                            ||table[i].equals("syshomehealth1")      || table[i].equals("syshomehealth2")
                            ||table[i].equals("syshomehealth3")      || table[i].equals("chealthperformance")

                            ||table[i].equals("visitanc")            ||table[i].equals("visitancdeliver")
                            ||table[i].equals("visitancdeliverchild")||table[i].equals("visitancmothercare")
                            ||table[i].equals("visitancpregnancy")   ||table[i].equals("visitancrisk")
                            ||table[i].equals("visitbabycare")       ||table[i].equals("visitclinic")
                            ||table[i].equals("visitcompsocialright")||table[i].equals("visitcounseling")
                            ||table[i].equals("visitdentalcheck")
                            ||table[i].equals("visitdiag506address") ||table[i].equals("visitepi")
                            ||table[i].equals("visitepiappoint")     ||table[i].equals("visitfp")
                            ||table[i].equals("visithealthcheck")    ||table[i].equals("visithomehealthindividual")
                            ||table[i].equals("visitlabblood")       ||table[i].equals("visitlabcancer")
                            ||table[i].equals("visitlabchcyhembmsse")||table[i].equals("visitlabsugarblood")
                            ||table[i].equals("visitnutrition")      ||table[i].equals("women")

                            ||table[i].equals("ncd_person")          ||table[i].equals("ncd_person_ncd")
                            ||table[i].equals("ncd_person_ncd_hist") ||table[i].equals("ncd_person_ncd_hist_detail")
                            ||table[i].equals("ncd_person_ncd_screen")

                            ||table[i].equals("villagebusiness")       ||table[i].equals("villageschool")
                            ||table[i].equals("villagetemple")         ||table[i].equals("villagewater")
                            ||table[i].equals("villagecommunity")      ||table[i].equals("housegenusculex")
                        // ** Bus review for Map View 2012-05-09
                            ||table[i].equals("personunable")      ||table[i].equals("cdisease506")
                            ||table[i].equals("cschoolclass")      ||table[i].equals("cschooldepend")
                            ||table[i].equals("cpersonincomplete")  ||table[i].equals("cpersonproblem")
                            ||table[i].equals("ffc_poi")            ||table[i].equals("ffc_hospital")
                            ||table[i].equals("cbusiness")          ||table[i].equals("user_tab")
                         // ** Add 2012-05-24
                            ||table[i].equals("creligion")
                         // ** for check province code
                            ||table[i].equals("personaddresscontact")
                            ||table[i].equals("ffc_cpoitype")

                        //  ** Add 2012-07-16
                            ||table[i].equals("persongrow") || table[i].equals("visitdiagappoint")
                       // ** Add 2012-07-17
                            ||table[i].equals("ffc_visitoldter") || table[i].equals("ffc_visitspecialperson")
                            ||table[i].equals("visitscreenspecialdisease") || table[i].equals("cpersonhelp")

                          // ** Add 2012-08-24
                            ||table[i].equals("visitepiappoint")
                            ||table[i].equals("personunable1type") || table[i].equals("personunable2prob")
                            ||table[i].equals("personunable3need") || table[i].equals("personunable4help")
                            ||table[i].equals("cpersonincomplete") || table[i].equals("cpersonproblem")
                            ||table[i].equals("cpersonneed")

                         // ** Add 2012-09-06
                            ||table[i].equals("sysdrugformula") || table[i].equals("sysdrugformuladetail")
                            ||table[i].equals("sysdrugformuladetaildiag")

                         // ** Add 2012-09-12
                            ||table[i].equals("cdentalproject") ||table[i].equals("visitdrugdental")
                            ||table[i].equals("visitdrugdentaldiag")

                         // ** Add 2012-10-09
                            ||table[i].equals("cfamilyrelation")

                            ){
                                if (!table[i].equals("house") && !table[i].equals("person")
                                && !table[i].equals("personbehavior") && !table[i].equals("personchronic")
                                && !table[i].equals("visit") && !table[i].equals("visitdiag")
                                && !table[i].equals("visitdrug")&& !table[i].equals("cdisease")
                                && !table[i].equals("visitfp") && !table[i].equals("visitlabcancer")
                                && !table[i].equals("visitpregnancy") /*&& !table[i].equals("visitancrisk")*/
                                && !table[i].equals("visitlabblood") && !table[i].equals("visitancdeliver")
                                && !table[i].equals("visitancmothercare") && !table[i].equals("visitbabycare")
                                && !table[i].equals("visitnutrition") && !table[i].equals("visitepi")
                                && !table[i].equals("visitdentalcheck")
                                && !table[i].equals("visitanc")
                                
                                ) {
                            // mslc.mysqlConvertToSqlite(table[i]);
                                java.sql.Statement stm = mslc.getMySQLsStatement();
                                ResultSet rs = stm.executeQuery("select * from " + table[i]);
                                mslc.mysqlConvertToSqlite(rs);
                                rs.close();
                                jhcisToAndroid.calculateProgressCount(1);
                                MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
                                } else {
                                    if(!table[i].equals("cdisease")){
                                        ResultSet rs = query.getRsQueryAll(table[i]);
                                        countVillageInformation(table[i],rs);
                                        System.out.println(table[i]);

                                        rs.beforeFirst();
                                        mslc.mysqlConvertToSqlite(rs);
                                        System.out.println("rowCount : "+ rs.getRow());
                                        rs.close();
                                    }else{
                                        ResultSet rs = query.getcdisease();
                                        rs.beforeFirst();
                                        mslc.mysqlConvertToSqlite(rs);
                                        rs.close();
                                    }
                                    jhcisToAndroid.calculateProgressCount(1);
                                    MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
                                }
                    }
            }
        }
        mslc.closeConntion();
        System.out.println("Enddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        return maxVisitDevice;
    }

  /*  private void ExcuteQuery(String tableName, ) throws SQLException{
        SqlQuery sql = new SqlQuery();
        SqlQuery.Query query = sql.getQuery();
        for(int j=0; j<this.houseList.size(); j++){
            if(j==0){
            ResultSet rs = query.getRsQuery(tableName,this.villageList.get(j), this.houseList.get(j));
            houseCount = countVillageInformation(rs);

            rs.beforeFirst();
            mslc.mysqlConvertToSqlite(rs);
            rs.close();
            }else{
                ResultSet rs = query.getHouseQuery(this.villageList.get(j), this.houseList.get(j));
                houseCount += countVillageInformation(rs);
                System.out.println("====================== " + houseCount + " =====================");
                rs.beforeFirst();
                mslc.insertDataToTable(rs);
                rs.close();
            }
        }
        //jhcisToAndroid.calculateProgressCount(1);
        MainForm.transForm.setValueTransferProgressBar(JhcisToAndroid.progressCount);
    }*/

    public ArrayList<String> convertSQLiteToMySQL(int maxIndevice, int maxIncom) {

        ArrayList<String> listDataUpdate = new ArrayList<String>();
        try {
            System.out.println("convrtSQLiteToMySQL BEGIN");
            MySQLiteConverter mslc = new MySQLiteConverter();
            System.out.println(androidDrive + androidDatabase);
            File test = new File(androidDrive + androidDatabase);
            if (!test.exists()) {
                try {
                    // test.mkdirs();//สร้างไฟลล์
                    System.out.println("convrtSQLiteToMySQL False");
                    return null;
                } catch (ExceptionInInitializerError ex) {
                    ex.printStackTrace();
                }
            }

            person = false;
            personbehavioir = false;
            house = false;
            visit = false;
            visitdiag = false;
            visitdrug = false;
            // String sqliteurl = "jdbc:sqlite:"+FFCParamiter.pathMobile+":" + File.separator + "AHCIS" + File.separator + "DataBase" + File.separator + "mJHCIS.db";
            String sqliteurl = "jdbc:sqlite:" + androidDrive + androidDatabase;
            // String mysqlurl = "jdbc:mysql://localhost:3333/jhcisdb";
            String mysqlurl = "jdbc:mysql://" + driverDataBase.getServer() + ":" + driverDataBase.getPort() + "/" + driverDataBase.getServerName();
            mslc.openConnection(mysqlurl, driverDataBase.getUserName(), driverDataBase.getPassword(), sqliteurl);
            mslc.setMaxIncom(maxIncom);
            mslc.clearListDataUpdate();
            person = mslc.sqliteSyncUpdateToMySQL("person");
            if (!person) {
                System.out.println("Person No Update");
            }
            personbehavioir = mslc.sqliteSyncUpdateORInsertToMySQL("personbehavior");
            if (!personbehavioir) {
                System.out.println("PersonBehavior No Update");
            }
            System.out.println("-------------------------");
            try {
                house = mslc.sqliteSyncUpdateToMySQL("house");
                if (!house) {
                    System.out.println("house No Update");
                }
            } catch (Exception ex) {
                System.out.println("----------------------------------------------------------------------");
            }
            System.out.println("maxIncom : =" + maxIncom + " maxIndevice : " + maxIndevice);
            visit = mslc.sqliteSpecialSyncToMySQL("visit", "visitno", maxIndevice);
            if (!visit) {
                System.out.println("visit No Update");
            }
            visitdiag = mslc.sqliteSpecialSyncByVisitNumberToMySQL("visitdiag");
            if (!visitdiag) {
                System.out.println("visitdiag No Update");
            }
            visitdrug = mslc.sqliteSpecialSyncByVisitNumberToMySQL("visitdrug");
            if (!visitdrug) {
                System.out.println("visitdrug No Update");
            }

            listDataUpdate = mslc.getListDataUpdate();
            mslc.closeConntion();
        } catch (SQLException ex) {
            Logger.getLogger(ConvertDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDataUpdate;
    }

    public boolean CheckUpdate() {
        System.out.println("=== LIST UPDATE====");
        System.out.println("person :" + person);
        System.out.println("personbehavioir :" + personbehavioir);
        System.out.println("house :" + house);
        System.out.println("visit :" + visit);
        System.out.println("visitdiag :" + visitdiag);
        System.out.println("visitdrug :" + visitdrug);

        //if(!person && !personbehavioir && !house && !visit && !visitdiag && !visitdrug)
        if (!person && !house && !visit && !visitdiag && !visitdrug) {
            return false;
        }
        return true;
    }

    private boolean check(String a) {
        if (a.equals("house") || a.equals("village") || a.equals("person")
                || a.equals("cdisease") || a.equals("personchronic") || a.equals("persondeath")
                || a.equals("personchronicfamily") || a.equals("personbehavior")
                || a.equals("cnation") || a.equals("chospital") || a.equals("cright")
                || a.equals("persontype") || a.equals("cpersontype") || a.equals("cdistrict")
                || a.equals("cprovince") || a.equals("csubdistrict") || a.equals("cstatus")
                || a.equals("ceducation")) {
            return true;
        } else {
            return false;
        }
    }

    public void countVillageInformation(String tableName,ResultSet rs) throws SQLException {
        System.out.println(tableName);
        //int rowCount = 0;
            rs.last();
            if(tableName.equals("person")){
                personCount += rs.getRow();
            }else if(tableName.equals("house")){
                houseCount += rs.getRow();
            }else if(tableName.equals("visit")){
                visitCount += rs.getRow();
            }
        return;
    }

    public void ConvertMysqlToSQLite(String Query, String databaseName) throws SQLException {
        //สร้าง object สำหรับการ sync ข้อมูลระหว่าง MySQL กับ SQLite
        MySQLiteConverter mslc = new MySQLiteConverter();
        //สร้างโฟลเดอร์ปลายทางสำหรับ sqlite file "D:\test\"
        File test = new File(comDrive + comTempDatabase);
        System.out.println(" File test :" + comDrive + comTempDatabase);
        if (!test.exists()) {
            try {
                test.mkdirs();
            } catch (ExceptionInInitializerError ex) {
                ex.printStackTrace();
            }
        }
        //url สำหรับเชื่อมต่อ sqlite  "D:\test\rJHCIS.db"
        String sqliteurl = "jdbc:sqlite:" + comDrive
                + comTempDatabase + databaseName;
        System.out.println("sqliteurl :" + "jdbc:sqlite:" + comDrive
                + comTempDatabase + "uJHCIS.db");
        //url สำหรับเชื่อมต่อ mySQL
        String mysqlurl = "jdbc:mysql://" + driverDataBase.getServer() + ":" + driverDataBase.getPort() + "/" + driverDataBase.getServerName();
        // String mysqlurl = "jdbc:mysql://localhost:3333/jhcisdb";
        //เปิดการเชื่อมต่อกับ  MySQL และ SQLite
        mslc.openConnection(mysqlurl, driverDataBase.getUserName(), driverDataBase.getPassword(), sqliteurl);
        java.sql.Statement stm = mslc.getMySQLsStatement();
        ResultSet rs = stm.executeQuery(Query);
        mslc.mysqlConvertToSqlite(rs);
        rs.close();
        mslc.closeConntion();
    }

    public void hashUserPassword(String databaseName) throws SQLException {
        //สร้าง object สำหรับการ sync ข้อมูลระหว่าง MySQL กับ SQLite

        //สร้างโฟลเดอร์ปลายทางสำหรับ sqlite file "D:\test\"

        File test = new File(comDrive + comTempDatabase);
        System.out.println(" File test :" + comDrive + comTempDatabase);
        if (!test.exists()) {
            try {
                test.mkdirs();
            } catch (ExceptionInInitializerError ex) {
                ex.printStackTrace();
            }
        }

        String sqliteurl = "jdbc:sqlite:" + comDrive
                + comTempDatabase + databaseName;
        String mysqlurl = "jdbc:mysql://" + driverDataBase.getServer() + ":" + driverDataBase.getPort() + "/" + driverDataBase.getServerName();
        MySQLiteConverter mslc = new MySQLiteConverter();
        mslc.openConnection(mysqlurl, driverDataBase.getUserName(), driverDataBase.getPassword(), sqliteurl);

        // 1. ลบข้อมูล user ืั้งหมดออกจาก uJhcis ก่อน
        // 2. query ข้อมูลจาก mysql
        // 3. นำ password มาเข้า sha-256 แล้วจึงเข้า sqlite

        // 1.
        String sql_delete = "DELETE FROM user";
        java.sql.Statement sqlite = mslc.getSQLiteStatement();
        sqlite.execute(sql_delete);

        // 2.
        String query = "SELECT user.pcucode,user.username,user.password,user.officertype FROM user ";
        java.sql.Statement stm = mslc.getMySQLsStatement();
        ResultSet rs = stm.executeQuery(query);
        System.out.println("row = " + rs.getRow());
        if (rs.first()) {

            // 3.
            String sql_insert = "INSERT INTO user (pcucode, username, password, officertype) VALUES (?, ?, ?, ?)";
            java.sql.Connection conn = mslc.getSQLiteConnection();
            java.sql.PreparedStatement prep = conn.prepareStatement(sql_insert);

            do {
                try {
                    String pcu = rs.getString(1);
                    String username = rs.getString(2).trim();
                    String unHashPassword = rs.getString(3).trim();
                    String officertype = rs.getString(4);
                    System.out.println(pcu + ":" + username + ":" + unHashPassword);
                    String password = MdManager.getSha256String(unHashPassword);
                    //System.out.println("pcu="+pcu+" username="+username+" pass="+password);
                    prep.setString(1, pcu);
                    prep.setString(2, username);
                    prep.setString(3, password);
                    prep.setString(4, officertype);
                    prep.addBatch();
                } catch (NullPointerException npe) {
                    System.out.println("Have null user record in database");
                }
            } while (rs.next());
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        }
        //mslc.mysqlConvertToSqlite(rs);
        rs.close();
        stm.close();
        mslc.closeConntion();
    }

    private ArrayList<String> setHouseString(ArrayList<ArrayList<String>> houseList){
        ArrayList<String> houseListString = new ArrayList<String>();
        //System.out.println("houseListSize11111111111111111111111111111111 : "+houseList.size());
        int houseListSize = houseList.size();
        for(int i=0; i<houseListSize; i++){
            String houseTemp = "";
            int houseListsize = houseList.get(i).size();
            for(int j=0; j<houseListsize; j++){
                if(j != houseListsize-1){
                if(houseList.get(i).get(j).toString()!=null)
                houseTemp += houseList.get(i).get(j) + "','";
            }
            else{
                if(houseList.get(i).get(j)!=null)
                houseTemp += houseList.get(i).get(j);
            }
            }
            houseListString.add(houseTemp);
            //System.out.println("houseListSizeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee : "+houseListString.size());
        }
        return houseListString;
    }
}
