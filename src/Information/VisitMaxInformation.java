/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

/**
 *
 * @author Administrator
 */
public class VisitMaxInformation{
//------------------------------------------------------------------------------
//@attribute hear
    String serialDevice;
    String visitMax;

//------------------------------------------------------------------------------
//@constructor hear

    public VisitMaxInformation() {
        this.serialDevice = null;
        this.visitMax = null;
    }
    public VisitMaxInformation(String serialDevice, String visitBook) {
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
