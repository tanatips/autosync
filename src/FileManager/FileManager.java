/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileManager;

import FFC_Form.TransferForm;
import Transfer.JhcisToAndroid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class FileManager {
    int fileCount = 0;
    private void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {
            if (sourceLocation.isDirectory()) {
                    if (!targetLocation.exists()) {
                            targetLocation.mkdir();
                    }
                String[] children = sourceLocation.list();
                    for (int i=0; i<children.length; i++) {
                        copyDirectory(new File(sourceLocation, children[i]),
                                new File(targetLocation, children[i]));
                    }
            } else {
                InputStream in = new FileInputStream(sourceLocation);
                OutputStream out = new FileOutputStream(targetLocation);
                byte[] buf = new byte[1024];
                int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                if(in!=null){
                    try{
                    in.close();
                    } catch (IOException ignored) {
                    }
                }
                 if(out!=null){
                    try{
                out.close();
                } catch (IOException ignored) {
              }
                }
                 fileCount++;
                 System.out.println(sourceLocation.getPath());
                 System.out.println(targetLocation.getPath());
                 System.out.println("Copy file success !!!");
                 System.out.println(fileCount);
            }
    }
    

    public void copyFile(String source,String target){
        File sourceLocation = new File(source);
        File targetLocation = new File(target);
            try {
                copyDirectory(sourceLocation, targetLocation);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

    }
// Deletes all files and subdirectories under dir.
// Returns true if all deletions were successful.
// If a deletion fails, the method stops attempting to delete and returns false.

    public boolean deleteDir(File dir) {
        
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                System.out.println(children[i]);
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();

    }
    public ArrayList<String> getListFireName(String path){
        ArrayList<String> arrayList = new ArrayList<String>();
        File directory = new File(path);
        String filename[] = directory.list();
        for(String temp:filename) {
            arrayList.add(temp);
        }
        return arrayList;

    }
    public boolean  isFile(String path){
        File directory = new File(path);
        return directory.isFile();
    }
    public boolean  isDirectory(String path){
        File directory = new File(path);
        return directory.isDirectory();
    }
    public void createDirectory(String path){
        File directory = new File(path);
        directory.mkdir();
    }
    
    public boolean deleteDirectory(String path){

    	File directory = new File(path);
    	//make sure directory exists
    	if(!directory.exists()){
           System.out.println("Directory does not exist.");
           return false;

        }else{
           deleteDir(directory);
           return true;
        }
    }

    public boolean deleteFile(String path){
        File file = new File(path);
        if(file.exists()){
            if(file.delete()){
                System.out.println("file deleted");
                return true;
            }else{
                System.out.println("file can not delete !!!");
                return false;
            }
        }else{
            System.out.println("file not found !!!!");
            return true;
        }
        //file.deleteOnExit();
        //return true;
    }

        

        
//    public static void main(String[] ses){
//        FileManager aa = new FileManager();
//        FileManager bb = new FileManager();
//        ArrayList<String> d = aa.getListFireName("C:/FFC/");
//        for (String string : d) {
//            System.out.println(">"+string);
//        }
//        System.out.println(aa.isDirectory("C:/Program Files/JHCIS/Photoes/"));
//        if(!aa.isDirectory("C:/Program Files/JHCIS/Photoes/")){
//            aa.createDirectory("C:/Program Files/JHCIS/Photoes/");
//        }
//        System.out.println(aa.isDirectory("C:/Program Files/JHCIS/Photoes/"));
//        System.out.println(new File("C:/DD/BB/2276286.png").compareTo(new File("C:/DD/AA/2276286.png")));
//        System.out.println(new File("C:/DD/AA/2276286.png").compareTo(new File("C:/DD/BB/2276286.png")));
//        System.out.println(new File("C:/DD/AA/").compareTo(new File("C:/DD/AA/")));
//    }

        public void resetFileCount(){
            this.fileCount = 0;
        }

        public int getFileCount(){
            return this.fileCount;
        }

}
