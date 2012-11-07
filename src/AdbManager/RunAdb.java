/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AdbManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PeeT
 */
public class RunAdb implements Runnable {

    private String adbBatPath;
    private OnAdbErrorListener listener;

    public RunAdb(String adbBatPath) {
        this.adbBatPath = adbBatPath;
    }

    @Override
    public void run() {
        try {
            System.out.println();
            Process p = Runtime.getRuntime().exec(this.adbBatPath);
            InputStream in = p.getErrorStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = buff.readLine()) != null) {

                System.out.println(line);
            }
            System.out.println("Exit code = " + p.waitFor());
        } catch (InterruptedException ex) {
            Logger.getLogger(RunAdb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RunAdb.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public void setOnAdbErrorListener(OnAdbErrorListener listener){
        this.listener = listener;
    }

    public boolean runAdb() {
        try {
            System.out.println();
            Process p = Runtime.getRuntime().exec(this.adbBatPath);
            InputStream in = p.getErrorStream();
            BufferedReader buff = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = buff.readLine()) != null) {
                if (listener != null) {
                    if (line.contains("remote object")) {
                        listener.onRemoteObjectNotExist();
                        return false;
                    }
                    if (line.contains("failed to copy")) {
                        listener.onFailedToCopy();
                        return false;
                    }
                    System.out.println(line);
                }
            }
            System.out.println("Exit code = " + p.waitFor());
            return true;
        } catch (InterruptedException ex) {
            Logger.getLogger(RunAdb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(RunAdb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static interface OnAdbErrorListener {

        public void onFailedToCopy();

        public void onRemoteObjectNotExist();
    }
}
