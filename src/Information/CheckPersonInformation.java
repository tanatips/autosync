/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

/**
 *
 * @author Administrator
 */
public class CheckPersonInformation{
//------------------------------------------------------------------------------
//@attribute hear
    String serialDevice;
    String pidMax;

//------------------------------------------------------------------------------
//@constructor hear

    public CheckPersonInformation() {
        this.serialDevice = null;
        this.pidMax = null;
    }
    public CheckPersonInformation(String serialDevice, String visitBook) {
        this.serialDevice = serialDevice;
        this.pidMax = visitBook;
    }

//------------------------------------------------------------------------------
//@Behavior hear
    public String getSerialDevice(){
        return this.serialDevice;
    }
    public String getpidMax(){
        return this.pidMax;
    }
    public void setSerialDevice(String serialDriver){
        this.serialDevice = serialDriver;
    }
    public void setpidMax(String VisitMax){
        this.pidMax = VisitMax;
    }   
}
