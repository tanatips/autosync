/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CompressFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author PeeT
 */
public class ExtractFile {
     public void getZipFiles(String filename)
    {
        try
        {
            String destinationname = ".\\FFC\\File_Temp\\Extract\\";
            byte[] buf = new byte[1024];
            ZipInputStream zipinputstream = null;
            ZipEntry zipentry;
            zipinputstream = new ZipInputStream(
                new FileInputStream(filename));

            zipentry = zipinputstream.getNextEntry();
            while (zipentry != null)
            {
                //for each entry to be extracted
                String entryName = zipentry.getName();
                System.out.println("entryname "+entryName);
                int n;
                FileOutputStream fileoutputstream;
                File newFile = new File(entryName);
                String directory = newFile.getParent();

                if(directory == null)
                {
                    if(newFile.isDirectory())
                        break;
                }

                fileoutputstream = new FileOutputStream(
                   destinationname+entryName);

                while ((n = zipinputstream.read(buf, 0, 1024)) > -1)
                    fileoutputstream.write(buf, 0, n);

                fileoutputstream.close();
                zipinputstream.closeEntry();
                zipentry = zipinputstream.getNextEntry();

            }//while

            zipinputstream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
