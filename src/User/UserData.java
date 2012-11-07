/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package User;

/**
 *
 * @author pi
 */
public class UserData {
    private String name;
    private String lastName;
    private String possition;
    private String organizations;
    private String address;

    public UserData() {
        
    }

    public UserData(String name, String lastName, String possition, String organizations, String address) {
        this.name = name;
        this.lastName = lastName;
        this.possition = possition;
        this.organizations = organizations;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getOrganizations() {
        return organizations;
    }

    public String getPossition() {
        return possition;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganizations(String organizations) {
        this.organizations = organizations;
    }

    public void setPossition(String possition) {
        this.possition = possition;
    }
    
}
