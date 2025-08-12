/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AdjustDatabase;

import ConnectDatabase.*;
import FileManager.FileSettingDataBaseFFCManager;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class AdjustDatabaseSql {

    Connection sqlConnection;
    String errorMessage;

    private String create_ffc_hospital = "CREATE TABLE `ffc_hospital` (`pcucode` varchar(9) NOT NULL,`villcode` char(8) NOT NULL,"
            + "`hospitalno` int(11) NOT NULL,`hospitalname` varchar(255) default NULL,`bedtotal` int(4) default NULL,"
            + "`tel` varchar(10) default NULL,`dateupdate` date default NULL,`xgis` varchar(55) default NULL,"
            + "`ygis` varchar(55) default NULL,PRIMARY KEY  (`pcucode`,`villcode`,`hospitalno`)) ENGINE=MyISAM DEFAULT CHARSET=utf8";

    private String create_ffc_poi = "CREATE TABLE `ffc_poi` (`pcucode` varchar(9) NOT NULL default '',`villcode` char(8) NOT NULL default '',"
            + "`poino` int(11) NOT NULL default '0',`poiname` varchar(255) default NULL,`poitype` int(2) default NULL,"
            + "`tel` varchar(10) default NULL,`dateupdate` date default NULL,`xgis` varchar(55) default NULL,"
            + "`ygis` varchar(55) default NULL,PRIMARY KEY  (`pcucode`,`villcode`,`poino`)) ENGINE=MyISAM DEFAULT CHARSET=utf8";

    private String create_ffc_cpoitype = "CREATE TABLE `ffc_cpoitype` (`poitype` int(2) unsigned NOT NULL auto_increment,"
            + "`poigroup` int(2) unsigned zerofill default NULL,`poiname` varchar(50) default NULL,PRIMARY KEY  (`poitype`)) "
            + "ENGINE=MyISAM  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=41 ;";

    private String create_ffc_androidvisit = "CREATE TABLE `ffc_androidvisit` (`id` int(11) NOT NULL auto_increment,`datesurvey` date NOT NULL,`amount` int(11) NOT NULL,PRIMARY KEY  (`id`)) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=0";

    private String create_ffc_visitoldter = "CREATE TABLE `ffc_visitoldter` (`pcucodeperson` char(5) NOT NULL default '',`pcucode` char(5) NOT NULL default '',`visitno` int(11) NOT NULL default '0',`dentalcheck` varchar(1) default NULL,`sleephour` int(2) default NULL,"
            + "`sleep_q1` char(1) default NULL,`clubname` char(25) default NULL,`funded` char(1) default NULL,`money_funded` int(6) default NULL,`carekeeper_name` char(25) default NULL,`pressure_q1` char(1) default NULL,`pressure_work` char(1) default NULL,`pressure_family` char(1) default NULL,"
            + "`pressure_social` char(1) default NULL,`pid` int(11) default NULL,`sex` varchar(1) default NULL,`age` decimal(3,1) default NULL,`weight` decimal(5,1) default NULL,`height` decimal(5,1) default NULL,`waist` decimal(5,1) default NULL,`ciga` varchar(1) default NULL,`wisky` varchar(1) default NULL,"
            + "`exercise` varchar(1) default NULL,`bigaccidentever` varchar(1) default NULL,`tonic` varchar(1) default NULL,`drugbyyourseft` varchar(1) default NULL,`sugar` varchar(1) default NULL,`salt` varchar(1) default NULL,`q_congenitaldisease` varchar(1) default NULL,"
            + "`diagcode` char(7) default NULL,`clubmember` varchar(1) default NULL,`carekeeper` varchar(1) default NULL,`dateupdate` datetime default NULL,PRIMARY KEY  (`pcucodeperson`,`pcucode`,`visitno`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";

    private String create_ffc_visitspecialperson = "CREATE TABLE `ffc_visitspecialperson` (`pcucode` char(5) NOT NULL default '',`disabid` char(10) default NULL,`pid` int(11) NOT NULL,"
            + "`disabtype` varchar(30) default NULL,`disabcause` varchar(2) default NULL,`diagcode` char(7) default NULL,`datedetect` date default NULL,`dateexpire` date default NULL,"
            + "`dateupdate` datetime default NULL,PRIMARY KEY  (`pcucode`,`pid`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";

    private String create_ffc_506radius = "CREATE TABLE `ffc_506radius` "
            + "(`id` int(11) NOT NULL AUTO_INCREMENT,\n"
            + "  `visitno` int(11) NOT NULL,\n"
            + "  `radius` double(4, 0) NULL DEFAULT NULL,\n"
            + "  `colorcode` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,\n"
            + "  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,\n"
            + "  PRIMARY KEY  BTREE (`id`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";
    private String create_ffc_sf_token = " CREATE TABLE ffc_sf_token (\n"
            + "    id INT PRIMARY KEY AUTO_INCREMENT,\n"
            + "    token_auth VARCHAR(255) NOT NULL,\n"
            + "    token_claim VARCHAR(255) NOT NULL,\n"
            + "    created_date datetime DATETIME DEFAULT NULL ,\n"
            + "    updated_date datetime DATETIME DEFAULT NULL \n"
            + ");";

    private String create_f43specialpp = "CREATE TABLE f43specialpp (\n"
            + "    pcucodeperson CHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,\n"
            + "    pid INT NOT NULL,\n"
            + "    dateserv DATE NOT NULL,\n"
            + "    ppspecial CHAR(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'รหัสการให้บริการส่งเสริม ป้องกันโรค(กำหนดโดย สนย)',\n"
            + "    ppresult VARCHAR(4) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n"
            + "    pcucode CHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n"
            + "    visitno INT DEFAULT NULL,\n"
            + "    servplace CHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'บริการใน /นอกสถานบริการ(1:ในฯ ,2:นอกฯ)',\n"
            + "    ppsplace CHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'รหัสสถานบริการที่ให้บริการส่งเสริม ป้องกันโรค(5 หลัก)',\n"
            + "    provider VARCHAR(35) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n"
            + "    dateupdate DATETIME DEFAULT NULL,\n"
            + "    issend2hisgateway ENUM('0','1','2','9') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n"
            + "    issend2hisgatewaydt DATETIME DEFAULT NULL COMMENT 'DATETIME jhcis data is subscribe to hisgateway',\n"
            + "    issend2hisgatewayall CHAR(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ใช้สำหรับเช็คเพื่อการส่ง HISGateway ทุก Reccord',\n"
            + "    PRIMARY KEY (pcucodeperson, pid, dateserv, ppspecial) USING BTREE\n"
            + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;";

    public void setConnection(Connection connection) {
        this.sqlConnection = connection;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void closeConnection() {
        try {
            this.sqlConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public boolean create_ffcHospital() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_hospital);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }

    }

    public boolean create_ffcPoi() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_poi);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }

    }

    public boolean create_ffcCpoitype() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_cpoitype);
            this.insert_ffcCpoitype();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean insert_ffcCpoitype() throws SQLException {

        String[] poitypeName = {"ร้านขายยา", "คลีนิค", "บริการทีพัก", "ห้างสรรพสินค้า", "ซุปเปอร์มาร์เก็ต", "ตลาด", "ร้านสะดวกซื้อ", "สถานที่ท่องเที่ยว", "โรงภาพยนตร์", "สถานบันเทิง", "ที่พักผ่อน", "กีฬา/สุขภาพ", "สถานีตำรวจ/ดับเพลิง", "สนามบิน", "สถานีขนส่ง", "สถานีรถไฟ", "ท่าเรือ", "ด่านเก็บเงิน/ทางแยก", "โชว์รูมรถยนต์", "ปั้มน้ำมัน", "NGV", "LPG", "อู่ซ่อมรถ", "ธนาคาร", "ATM", "บริษัท", "นิคม/โรงงาน/คลังสินค้า", "อุตสาหกรรม", "อาคารสำนักงาน", "หน่วยงานด้านยุติธรรม", "สถานทูต/กงสุล", "หน่วยงานต่างประเทศ", "หน่วยงานปกครอง", "หน่วยงานราชการ", "รัฐวิสาหกิจ", "ศาสนสถาน", "สถานที่สำคัญ", "สหกรณ์/มูลนิธี/สมาคม", "หน่วยงานสาธารณูปโภค", "จุดสนใจอื่นๆ"};
        //String insert;
        Statement stm = this.sqlConnection.createStatement();
        for (int i = 0; i < 40; i++) {
            int cpoitypeNum = i + 1;
            String insert = "INSERT INTO `ffc_cpoitype` VALUES (" + cpoitypeNum + ", NULL, '" + poitypeName[i] + "')";
            System.out.println(insert);
            stm.addBatch(insert);
        }
        stm.executeBatch();
        return true;

    }

    public boolean modifyVillageTemple() {
        try {
            String addDateField = "ALTER TABLE  `villagetemple` ADD  `dateupdate` DATE NULL";
            String changeReligionLenght = "ALTER TABLE  `villagetemple` CHANGE  `religion`  `religion` VARCHAR( 2 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL";
            Statement stm = this.sqlConnection.createStatement();
            stm.addBatch(addDateField);
            stm.addBatch(changeReligionLenght);
            stm.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean modifyVillageschool() {
        try {
            String changeMaxclassLenght = "ALTER TABLE  `villageschool` CHANGE  `maxclass`  `maxclass` VARCHAR( 4 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL";
            Statement stm = this.sqlConnection.createStatement();
            stm.addBatch(changeMaxclassLenght);
            stm.executeBatch();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffcandroidvisit() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_androidvisit);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffcvisitoldter() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_visitoldter);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_visitspecialperson() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_visitspecialperson);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_506radius() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_506radius);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_token() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_token);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_f43specialpp() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_f43specialpp);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean insert_token() throws SQLException {

        FileSettingDataBaseFFCManager fileSettingDataBaseFFCManager = new FileSettingDataBaseFFCManager();
        //Set PathFile
        fileSettingDataBaseFFCManager.setPathFile("./FFC/config_mysql_local.ffc");
        DriverDataBase driver = fileSettingDataBaseFFCManager.readDriverDataBase();
        Statement stm = this.sqlConnection.createStatement();
        String token_auth = driver.getTokenAuth();
        String token_claim = driver.getTokenClaim();
        String insert = "INSERT INTO ffc_sf_token(token_auth,token_claim,created_date,updated_date) VALUES ('" + token_auth + "','" + token_claim + "',sysdate(),sysdate())";
        System.out.println(insert);
        stm.addBatch(insert);
        stm.executeBatch();
        return true;

    }

    private String create_ffc_sf_person_info = "CREATE TABLE `ffc_sf_person_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`fname` varchar(100) NOT NULL,"
            + "`lname` varchar(100) NOT NULL,"
            + "`birthday` date DEFAULT NULL,"
            + "`gender` varchar(1) DEFAULT NULL,"
            + "`phone` varchar(15) DEFAULT NULL,"
            + "`hn` varchar(20) DEFAULT NULL,"
            + "`authen_date` datetime DEFAULT NULL,"
            + "`authen_code` varchar(50) DEFAULT NULL,"
            + "`weight` decimal(5,2) DEFAULT NULL,"
            + "`height` decimal(5,2) DEFAULT NULL,"
            + "`waist_size` decimal(5,2) DEFAULT NULL,"
            + "`bp` varchar(20) DEFAULT NULL,"
            + "`bmi` varchar(10) DEFAULT NULL,"
            + "`systolic_pressure` decimal(5,2) DEFAULT NULL,"
            + "`diastolic_pressure` decimal(5,2) DEFAULT NULL,"
            + "`serviceCode` varchar(50) DEFAULT NULL,"
            + "`transId` varchar(100) DEFAULT NULL,"
            + "`sourceId` varchar(50) DEFAULT NULL,"
            + "`subDistName` varchar(100) DEFAULT NULL,"
            + "`subDistCode` varchar(10) DEFAULT NULL,"
            + "`distCode` varchar(10) DEFAULT NULL,"
            + "`distName` varchar(100) DEFAULT NULL,"
            + "`provCode` varchar(10) DEFAULT NULL,"
            + "`provName` varchar(100) DEFAULT NULL,"
            + "`postCode` varchar(10) DEFAULT NULL,"
            + "`homeNo` varchar(50) DEFAULT NULL,"
            + "`villageNo` varchar(10) DEFAULT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`send_to_claim` int(1) DEFAULT 0,"
            + "`temperature` decimal(4,2) DEFAULT NULL,"
            + "`hcode` varchar(10) DEFAULT NULL,"
            + "`claim_id` varchar(100) DEFAULT NULL,"
            + "`claim_status` varchar(50) DEFAULT NULL,"
            + "`claim_message` text DEFAULT NULL,"
            + "`claim_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "`photo` longblob DEFAULT NULL,"
            + "`seq` varchar(20) DEFAULT NULL,"
            + "PRIMARY KEY (`id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

    public boolean create_ffc_sf_person_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_person_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean insert_ffc_sf_person_info(String idcard, String fname, String lname, String birthday,
            String gender, String phone, String hn, String authenDate, Double weight, Double height,
            Double waistSize, Double systolicPressure, Double diastolicPressure, String serviceCode,
            String subDistName, String subDistCode, String distName, String distCode,
            String provName, String provCode, String createdBy, String hcode) {

        String sql = "INSERT INTO `ffc_sf_person_info` (`idcard`, `fname`, `lname`, `birthday`, `gender`, "
                + "`phone`, `hn`, `authen_date`, `weight`, `height`, `waist_size`, `systolic_pressure`, "
                + "`diastolic_pressure`, `serviceCode`, `subDistName`, `subDistCode`, `distName`, `distCode`, "
                + "`provName`, `provCode`, `created_by`, `created_date`, `send_to_claim`, `hcode`) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), 0, ?)";

        try {
            PreparedStatement pstmt = sqlConnection.prepareStatement(sql);

            pstmt.setString(1, idcard);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, birthday);
            pstmt.setString(5, gender);
            pstmt.setString(6, phone);
            pstmt.setString(7, hn);
            pstmt.setString(8, authenDate);
            pstmt.setDouble(9, weight != null ? weight : 0.0);
            pstmt.setDouble(10, height != null ? height : 0.0);
            pstmt.setDouble(11, waistSize != null ? waistSize : 0.0);
            pstmt.setDouble(12, systolicPressure != null ? systolicPressure : 0.0);
            pstmt.setDouble(13, diastolicPressure != null ? diastolicPressure : 0.0);
            pstmt.setString(14, serviceCode);
            pstmt.setString(15, subDistName);
            pstmt.setString(16, subDistCode);
            pstmt.setString(17, distName);
            pstmt.setString(18, distCode);
            pstmt.setString(19, provName);
            pstmt.setString(20, provCode);
            pstmt.setString(21, createdBy);
            pstmt.setString(22, hcode);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }
    // เพิ่มใน AdjustDatabaseSql.java

// 1. ffc_sf_cardiovascular_risk_info
    private String create_ffc_sf_cardiovascular_risk_info = "CREATE TABLE `ffc_sf_cardiovascular_risk_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`age` varchar(10) DEFAULT NULL,"
            + "`gender` varchar(10) DEFAULT NULL,"
            + "`blood_pressure` varchar(20) DEFAULT NULL,"
            + "`waist_size` varchar(10) DEFAULT NULL,"
            + "`height` varchar(10) DEFAULT NULL,"
            + "`cholesterol` varchar(10) DEFAULT NULL,"
            + "`is_smoking` varchar(10) DEFAULT NULL,"
            + "`has_diabetes` varchar(10) DEFAULT NULL,"
            + "`risk_level` varchar(20) DEFAULT NULL,"
            + "`risk_percentage` varchar(10) DEFAULT NULL,"
            + "`recommendation` text DEFAULT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 2. ffc_sf_card_reading_history
    private String create_ffc_sf_card_reading_history = "CREATE TABLE `ffc_sf_card_reading_history` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`read_timestamp` datetime NOT NULL,"
            + "`username` varchar(50) NOT NULL,"
            + "`citizen_id` varchar(13) NOT NULL,"
            + "`citizen_name` varchar(200) NOT NULL,"
            + "`device_model` varchar(100) NOT NULL,"
            + "`device_brand` varchar(100) NOT NULL,"
            + "`card_reader_model` varchar(100) NOT NULL,"
            + "`app_version` varchar(50) DEFAULT NULL,"
            + "`read_status` varchar(20) DEFAULT NULL,"
            + "`notes` text DEFAULT NULL,"
            + "`created_at` datetime DEFAULT CURRENT_TIMESTAMP,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_citizen_id` (`citizen_id`),"
            + "INDEX `idx_read_timestamp` (`read_timestamp`),"
            + "INDEX `idx_username` (`username`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 3. ffc_sf_counseling_signature
    private String create_ffc_sf_counseling_signature = "CREATE TABLE `ffc_sf_counseling_signature` ("
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

// 4. ffc_sf_drinking_info
    private String create_ffc_sf_drinking_info = "CREATE TABLE `ffc_sf_drinking_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`drinking` varchar(10) NOT NULL,"
            + "`drinking_frequency` varchar(10) NOT NULL,"
            + "`drinking_alway` varchar(10) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 5. ffc_sf_drugs
    private String create_ffc_sf_drugs = "CREATE TABLE `ffc_sf_drugs` ("
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`question` varchar(255) NOT NULL,"
            + "`subquestion` varchar(255) DEFAULT NULL,"
            + "`other_drugs` varchar(255) DEFAULT NULL,"
            + "`answer` varchar(255) DEFAULT NULL,"
            + "`created_by` varchar(50) NOT NULL,"
            + "`created_date` datetime DEFAULT CURRENT_TIMESTAMP,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`idcard` varchar(13) DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "INDEX `idx_person_info_id` (`person_info_id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_visit_no` (`visitno`),"
            + "INDEX `idx_dateupdate` (`dateupdate`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8";

// 6. ffc_sf_health_risk_assessment_info
    private String create_ffc_sf_health_risk_assessment_info = "CREATE TABLE `ffc_sf_health_risk_assessment_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`health_risk_q1` varchar(10) NOT NULL,"
            + "`health_risk_q2` varchar(10) NOT NULL,"
            + "`health_risk_q3` varchar(10) NOT NULL,"
            + "`health_risk_q4` varchar(10) NOT NULL,"
            + "`health_risk_q5` varchar(10) NOT NULL,"
            + "`health_risk_q6` varchar(10) NOT NULL,"
            + "`fcbg` varchar(10) NOT NULL,"
            + "`fpg` varchar(10) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 7. ffc_sf_nhso_claim_data
    private String create_ffc_sf_nhso_claim_data = "CREATE TABLE `ffc_sf_nhso_claim_data` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`visitno` int(11) NOT NULL,"
            + "`json_data` text NOT NULL,"
            + "`seq` varchar(50) DEFAULT NULL,"
            + "`claim_status` varchar(20) DEFAULT '0',"
            + "`response_data` text DEFAULT NULL,"
            + "`error_message` text DEFAULT NULL,"
            + "`created_date` datetime NOT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`send_date` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_visitno` (`visitno`),"
            + "INDEX `idx_claim_status` (`claim_status`),"
            + "INDEX `idx_created_date` (`created_date`),"
            + "UNIQUE KEY `unique_visitno` (`visitno`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 8. ffc_sf_nicotine_info
    private String create_ffc_sf_nicotine_info = "CREATE TABLE `ffc_sf_nicotine_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`nicotine1` varchar(10) NOT NULL,"
            + "`nicotine2` varchar(10) NOT NULL,"
            + "`nicotine3` varchar(10) NOT NULL,"
            + "`nicotine4` varchar(10) NOT NULL,"
            + "`nicotine5` varchar(10) NOT NULL,"
            + "`nicotine6` varchar(10) NOT NULL,"
            + "`points` varchar(50) NOT NULL,"
            + "`sum` int(11) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`),"
            + "INDEX `idx_visit_no` (`visitno`),"
            + "INDEX `idx_dateupdate` (`dateupdate`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 9. ffc_sf_screening_result_code
    private String create_ffc_sf_screening_result_code = "CREATE TABLE `ffc_sf_screening_result_code` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_id` int(11) NOT NULL,"
            + "`visitno` int(11) NOT NULL,"
            + "`screening_type` varchar(50) NOT NULL,"
            + "`result_code` varchar(20) NOT NULL,"
            + "`result_description` varchar(255) DEFAULT NULL,"
            + "`total_score` int(11) DEFAULT 0,"
            + "`risk_level` varchar(20) DEFAULT 'NORMAL',"
            + "`is_abnormal` int(1) DEFAULT 0,"
            + "`recommendation` text DEFAULT NULL,"
            + "`screening_date` date DEFAULT CURRENT_DATE,"
            + "`status` varchar(20) DEFAULT 'ACTIVE',"
            + "`createtime` datetime DEFAULT CURRENT_TIMESTAMP,"
            + "`updatetime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "`user_create` varchar(50) DEFAULT NULL,"
            + "`user_update` varchar(50) DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_person_id` (`person_id`),"
            + "INDEX `idx_visitno` (`visitno`),"
            + "INDEX `idx_screening_type` (`screening_type`),"
            + "INDEX `idx_screening_date` (`screening_date`),"
            + "UNIQUE KEY `unique_person_visit_screening` (`person_id`, `visitno`, `screening_type`, `screening_date`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 10. ffc_sf_smoker_info
    private String create_ffc_sf_smoker_info = "CREATE TABLE `ffc_sf_smoker_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`smoker_group` varchar(50) NOT NULL,"
            + "`smoker_assist` varchar(50) NOT NULL,"
            + "`smoker_regularly` varchar(50) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`),"
            + "INDEX `idx_visit_no` (`visitno`),"
            + "INDEX `idx_dateupdate` (`dateupdate`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 11. ffc_sf_stress_depression_2q_info
    private String create_ffc_sf_stress_depression_2q_info = "CREATE TABLE `ffc_sf_stress_depression_2q_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`q1` varchar(10) NOT NULL,"
            + "`q2` varchar(10) NOT NULL,"
            + "`points` varchar(50) NOT NULL,"
            + "`sum` int(11) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 12. ffc_sf_stress_depression_9q_info
    private String create_ffc_sf_stress_depression_9q_info = "CREATE TABLE `ffc_sf_stress_depression_9q_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`q1` varchar(10) NOT NULL,"
            + "`q2` varchar(10) NOT NULL,"
            + "`q3` varchar(10) NOT NULL,"
            + "`q4` varchar(10) NOT NULL,"
            + "`q5` varchar(10) NOT NULL,"
            + "`q6` varchar(10) NOT NULL,"
            + "`q7` varchar(10) NOT NULL,"
            + "`q8` varchar(10) NOT NULL,"
            + "`q9` varchar(10) NOT NULL,"
            + "`points` varchar(50) NOT NULL,"
            + "`sum` int(11) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 13. ffc_sf_stress_depression_info
    private String create_ffc_sf_stress_depression_info = "CREATE TABLE `ffc_sf_stress_depression_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`q1` varchar(10) NOT NULL,"
            + "`q2` varchar(10) NOT NULL,"
            + "`q3` varchar(10) NOT NULL,"
            + "`q4` varchar(10) NOT NULL,"
            + "`q5` varchar(10) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`),"
            + "INDEX `idx_visit_no` (`visitno`),"
            + "INDEX `idx_dateupdate` (`dateupdate`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// 14. ffc_sf_suicide_assessment_8q_info
    private String create_ffc_sf_suicide_assessment_8q_info = "CREATE TABLE `ffc_sf_suicide_assessment_8q_info` ("
            + "`id` int(11) NOT NULL auto_increment,"
            + "`person_info_id` varchar(100) NOT NULL,"
            + "`idcard` varchar(13) NOT NULL,"
            + "`q1` varchar(10) NOT NULL,"
            + "`q2` varchar(10) NOT NULL,"
            + "`q3` varchar(10) NOT NULL,"
            + "`q3_2_1` varchar(10) NOT NULL,"
            + "`q4` varchar(10) NOT NULL,"
            + "`q5` varchar(10) NOT NULL,"
            + "`q6` varchar(10) NOT NULL,"
            + "`q7` varchar(10) NOT NULL,"
            + "`q8` varchar(10) NOT NULL,"
            + "`created_by` varchar(50) DEFAULT NULL,"
            + "`created_date` datetime DEFAULT NULL,"
            + "`updated_by` varchar(50) DEFAULT NULL,"
            + "`updated_date` datetime DEFAULT NULL,"
            + "`visitno` varchar(20) DEFAULT NULL,"
            + "`dateupdate` datetime DEFAULT NULL,"
            + "PRIMARY KEY (`id`),"
            + "INDEX `idx_idcard` (`idcard`),"
            + "INDEX `idx_person_info_id` (`person_info_id`)"
            + ") ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1";

// เพิ่ม create methods สำหรับแต่ละ table
    public boolean create_ffc_sf_cardiovascular_risk_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_cardiovascular_risk_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_card_reading_history() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_card_reading_history);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_counseling_signature() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_counseling_signature);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_drinking_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_drinking_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_drugs() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_drugs);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_health_risk_assessment_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_health_risk_assessment_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_nhso_claim_data() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_nhso_claim_data);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_nicotine_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_nicotine_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_screening_result_code() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_screening_result_code);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_smoker_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_smoker_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_stress_depression_2q_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_stress_depression_2q_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_stress_depression_9q_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_stress_depression_9q_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_stress_depression_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_stress_depression_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }

    public boolean create_ffc_sf_suicide_assessment_8q_info() {
        try {
            Statement stm = sqlConnection.createStatement();
            stm.execute(this.create_ffc_sf_suicide_assessment_8q_info);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);
            this.errorMessage = ex.getMessage();
            return false;
        }
    }
}
