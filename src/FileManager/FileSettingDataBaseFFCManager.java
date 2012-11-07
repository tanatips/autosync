/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileManager;

import ConnectDatabase.DriverDataBase;

/**
 *
 * @author Administrator
 */
public class FileSettingDataBaseFFCManager extends FileBehavior{

    public void setPathFile(String pathFileSettingFFCDataBase){
        setPathData(pathFileSettingFFCDataBase);
    }
    public void writeDriverDataBase(DriverDataBase driverDataBase){
        writeData(driverDataBase.getServer()
                +"/"
                +driverDataBase.getServerName()
                +"/"
                +driverDataBase.getDriverName()
                +"/"
                +driverDataBase.getPort()
                +"/"
                +driverDataBase.getUserName()
                +"/"
                +driverDataBase.getPassword()
                +"/");
    }

    public DriverDataBase readDriverDataBase(){
        String []temp= readData().split("/");
        return new DriverDataBase(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5]);
    }
//    public static void main(String[]args){
//        FileSettingDataBaseFFCManager a = new FileSettingDataBaseFFCManager();
//        a.setPathFile("C:/testDatabaseSetting.txt");
//       // a.writeDriverDataBase(new DriverDataBase("AAA##", "BBB", "CCCC", "DDD", "EEE", "FFF"));
//        DriverDataBase d = new DriverDataBase();
//        d = a.readDriverDataBase();
//        System.out.println(d.getServer());
//        System.out.println(d.getServerName());
//        System.out.println(d.getDriverName());
//        System.out.println(d.getPort());
//        System.out.println(d.getUserName());
//        System.out.println(d.getPassword());
//    }

}
