/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class FileBehavior{
    //Attribut
    private String pathData;

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    protected String getPathData() {
        return pathData;
    }

    //end Attribute

    //Behaviori
   /* protected void writeData(String data){
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
    }*/

    public void writeData(String data){
      File file = new File(pathData);
      try {
         Writer out = new BufferedWriter(
               new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
         out.write(data);
         out.close();
         out = null;
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
    }

    public void writeDataArrayList(ArrayList<String> data){
      File file = new File(pathData);
      try {
         Writer out = new BufferedWriter(
               new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
         for(int i=0; i<data.size(); i++){
            out.write(data.get(i)+"\n");
         }
         out.close();
         out = null;
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
    }

    public String readData(){
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
                contents.append(text);
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
    //end Behaviori
    public void writeDataWithNewline(String data){
      File file = new File(pathData);
      try {
         Writer out = new BufferedWriter(
               new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
         out.write(data+"\n");
         out.close();
         out = null;
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
    }

    public ArrayList<String> readDataMultipleline(){
        File file = new File(pathData);
        //StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
         ArrayList<String> dataList = new ArrayList<String>();
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
           
            while ((text = reader.readLine()) != null)
            {
                //contents.append(text);
                //contents.append(System.getProperty("line.separator"));
                dataList.add(text);
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
        return dataList;
       
    }

}
