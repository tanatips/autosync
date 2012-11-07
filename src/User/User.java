/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

/**
 *
 * @author PeeT
 */
public class User {
     private String username;
    private String password;
    private String fullname;
    private String officerposition;
    
    public User(String Username, String Password, String Fullname, String Officerposition)
    {
        this.username = Username;
        this.password = Password;
        this.fullname = Fullname;
        this.officerposition = Officerposition;
    }

    public User() {
    }
    
    public String getUserName()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public String getFullname()
    {
        return fullname;
    }
    
    public String getOfficerposition()
    {
        return officerposition;
    }
}
