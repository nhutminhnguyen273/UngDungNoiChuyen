package Client;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class frmLogin extends javax.swing.JFrame {

    public static String ipUser;
    public static int port;
    public static String USERNAME;

    public frmLogin() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDangNhap = new javax.swing.JButton();
        btnDangKy = new javax.swing.JButton();
        lbDangNhap = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Username");

        jLabel2.setText("Mật khẩu");

        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        btnDangKy.setText("Đăng ký");
        btnDangKy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangKyActionPerformed(evt);
            }
        });

        lbDangNhap.setText("Đăng Nhập");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnDangNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addComponent(btnDangKy))
                    .addComponent(txtUsername)
                    .addComponent(txtPassword))
                .addGap(113, 113, 113))
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(lbDangNhap)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lbDangNhap)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDangNhap)
                    .addComponent(btnDangKy))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        try {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String ip = InetAddress.getLocalHost().getHostAddress();

            // Kiểm tra thông tin nhập liệu
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }
            
            // Gửi thông tin đăng ký đến server
            try (Socket socket = new Socket("192.168.1.1", 12345); // Địa chỉ và cổng server
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Chuỗi yêu cầu dạng "REGISTER,username,password,ip,port"
                String request = String.format("LOGIN,%s,%s", username, password);
                out.println(request); // Gửi yêu cầu

                // Đọc phản hồi từ server
                String response = in.readLine();
                if (response.startsWith("LOGIN_SUCCESS")) {
                    // Phân tách phản hồi để lấy port
                    String[] responseParts = response.split(",");
                    int userPort = Integer.parseInt(responseParts[1]); // Lấy port từ phản hồi

                    JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Port: " + userPort);

                    // Đăng nhập thành công -> Cập nhật IP
                    String updateIpRequest = String.format("UPDATE_IP,%s,%s", username, ip);
                    out.println(updateIpRequest); // Gửi yêu cầu cập nhật IP
                    String updateResponse = in.readLine();

                    if ("UPDATE_SUCCESS".equals(updateResponse)) {
                        JOptionPane.showMessageDialog(this, "Đăng nhập và cập nhật IP thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Đăng nhập thành công nhưng không thể cập nhật IP!");
                    }

                    // Truyền Port vào biến static (hoặc sử dụng cho mục đích khác)
                    port = userPort;
                    USERNAME = username;
                    frmClient frm = new frmClient();
                    frm.setVisible(true);
                    this.dispose(); // Đóng cửa sổ hiện tại
                } else if ("LOGIN_FAILED".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Đăng nhập thất bại");
                } else {
                    JOptionPane.showMessageDialog(this, "Phản hồi không xác định từ server: " + response);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến server: " + ex.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        frmRegister frm = new frmRegister();
        frm.setVisible(true);
    }//GEN-LAST:event_btnDangKyActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangKy;
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbDangNhap;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
