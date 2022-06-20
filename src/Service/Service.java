/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import ConnectDatabase.ConnectSQL;
import ConnectDatabase.DriverDataBase;
import DeviceManager.DeviceInformation;
import FFC_Form.FFCAgreement;
import FFC_Form.LogInForm;
import FFC_Form.MainForm;
import User.User;
import java.awt.Point;
import ConvertSQLiteToSQL.ConvertSQLiteToSQL;
import FFC_Information.FFCInformationManager;
import FileManager.FileBehavior;
import java.sql.SQLException;
/**
 *
 * @author PeeT
 */
public class Service {
    public static MainForm mainform;
    public static LogInForm loginform;
    public static FFCAgreement Agreementform;
    public static User user;
    public static String driveDeviceConnect;
    public static String serialDeviceConnect;
    public static boolean connectDeviceStatus;
    public static DeviceInformation deviceInformation;
    public static int numDeviceConnect;
    public static DriverDataBase driverDatabase;
    public static ConnectSQL connectionSQL;
   
    public static boolean checkworking = false;
    public static Point frameLocation;
    public static String deviceMode;
    public static final String defaultJhcisPath = "C:/Program Files/JHCIS";
    public static String jhcisPath = defaultJhcisPath;
    public static String mJHCISPath = "./FFC/Db_tmp/mJHCIS.db";
    public static String mJHCISsdbPath = "./FFC/Db_tmp/mJHCIS.sdb";
    public static String uJHCISPath = "./FFC/Db_tmp/uJHCIS.db";
    public static String ffcInformationPath = "./FFC/FFC_Information.db";
    public static String autosyncVersion = "3.0.2022.06.13";
    public static ConvertSQLiteToSQL convert;
    public static ConvertSQLiteToSQL.UpdateCountSet updateCount;
    public static FileBehavior fileBehavior = new FileBehavior();
    public static ConnectDatabase.ConnectSQLite SQLiteConnection = new ConnectDatabase.ConnectSQLite();
    public static FFCInformationManager ffcInformationManager = new FFCInformationManager();

    public static void Service() throws ClassNotFoundException, SQLException{
        convert = new ConvertSQLiteToSQL();
        updateCount = convert.getUpdateCountSet();
    }
}
