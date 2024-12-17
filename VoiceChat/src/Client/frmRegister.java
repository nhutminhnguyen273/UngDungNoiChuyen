package Client;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Random;
import javax.swing.JOptionPane;


public class frmRegister extends javax.swing.JFrame {


    public frmRegister() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnDangKy = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtConfirmPass = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username");

        jLabel2.setText("Mật khẩu");

        jLabel3.setText("Xác nhận mật khẩu");

        btnDangKy.setText("Đăng ký");
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

        btnThoat.setText("Thoát");
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        jLabel4.setText("Đăng Ký");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDangKy)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(btnThoat))
                    .addComponent(txtUsername)
                    .addComponent(txtPassword)
                    .addComponent(txtConfirmPass))
                .addGap(91, 91, 91))
            .addGroup(layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtConfirmPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDangKy)
                    .addComponent(btnThoat))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        try {
            String id = java.util.UUID.randomUUID().toString();
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPass.getText();
            String ip = InetAddress.getLocalHost().getHostAddress();
            Random random = new Random();
            int port = 1000 + random.nextInt(9000);

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
                return;
            }

            try (Socket socket = new Socket("192.168.1.4", 12345);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("REGISTER;" + id + ";" + username + ";" + password + ";" + ip + ";" + port);
                String response = in.readLine();
                JOptionPane.showMessageDialog(this, response.equals("SUCCESS") ? "Đăng ký thành công!" : "Đăng ký thất bại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    String getVMnet8IPAddress() {
        try {
            // Lấy interface mạng VMnet8
            NetworkInterface networkInterface = NetworkInterface.getByName("VMnet8");

            if (networkInterface != null) {
                // Lấy danh sách các địa chỉ IP gắn với interface này
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                // Duyệt qua các địa chỉ IP
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address) { // Lấy IPv4
                        return inetAddress.getHostAddress(); // Trả về địa chỉ IP
                    }
                }
            } else {
                System.out.println("Không tìm thấy interface VMnet8");
            }
        } catch (SocketException e) {
            System.err.println("Lỗi khi lấy địa chỉ IP: " + e.getMessage());
        }
        return "Không lấy được IP";
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField txtConfirmPass;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
