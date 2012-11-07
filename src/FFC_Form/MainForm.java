/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainForm.java
 *
 * Created on 28 เม.ย. 2554, 16:23:21
 */
package FFC_Form;

import FileManager.FileManager;
import Service.Service;
import javax.swing.ToolTipManager;
import auto_sync_v2.Main;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
/**
 *
 * @author PeeT
 */
public class MainForm extends javax.swing.JFrame {
   public static ConnectDeviceForm connForm;
   public static TransferForm transForm;
   public static MaintenenceForm maintenForm;
   SetFont setFont;

   Point start_drag;
   Point start_loc;
    /** Creates new form MainForm */
    public MainForm() {
        initComponents();
        this.setAllbg();
        this.setVersion();
        setFont = new SetFont();
        setFont.changeFont("THSarabun Bold.ttf", 18f, this.jLabel1,this.jLabel2,this.logoutButton);
        setFont.changeFont("THSarabun.ttf", 18f, this.nameLabel,this.positionLabel);
        ToolTipManager.sharedInstance().setInitialDelay(100);
        setLocationRelativeTo(null);
        connForm = new ConnectDeviceForm();
        transForm = new TransferForm();
        maintenForm = new MaintenenceForm();
        Service.frameLocation = this.getLocation();
        this.adjustDatabaseDialog.setLocationRelativeTo(null);
        
        
    }

    /** This method is called from within the constructor to
     * initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        adjustDatabaseDialog = new javax.swing.JDialog();
        jLayeredPane17 = new javax.swing.JLayeredPane();
        jLabel40 = new javax.swing.JLabel();
        adjustTimeLabel = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        decryptProgressBar = new javax.swing.JProgressBar();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLayeredPane6 = new javax.swing.JLayeredPane();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        versionLabel = new javax.swing.JLabel();

        adjustDatabaseDialog.setEnabled(false);
        adjustDatabaseDialog.setMinimumSize(new java.awt.Dimension(315, 180));
        adjustDatabaseDialog.setResizable(false);
        adjustDatabaseDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                adjustDatabaseDialogWindowClosing(evt);
            }
        });

        jLayeredPane17.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/Image/FFC_Background/decryptdialog_bg.png")))); // NOI18N

        jLabel40.setText("Time :");
        jLabel40.setBounds(20, 110, 50, -1);
        jLayeredPane17.add(jLabel40, javax.swing.JLayeredPane.DEFAULT_LAYER);

        adjustTimeLabel.setText("0");
        adjustTimeLabel.setBounds(80, 110, 30, -1);
        jLayeredPane17.add(adjustTimeLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel41.setText("Sec.");
        jLabel41.setBounds(110, 110, 50, -1);
        jLayeredPane17.add(jLabel41, javax.swing.JLayeredPane.DEFAULT_LAYER);

        decryptProgressBar.setIndeterminate(true);
        decryptProgressBar.setBounds(20, 80, 276, 22);
        jLayeredPane17.add(decryptProgressBar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel42.setText("กรุณารอสักครู่...");
        jLabel42.setBounds(20, 40, 140, 27);
        jLayeredPane17.add(jLabel42, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel43.setText("กำลังปรับปรุงฐานข้อมูล");
        jLabel43.setBounds(100, 10, 158, 25);
        jLayeredPane17.add(jLabel43, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout adjustDatabaseDialogLayout = new javax.swing.GroupLayout(adjustDatabaseDialog.getContentPane());
        adjustDatabaseDialog.getContentPane().setLayout(adjustDatabaseDialogLayout);
        adjustDatabaseDialogLayout.setHorizontalGroup(
            adjustDatabaseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
        );
        adjustDatabaseDialogLayout.setVerticalGroup(
            adjustDatabaseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 102, 51));
        setUndecorated(true);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        jLayeredPane4.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/Image/bg3.jpg")))); // NOI18N

        jLayeredPane1.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/Image/head_main_new.jpg")))); // NOI18N

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/minimize-green20x20.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton6.setBounds(830, 10, 20, 20);
        jLayeredPane1.add(jButton6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/close-green20x20.png"))); // NOI18N
        jButton5.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/close-green2.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton5.setBounds(850, 10, 20, 20);
        jLayeredPane1.add(jButton5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane1.setBounds(10, 10, 880, 100);
        jLayeredPane4.add(jLayeredPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane2.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/Image/bg_bg880x60.png")))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("ตำแหน่ง :");
        jLabel2.setBounds(380, 10, 63, 38);
        jLayeredPane2.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ชื่อผู้ใช้ :");
        jLabel1.setBounds(50, 10, 55, 38);
        jLayeredPane2.add(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        nameLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        nameLabel.setBounds(120, 10, 185, 38);
        jLayeredPane2.add(nameLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        positionLabel.setBackground(new java.awt.Color(255, 255, 255));
        positionLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        positionLabel.setBounds(460, 10, 207, 38);
        jLayeredPane2.add(positionLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        logoutButton.setText("ออกจากระบบ");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        logoutButton.setBounds(710, 10, 120, 38);
        jLayeredPane2.add(logoutButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane2.setBounds(20, 150, 860, 60);
        jLayeredPane4.add(jLayeredPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/button_peet/button_connect_nomousemove.jpg"))); // NOI18N
        jButton1.setToolTipText("ลงทะเบียน Device และเชื่อมต่อกับ Device");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setMaximumSize(new java.awt.Dimension(200, 150));
        jButton1.setMinimumSize(new java.awt.Dimension(200, 150));
        jButton1.setPreferredSize(new java.awt.Dimension(200, 150));
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/button_peet/button_connect.jpg"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.setBounds(160, 60, 200, 150);
        jLayeredPane3.add(jButton1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/button_peet/button_transfer_nomousemove.jpg"))); // NOI18N
        jButton2.setToolTipText("ถ่ายโอนข้อมูลระหว่าง Server แล Device");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/button_peet/button_transfer.jpg"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton2.setBounds(520, 60, 200, 150);
        jLayeredPane3.add(jButton2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane3.setBounds(10, 220, 880, 260);
        jLayeredPane4.add(jLayeredPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane5.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/Image/footer_logo.jpg")))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ศูนย์เทคโนโลยีอิเล็กทรอนิกส์และคอมพิวเตอร์แห่งชาติ พัฒนาร่วมกับ สำนักงานสาธารณสุขจังหวัดอุบลราชธานี");
        jLabel6.setBounds(170, 40, 610, 15);
        jLayeredPane5.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("112 อุทยานวิทยาศาสตร์ประเทศไทย ถนนพหลโยธิน ตำบลคลองหนึ่ง อำเภอคลองหลวง จังหวัดปทุมธานี 12120");
        jLabel7.setBounds(160, 60, 580, 15);
        jLayeredPane5.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("สงวนลิขสิทธิ์ ตาม พรบ.ลิขสิทธิ์ พ.ศ. 2537");
        jLabel3.setBounds(330, 20, 220, 15);
        jLayeredPane5.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("E-mail: watcharakon.noothong@nectec.or.th");
        jLabel10.setBounds(310, 100, 270, 15);
        jLayeredPane5.add(jLabel10, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("โทรศัพท์ 02-564-6900 ต่อ 2513 โทรสาร 02-564-6871 ");
        jLabel9.setBounds(290, 80, 320, 15);
        jLayeredPane5.add(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane5.setBounds(10, 500, 880, 140);
        jLayeredPane4.add(jLayeredPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane6.setBounds(20, 120, 0, 0);
        jLayeredPane4.add(jLayeredPane6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane7.setBackground(new java.awt.Color(0, 153, 51));
        jLayeredPane7.setBounds(20, 130, 880, 20);
        jLayeredPane4.add(jLayeredPane7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(new java.awt.Color(102, 204, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(740, Short.MAX_VALUE)
                .addComponent(versionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(versionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBounds(10, 110, 880, 20);
        jLayeredPane4.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Main.closeMain();
        connForm.setLocation(Service.frameLocation);
        connForm.setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Main.closeMain();
        transForm.setLocation(Service.frameLocation);
        transForm.setDefaultLogLabel();
        transForm.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
        FileManager filemanager = new FileManager();
        filemanager.deleteFile(Service.mJHCISPath);
        filemanager.deleteFile(Service.mJHCISsdbPath);
        filemanager.deleteFile(Service.uJHCISPath);
        Service.mainform.setVisible(false);
        Service.loginform.setVisible(true);
        
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        FileManager filemanager = new FileManager();
        filemanager.deleteFile(Service.mJHCISPath);
        filemanager.deleteFile(Service.mJHCISsdbPath);
        filemanager.deleteFile(Service.uJHCISPath);
        System.exit(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.setState(Frame.ICONIFIED);
}//GEN-LAST:event_jButton6ActionPerformed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        Point current = this.getScreenLocation(evt);
        Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
        (int) current.getY() - (int) start_drag.getY());
        Point new_location = new Point((int) (this.start_loc.getX() + offset.getX()), (int) (this.start_loc.getY() + offset.getY()));
        Service.frameLocation = new_location;
        this.setLocation(new_location);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        start_drag = this.getScreenLocation(evt);
        start_loc = this.getLocation();
    }//GEN-LAST:event_formMousePressed

    private void adjustDatabaseDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_adjustDatabaseDialogWindowClosing
        // TODO add your handling code here:
      /*  RunAdb runAdbKill = new RunAdb("./FFC/adb/adbKillServer.bat");
        runAdbKill.runAdb();
        RunAdb runAdb = new RunAdb("./FFC/adb/adbStartServer.bat");
        Thread run = new Thread(runAdb);
        run.start();
        FileManager fileManager = new FileManager();
        fileManager.deleteFile("./FFC/db_tmp/mJHCIS.sdb");*/
}//GEN-LAST:event_adjustDatabaseDialogWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }
    
    public static void closeForm(int check)
    {
        switch(check)
        {
            case 1 : connForm.setVisible(false);
                break;
            case 2 : transForm.setVisible(false);
                break;
            case 3 : maintenForm.setVisible(false);
                break;
        }
    }
    
    public static void setUser()
    {
        nameLabel.setText("  " + Service.user.getUserName());
        positionLabel.setText("  " + Service.user.getOfficerposition());
    }
    
    public static void openConnectForm()
    {
        connForm.setVisible(true);
    }

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog adjustDatabaseDialog;
    private javax.swing.JLabel adjustTimeLabel;
    private javax.swing.JProgressBar decryptProgressBar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane17;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane6;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logoutButton;
    public static javax.swing.JLabel nameLabel;
    public static javax.swing.JLabel positionLabel;
    private javax.swing.JLabel versionLabel;
    // End of variables declaration//GEN-END:variables

    Point getScreenLocation(MouseEvent e) {
    Point cursor = e.getPoint();
    Point target_location = this.getLocationOnScreen();
    return new Point((int) (target_location.getX() + cursor.getX()),
        (int) (target_location.getY() + cursor.getY()));
    }

    private void setbackground(JComponent component, String imagePath){
        ImageIcon earth = new ImageIcon(imagePath);
        JLabel backlabel = new JLabel(earth);
        component.add(backlabel, new Integer(Integer.MIN_VALUE));
        backlabel.setBounds(0, 0, earth.getIconWidth(), earth.getIconHeight());
    }

    private void setAllbg(){
        this.setbackground(this.jLayeredPane1,".\\src\\Image\\head_main_new.jpg");
        this.setbackground(this.jLayeredPane4,".\\src\\Image\\bg3.jpg");
        this.setbackground(this.jLayeredPane2,".\\src\\Image\\bg_bg880x60.png");
        this.setbackground(this.jLayeredPane5,".\\src\\Image\\footer_logo.jpg");
        this.setbackground(this.jLayeredPane17,".\\src\\Image\\decryptdialog_bg.png");
        
        
    }

    public void setAdjustTimeLabel(String time){
        this.adjustTimeLabel.setText(time);
    }

    public void setVisibleAdjustDatabaseDialog(boolean set){
        this.adjustDatabaseDialog.setVisible(set);
    }

    private void setVersion(){
        this.versionLabel.setText("Version "+Service.autosyncVersion);
    }
}