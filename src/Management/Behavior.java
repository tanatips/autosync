/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Management;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *+
 * @author Administrator
 */
public class Behavior {
    //Attribut
    private String pathData;
    public final String WORD_INTERN = ";"; //คำแยก ค่าในข้อมูล
    public final String WORD_STEP = ","; //คำแยก ระหว่างข้อมูล
    //end Attribute
    
    //Behaviori
    protected void setPathData(String pathData){
        this.pathData=pathData;
    }
    protected String getPathData(){
        return this.pathData;
    }
    protected void writeData(String data){
        FileOutputStream fos;
        DataOutputStream dos;

        try {
            File file= new File(pathData);
            fos = new FileOutputStream(file);
            dos=new DataOutputStream(fos);
            dos.writeBytes(data);
            fos.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }

    protected void writeData1(String data){
        FileOutputStream fos;
        DataOutputStream dos;

        try {
            File file= new File(pathData);
            fos = new FileOutputStream(file);
            dos=new DataOutputStream(fos);
            dos.writeBytes(data+"\n");
            fos.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeDataWithNewline(ArrayList<String> data){
        FileOutputStream fos;
        DataOutputStream dos;

        try {
            File file= new File(pathData);
            fos = new FileOutputStream(file);
            dos=new DataOutputStream(fos);
            for(String data1: data){
                dos.writeBytes(data1+"\n");
            }
            fos.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String readData(){
        File file = new File(pathData);
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null)
            {
//                contents.append(text)
//                    .append(System.getProperty(
//                        "line.separator"));
                contents.append(text).append("\n");
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        System.out.println(contents.toString());
        if(contents!= null)
        return contents.toString();
        else
            return "";

    }

    protected ArrayList<String> readDataLine(){
        File file = new File(pathData);

        ArrayList<String> data = new ArrayList<String>();
        BufferedReader reader = null;

        try
        {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null)
            {
                //contents.append(text);
                data.add(text);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        //System.out.println(contents.toString());
        return data;

    }
    //end Behaviori

}
