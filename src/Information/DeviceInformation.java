/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;


/**
 *
 * @author Administrator
 */
public class DeviceInformation {
    private String seralDevice;
    private String modelDevice;
    private String nameDevice;
//------------------------------------------------------------------------------
    public DeviceInformation() {
        this.seralDevice = null;
        this.modelDevice = null;
        this.nameDevice = null;       
    }
    public DeviceInformation(String seralDevice, String modelDevice,
            String nameDevice) {
        this.seralDevice = seralDevice;
        this.modelDevice = modelDevice;
        this.nameDevice = nameDevice;
    }
//------------------------------------------------------------------------------
    public String getSerialDevice(){
        return seralDevice;
    }
    public String getModelDevice(){
        return modelDevice;
    }
    public String getNameDevice(){
        return nameDevice;
    }
    public void setSerialDevice(String serialDriver){
        this.seralDevice = serialDriver;
    }
    public void setModelDevice(String modelDriver){
        this.modelDevice = modelDriver;
    }
    public void setNameDriver(String nameDriver){
        this.nameDevice = nameDriver;
    }
}
