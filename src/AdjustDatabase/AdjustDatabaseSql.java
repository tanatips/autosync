/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package AdjustDatabase;

import ConnectDatabase.*;
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
              +"`sleep_q1` char(1) default NULL,`clubname` char(25) default NULL,`funded` char(1) default NULL,`money_funded` int(6) default NULL,`carekeeper_name` char(25) default NULL,`pressure_q1` char(1) default NULL,`pressure_work` char(1) default NULL,`pressure_family` char(1) default NULL,"
              +"`pressure_social` char(1) default NULL,`pid` int(11) default NULL,`sex` varchar(1) default NULL,`age` decimal(3,1) default NULL,`weight` decimal(5,1) default NULL,`height` decimal(5,1) default NULL,`waist` decimal(5,1) default NULL,`ciga` varchar(1) default NULL,`wisky` varchar(1) default NULL,"
              +"`exercise` varchar(1) default NULL,`bigaccidentever` varchar(1) default NULL,`tonic` varchar(1) default NULL,`drugbyyourseft` varchar(1) default NULL,`sugar` varchar(1) default NULL,`salt` varchar(1) default NULL,`q_congenitaldisease` varchar(1) default NULL,"
              +"`diagcode` char(7) default NULL,`clubmember` varchar(1) default NULL,`carekeeper` varchar(1) default NULL,`dateupdate` datetime default NULL,PRIMARY KEY  (`pcucodeperson`,`pcucode`,`visitno`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";

    private String create_ffc_visitspecialperson = "CREATE TABLE `ffc_visitspecialperson` (`pcucode` char(5) NOT NULL default '',`disabid` char(10) default NULL,`pid` int(11) NOT NULL,"
            + "`disabtype` varchar(30) default NULL,`disabcause` varchar(2) default NULL,`diagcode` char(7) default NULL,`datedetect` date default NULL,`dateexpire` date default NULL,"
            + "`dateupdate` datetime default NULL,PRIMARY KEY  (`pcucode`,`pid`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";
    
     private String create_ffc_506radius = "CREATE TABLE `ffc_506radius` "+
            "(`id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `visitno` int(11) NOT NULL,\n" +
            "  `radius` double(4, 0) NULL DEFAULT NULL,\n" +
            "  `colorcode` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,\n" +
            "  `level` varchar(1) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,\n" +
            "  PRIMARY KEY  BTREE (`id`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";
    
    public void setConnection(Connection connection){
        this.sqlConnection = connection;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }

    public void closeConnection(){
        try {
            this.sqlConnection.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdjustDatabaseSql.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public boolean create_ffcHospital(){
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
    
    public boolean create_ffcPoi(){
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
    
    public boolean create_ffcCpoitype(){
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
    
    public boolean insert_ffcCpoitype() throws SQLException{
        
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

    public boolean modifyVillageTemple(){
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

    public boolean modifyVillageschool(){
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

    public boolean create_ffcandroidvisit(){
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

    public boolean create_ffcvisitoldter(){
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

    public boolean create_ffc_visitspecialperson(){
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

    public boolean create_ffc_506radius(){
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

}
