/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MailManager;

import FFC_Form.FFCAgreement;
import FileManager.FileUser;
import User.UserData;
import auto_sync_v2.Main;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Service.Service;

/**
 *
 * @author pi
 */
public class SendMail implements Runnable{
    private String userName;
    private String host;
    private String port;
    private String starttls;
    private String auth;
    private boolean debug;
    private String socketFactoryClass;
    private String fallback;
    private String [] to;
    private String subject;
    private String text;
    private String passWord;
    private Thread sendMailThread;
    private UserData userData;
    @Override
    public void run(){
        Main.ffcRegisterForm.setEnabled(false);
        Main.ffcRegisterForm.setVisibleSendMailDialog(true);
        Thread timeThread = new Thread(countTime);
        timeThread.start();
        
        //Send Email
        if(!sendMail()){
            Main.ffcRegisterForm.setVisibleSendMailDialog(false);
            timeThread.stop();
            JOptionPane.showMessageDialog(Main.ffcRegisterForm,
            "ไม่สามารถเชื่อมต่ออินเตอร์เน็ต",
            "error",
            JOptionPane.ERROR_MESSAGE);
            Main.ffcRegisterForm.setEnabled(true);
            return;
        }
        FileUser fileUser = new FileUser();
        fileUser.setPathFile("./FFC/Register_user.ffc");

        //Write User information
        fileUser.writeDriverDataBase(userData);
        timeThread.stop();
        Main.ffcRegisterForm.setVisibleSendMailDialog(false);
        JOptionPane.showMessageDialog(Main.ffcRegisterForm,
            "การลงทะเบียนเสร็จสิ้น");
        Main.ffcRegisterForm.setVisible(false);
        Service.Agreementform = new FFCAgreement();
        Service.Agreementform.setVisible(true);
    }
        public void setVariable(String userName
                ,String passWord,String host,String port,String starttls
                ,String auth,boolean debug,String socketFactoryClass
                ,String fallback,String[] to,String subject,String text){
            this.auth = auth;
            this.debug = debug;
            this.fallback = fallback;
            this.host = host;
            this.passWord = passWord;
            this.port = port;
            this.socketFactoryClass = socketFactoryClass;
            this.starttls = starttls;
            this.subject = subject;
            this.text = text;
            this.to = to;
            this.userName = userName;
        }

        public boolean sendMail(){
            Properties props = new Properties();
                //Properties props=System.getProperties();
            props.put("mail.smtp.user", userName);
        
            props.put("mail.smtp.host", host);
                
            if(!"".equals(port)){
                    props.put("mail.smtp.port", port);
            }   
            if(!"".equals(starttls)){
                props.put("mail.smtp.starttls.enable",starttls);
            }
            props.put("mail.smtp.auth", auth);
                
            if(debug){
                props.put("mail.smtp.debug", "true");      
            }else{          
                props.put("mail.smtp.debug", "false");         
            }
                
            if(!"".equals(port)){
                props.put("mail.smtp.socketFactory.port", port);
            }
            if(!"".equals(socketFactoryClass)){
                props.put("mail.smtp.socketFactory.class",socketFactoryClass);
            }       
            if(!"".equals(fallback)){
                props.put("mail.smtp.socketFactory.fallback", fallback);
            }

            try{
                        
                Session session = Session.getDefaultInstance(props, null);
            
                session.setDebug(debug);
            
                MimeMessage msg = new MimeMessage(session);
            
                msg.setText(text);
            
                msg.setSubject(subject);
            
                msg.setFrom(new InternetAddress("ffcautosync@gmail.com"));
                        
                for(int i=0;i<to.length;i++){
                    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));      
                }
            try{
                msg.saveChanges();         
                Transport transport = session.getTransport("smtp");          
                transport.connect(host, userName, passWord);        
                transport.sendMessage(msg, msg.getAllRecipients());       
                transport.close(); 
            }catch(MessagingException messagingException){ 
                return false;
            }
                return true;
        }catch (Exception mex){               
            mex.printStackTrace();
            return false;
        }
        }


        Runnable countTime = new Runnable() {
            int count=0;
            @Override

            public void run(){
                while(sendMailThread.isAlive()){
                try {
                    Thread.sleep(1000);
                    Main.ffcRegisterForm.setTextTimeLabel(String.valueOf(count));
                    count++;
                } catch (InterruptedException ex) {
                    Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            }
        };

        public void getThread(Thread thread){
            this.sendMailThread = thread;
        }

        public void getUserData(UserData userData){
            this.userData = userData;
        }
}
