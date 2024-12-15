package Client;

import javax.swing.JOptionPane;


public class frmClient extends javax.swing.JFrame {
    
    private VoiceSender sender;
    private VoiceReceiver receiver;

    public frmClient() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtIP = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPortSend = new javax.swing.JTextField();
        btnKetNoi = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        txtPortReceiver = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Địa chỉ IP");

        jLabel2.setText("Port Send");

        btnKetNoi.setText("Kết nối");
        btnKetNoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKetNoiActionPerformed(evt);
            }
        });

        btnHuy.setText("Hủy kết nối");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        jLabel3.setText("Port Receiver");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPortReceiver)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnKetNoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addComponent(btnHuy))
                    .addComponent(txtIP)
                    .addComponent(txtPortSend))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPortSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPortReceiver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKetNoi)
                    .addComponent(btnHuy))
                .addContainerGap(130, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKetNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetNoiActionPerformed
        String ip = txtIP.getText().trim();
        String portSendText = txtPortSend.getText().trim();
        String portReceiveText = txtPortReceiver.getText().trim();
        
        // Kiểm tra nếu các trường nhập rỗng
        if (ip.isEmpty() || portSendText.isEmpty() || portReceiveText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ IP, Port Send và Port Receive.", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int portSend = Integer.parseInt(portSendText);
            int portReceive = Integer.parseInt(portReceiveText);

            // Kiểm tra giá trị hợp lệ của cổng
            if (portSend <= 0 || portSend > 65535 || portReceive <= 0 || portReceive > 65535) {
                JOptionPane.showMessageDialog(this, "Port phải nằm trong khoảng từ 1 đến 65535.", 
                                              "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Khởi tạo Sender và Receiver
            sender = new VoiceSender(ip, portSend);
            receiver = new VoiceReceiver(portReceive);

            sender.start();
            receiver.start();

            btnKetNoi.setEnabled(false);
            btnHuy.setEnabled(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Port phải là số nguyên hợp lệ.", 
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnKetNoiActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        if (sender != null) 
            sender.stopSending();
        if (receiver != null) 
            receiver.stopReceiving();
        btnKetNoi.setEnabled(true);
        btnHuy.setEnabled(false);
    }//GEN-LAST:event_btnHuyActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnKetNoi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtPortReceiver;
    private javax.swing.JTextField txtPortSend;
    // End of variables declaration//GEN-END:variables
}
