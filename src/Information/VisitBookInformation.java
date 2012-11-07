/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

/**
 *
 * @author Administrator
 */
public class VisitBookInformation{
//------------------------------------------------------------------------------
//@attribute hear
    String serialDevice;
    String visitMax;

//------------------------------------------------------------------------------
//@constructor hear

    public VisitBookInformation() {
        this.serialDevice = null;
        this.visitMax = null;
    }
    public VisitBookInformation(String serialDevice, String visitBook) {
        this.serialDevice = serialDevice;
        this.visitMax = visitBook;
    }

//------------------------------------------------------------------------------
//@Behavior hear
    public String getSerialDevice(){
        return this.serialDevice;
    }
    public String getVisitMax(){
        return this.visitMax;
    }
    public void setSerialDevice(String serialDriver){
        this.serialDevice = serialDriver;
    }
    public void setVisitMax(String VisitMax){
        this.visitMax = VisitMax;
    }   
}
