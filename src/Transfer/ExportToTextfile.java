/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Transfer;

import CompressFile.CompressFile;
import Export.ExportToTextFile;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class ExportToTextfile implements Runnable{
    private char separated = ' ';
    private String path = "";

    public void setPathFile(String path){
        this.path = path;
    }

    public void setSeparated(char separated){
        this.separated = separated;
    }
    
    
    @Override
    public void run(){
        ExportToTextFile export = new ExportToTextFile();
        try {
            System.out.println("Start Export...");
            System.out.println(path);
            
            export.Export(true, this.separated, "visit","person","visitdiag","visitDrug","house","personbehavior");

            CompressFile compress = new CompressFile();
            compress.zipDirectory(".\\FFC\\File_Temp\\Compress", path);
        } catch (IOException ex) {
            Logger.getLogger(ExportToTextfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ExportToTextfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

