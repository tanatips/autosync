/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DeviceManager;


/**
 *
 * @author Administrator
 */
public class DeviceInformation {
    private String serialDevice;
    private String modelDevice;
    private String nameDevice;
//------------------------------------------------------------------------------
    public DeviceInformation() {
        this.serialDevice = null;
        this.modelDevice = null;
        this.nameDevice = null;       
    }
    public DeviceInformation(String serialDevice, String modelDevice,
            String nameDevice) {
        this.serialDevice = serialDevice;
        this.modelDevice = modelDevice;
        this.nameDevice = nameDevice;
    }
//------------------------------------------------------------------------------
    public String getSerialDevice(){
        return serialDevice;
    }
    public String getModelDevice(){
        return modelDevice;
    }
    public String getNameDevice(){
        return nameDevice;
    }
    public void setSerialDevice(String serialDriver){
        this.serialDevice = serialDriver;
    }
    public void setModelDevice(String modelDriver){
        this.modelDevice = modelDriver;
    }
    public void setNameDriver(String nameDriver){
        this.nameDevice = nameDriver;
    }
}
