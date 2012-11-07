/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CompressFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author PeeT
 */
public class CompressFile {
    public void zipDirectory(String dir, String zipfile)
      throws IOException, IllegalArgumentException {
    // Check that the directory is a directory, and get its contents
    File d = new File(dir);
    if (!d.isDirectory())
      throw new IllegalArgumentException("Not a directory:  "
          + dir);
    String[] entries = d.list();
    byte[] buffer = new byte[4096]; // Create a buffer for copying
    int bytesRead;

    ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));

    for (int i = 0; i < entries.length; i++) {
      File f = new File(d, entries[i]);
      if (f.isDirectory())
        continue;//Ignore directory
      FileInputStream in = new FileInputStream(f); // Stream to read file
      ZipEntry entry = new ZipEntry(f.getName()); // Make a ZipEntry
      out.putNextEntry(entry); // Store entry
      while ((bytesRead = in.read(buffer)) != -1)
        out.write(buffer, 0, bytesRead);
      in.close();
    }
    out.close();
  }
}
