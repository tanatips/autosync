/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DeviceManager;

import FileManager.FileBehavior;

/**
 *
 * @author Administrator
 */
public class BatFileManager extends FileBehavior{
    final public String NEW_LINE = System.getProperty("line.separator");

    protected void setPathFileBat(String pathFileBat){
        setPathData(pathFileBat);
    }
    protected String getPathFileBat(){
        return getPathData();
    }
    protected void writeBatFile(String discription){
        writeData(discription);
    }
}
