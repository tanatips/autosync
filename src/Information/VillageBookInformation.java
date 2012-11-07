/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

/**
 *
 * @author Administrator
 */
public class VillageBookInformation{
//------------------------------------------------------------------------------
//@Attribute hear
    private String villageCode;
    private String serialDevice;
    private String dateUpdate;

    public VillageBookInformation() {
        this.villageCode = null;
        this.serialDevice = null;
        this.dateUpdate = null;
    }
        public VillageBookInformation(String serialDevice, String villageCode, String dateUpdate) {
        this.villageCode = villageCode;
        this.serialDevice = serialDevice;
        this.dateUpdate = dateUpdate;
    }    
//------------------------------------------------------------------------------
//@Behavior hear
    public void setVillageCode(String villageCode){
        this.villageCode = villageCode;
    }
    public void setSerialDevice(String serialDevice){
        this.serialDevice = serialDevice;
    }
    public String getVillageCode(){
        return this.villageCode;
    }
    public String getSerialDevice(){
        return this.serialDevice;
    }
    
    public String setDateUpdate(String dateUpdate)
    {
        return this.dateUpdate = dateUpdate; 
    }
    
    public String getdateUpdate()
    {
        return this.dateUpdate;
    }
    
}

