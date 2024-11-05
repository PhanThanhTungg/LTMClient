package view;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import BIN.Play;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


import controller.Client;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import model.Pikachu;

public class AdminFrm extends javax.swing.JFrame {
    List<Play> listPlay;
    List<Pikachu> listPikachu;
    public AdminFrm(List<Pikachu> listPikachu) {
        initComponents();
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf"); 
            SwingUtilities.updateComponentTreeUI(this);  // 'frame' là JFrame chính của bạn
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setTitle("Admin");
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.listPikachu = listPikachu;
        updateListPikachu(listPikachu);
        uploadButton.addActionListener(e -> uploadImage());
    }
    
    private void uploadImage(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "ddr3axv38",
            "api_key", "147594271812811",
            "api_secret", "KlZCzS0-PsCdv2TdvJf6Zu-pk-I"
        ));
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if(returnValue==JFileChooser.APPROVE_OPTION){
            try {
                File file = fileChooser.getSelectedFile();
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                String urlStr = (String) uploadResult.get("url");
                URL url = new URL(urlStr);
                System.out.println(url);
                Client.socketHandle.write("addPikachu,"+url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateListPikachu(List<Pikachu> listPikachu){ 
        this.listPikachu = listPikachu;
        pikachuPanel.removeAll();  
        pikachuPanel.setLayout(new GridLayout(0, 6, 10, 10));  
        for (Pikachu pikachu: listPikachu) {
            try {   
                ImageIcon icon = new ImageIcon(new URL(pikachu.getAvatar()));

                Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);

                JLabel label = new JLabel(icon);      
                label.setHorizontalAlignment(SwingConstants.CENTER);  
                label.putClientProperty("pikachuId", pikachu.getId());
                
                label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==2){
                        
                        int pikachuId = (int) ((JLabel) e.getSource()).getClientProperty("pikachuId");
                        try {
                            Client.socketHandle.write("Delete-pikachu,"+pikachuId);
                        } catch (IOException ex) {
                            Logger.getLogger(AdminFrm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        listPikachu.removeIf(p -> p.getId() == pikachuId);
                        updateListPikachu(listPikachu);
                        
                    }
                }
            });
                pikachuPanel.add(label);
            } catch (Exception e) {                         
                e.printStackTrace();
                JLabel errorLabel = new JLabel("Failed to load image");
                errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                pikachuPanel.add(errorLabel);
            }
        }

        pikachuPanel.revalidate();  
        pikachuPanel.repaint();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardLabel = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jPanel1 = new javax.swing.JPanel();
        boardLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        uploadButton = new javax.swing.JButton();
        pikachuPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        boardLabel.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        boardLabel.setForeground(new java.awt.Color(204, 204, 204));
        boardLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boardLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N
        boardLabel.setText("Server");

        label1.setText("label1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));

        boardLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        boardLabel2.setForeground(new java.awt.Color(204, 204, 204));
        boardLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boardLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N
        boardLabel2.setText("Admin");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(boardLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        uploadButton.setBackground(new java.awt.Color(102, 102, 102));
        uploadButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        uploadButton.setForeground(new java.awt.Color(255, 255, 255));
        uploadButton.setText("Thêm");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadButtonActionPerformed(evt);
            }
        });

        pikachuPanel.setBackground(new java.awt.Color(250, 250, 250));

        javax.swing.GroupLayout pikachuPanelLayout = new javax.swing.GroupLayout(pikachuPanel);
        pikachuPanel.setLayout(pikachuPanelLayout);
        pikachuPanelLayout.setHorizontalGroup(
            pikachuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 725, Short.MAX_VALUE)
        );
        pikachuPanelLayout.setVerticalGroup(
            pikachuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );

        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("Click 2 lần vào pokemon để xóa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(uploadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pikachuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(pikachuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uploadButton)
                    .addComponent(jLabel1))
                .addGap(23, 23, 23))
        );

        jTabbedPane1.addTab("Quản lý Pikachu", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 779, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Quản lý người dùng", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void uploadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadButtonActionPerformed
        
    }//GEN-LAST:event_uploadButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel boardLabel;
    private javax.swing.JLabel boardLabel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    private javax.swing.JPanel pikachuPanel;
    private javax.swing.JButton uploadButton;
    // End of variables declaration//GEN-END:variables
}
