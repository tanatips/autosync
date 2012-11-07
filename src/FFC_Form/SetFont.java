/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FFC_Form;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author PeeT
 */
public class SetFont {
    private Font font;
    
    public SetFont(){
        
    }
    
    public void changeFont(String pathFont,float fontSize,javax.swing.JComponent... component){
        this.font = this.getFont(pathFont,fontSize);
        for(javax.swing.JComponent jc : component){
            jc.setFont(this.font);
        }
    }
    
    private Font getFont(String pathFont,float fontSize)
     {  Font newfont = null; 
        try {
            newfont = Font.createFont(Font.TRUETYPE_FONT, new File(pathFont));
            return newfont.deriveFont(fontSize);
        } catch (FontFormatException ex) {
            Logger.getLogger(TransferForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TransferForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newfont;
     }        
}
