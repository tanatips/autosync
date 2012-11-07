/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ConnectDatabase;

/**
 *
 * @author Administrator
 */
public class DriverDataBase {
    private String server;
    private String serverName;
    private String driverName;
    private String port;
    private String userName;
    private String password;

    public DriverDataBase() {
        this.server = null;
        this.serverName = null;
        this.driverName = null;
        this.port = null;
        this.userName = null;
        this.password = null;
    }

    public DriverDataBase(String server, String serverName, String driverName, String port, String userName, String password) {
        this.server = server;
        this.serverName = serverName;
        this.driverName = driverName;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getServerName() {
        return serverName;
    }

    public String getServer() {
        return server;
    }

    public String getUserName() {
        return userName;
    }
           
}
