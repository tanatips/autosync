/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ErrorManager;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author PeeT
 */
public class ErrorManagement {
    public void errorReport(Component component, String errorMessage){
        JOptionPane.showMessageDialog(component, errorMessage, "", JOptionPane.ERROR_MESSAGE);
    }
}
