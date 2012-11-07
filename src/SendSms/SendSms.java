/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SendSms;


 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendSms {

    private String username;
    private String password;
    private String fname;
    private String lname;
    private String telephone;
    private String appoitype;
    private String appoidate;
    private String hospitalname;


    public SendSms(String username, String password, String fname, String lname, String telephone, String appoitype, String appoidate, String hospitalname) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.telephone = telephone;
        this.appoitype = appoitype;
        this.appoidate = appoidate;
        this.hospitalname = hospitalname;
    }
    public boolean send() {
        String result = "";
        try {
        // Construct data
        String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(this.username, "UTF-8");
        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(this.password, "UTF-8");
        data += "&" + URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(this.fname, "UTF-8");
        data += "&" + URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(this.lname, "UTF-8");
        data += "&" + URLEncoder.encode("telephone", "UTF-8") + "=" + URLEncoder.encode(this.telephone, "UTF-8");
        data += "&" + URLEncoder.encode("appoitype", "UTF-8") + "=" + URLEncoder.encode(this.appoitype, "UTF-8");
        data += "&" + URLEncoder.encode("appoidate", "UTF-8") + "=" + URLEncoder.encode(this.appoidate, "UTF-8");
        data += "&" + URLEncoder.encode("hospitalname", "UTF-8") + "=" + URLEncoder.encode(this.hospitalname, "UTF-8");

        System.out.println(data);
        // Send data
        URL url = new URL("http://www.ffc.in.th/ffc_sendsms/thsms.php");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();


        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            
            result = line.trim();
        }
        wr.close();
        rd.close();


    } catch (Exception e) {
    }
        System.out.println(result);
    if(result.equals("success")){
        return true;
    }else{
        return false;
    }
}



}

