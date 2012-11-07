/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Information;

/**
 *
 * @author PeeT
 */
public class FFCInformation {
    private String dserial;
    private String dname;
    private String dmodel;
    private String maxvisit;
    private String lastupdate;
    private String maxpid;
    private String maxhcode;

    public FFCInformation() {
        this.dserial = null;
        this.dmodel = null;
        this.dname = null;
        this.maxvisit = null;
        this.maxpid = null;
        this.lastupdate = null;
        this.maxhcode = null;
        
    }
    
    public FFCInformation(String dserial,String dname,String dmodel,String maxvisit,String lastupdate,String maxpid,String maxhcode) {
        this.dserial = dserial;
        this.dname = dname;
        this.dmodel = dmodel;
        this.maxvisit = maxvisit;
        this.lastupdate = lastupdate;
        this.maxpid = maxpid;
        this.maxhcode = maxhcode;
    }
           
//------------------------------------------------------------------------------
//@Behavior hear
    public void setdserial(String deviceSerial){
        this.dserial= deviceSerial;
    }
    
    public void setdname(String deviceName){
        this.dname = deviceName;
    }
    
    public void setdmodel(String deviceModel){
        this.dmodel = deviceModel;
    }
    
    public void setMaxvisit(String maxvisit){
        this.maxvisit = maxvisit;
    }
    
    public void setMaxpid(String maxpid){
        this.maxpid = maxpid;
    }
    
    public void setMaxhcode(String maxhcode){
        this.maxhcode = maxhcode;
    }
    
    public void setLastupdate(String lastupdate){
        this.lastupdate = lastupdate;
    }
    
    public String getdserial(){
        return this.dserial;
    }
    
    public String getdname(){
        return this.dname;
    }
    
    public String getdmodel(){
        return this.dmodel;
    }

    public String getMaxvisit(){
        return this.maxvisit;
    }

    public String getLastupdate(){
        return this.lastupdate;
    }

    public String getMaxpid(){
        return this.maxpid;
    }

    public String getMaxhcode(){
        return this.maxhcode;
    }
    
    
}
