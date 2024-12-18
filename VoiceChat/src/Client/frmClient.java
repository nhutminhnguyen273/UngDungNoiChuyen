package Client;

import static Client.frmLogin.port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
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

        btnKetNoi = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listUsers = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        btnThongBao = new javax.swing.JTextArea();
        btnHienThi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jScrollPane1.setViewportView(listUsers);

        btnThongBao.setColumns(20);
        btnThongBao.setRows(5);
        jScrollPane2.setViewportView(btnThongBao);

        btnHienThi.setText("Hiển thị");
        btnHienThi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHienThiActionPerformed(evt);
            }
        });

        jLabel1.setText("Nói chuyện");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnHienThi, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnKetNoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHuy)))
                .addGap(95, 95, 95))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(293, 293, 293))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHienThi))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKetNoi)
                    .addComponent(btnHuy))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKetNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKetNoiActionPerformed

        startConnection();
    }//GEN-LAST:event_btnKetNoiActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        if (sender != null) 
            sender.stopSending();
        if (receiver != null) 
            receiver.stopReceiving();
        btnKetNoi.setEnabled(true);
        btnHuy.setEnabled(false);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnHienThiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHienThiActionPerformed
        loadUserList();
    }//GEN-LAST:event_btnHienThiActionPerformed

    private void loadUserList() {
        List<String> users = fetchUsersFromServer();
        DefaultListModel<String> model = new DefaultListModel<>();

        if (users != null) {
            for (String user : users) {
                model.addElement(user);
            }
        } else {
            model.addElement("Không thể lấy danh sách người dùng.");
        }

        listUsers.setModel(model);
    }

    private List<String> fetchUsersFromServer() {
        List<String> users = new ArrayList<>();
        try (Socket socket = new Socket("192.168.1.4", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("GET_USERS");

            String response = in.readLine();
            if (response != null && !response.isEmpty()) {
                // Phản hồi được phân tách bởi dấu ";"
                String[] userArray = response.split(";");
                for (String user : userArray) {
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    
//    private void listUsersValueChanged(javax.swing.event.ListSelectionEvent evt) {
//        // Kiểm tra xem có chọn mục nào không
//        if (!evt.getValueIsAdjusting()) {
//            String selectedUser = listUsers.getSelectedValue();
//            if (selectedUser != null && !selectedUser.isEmpty()) {
//                // Dữ liệu hiển thị dạng Username - IP:Port
//                String[] parts = selectedUser.split(" - ");
//                if (parts.length == 2) {
//                    String[] ipAndPort = parts[1].split(":");
//                    if (ipAndPort.length == 2) {
//                        String ip = ipAndPort[0];
//                        String portReceiveText = ipAndPort[1];
//
//                        try {
//                            int portReceive = Integer.parseInt(portReceiveText);
//
//                            // Lưu giá trị IP và PortReceive vào biến toàn cục (nếu cần)
//                            JOptionPane.showMessageDialog(this, 
//                                "Selected IP: " + ip + ", Port: " + portReceive, 
//                                "Thông báo", 
//                                JOptionPane.INFORMATION_MESSAGE);
//
//                            // Có thể kích hoạt kết nối ngay tại đây
//                            startConnection();
//                        } catch (NumberFormatException e) {
//                            JOptionPane.showMessageDialog(this, 
//                                "Port không hợp lệ.", 
//                                "Lỗi", 
//                                JOptionPane.ERROR_MESSAGE);
//                        }
//                    }
//                }
//            }
//        }
//    }
    
    private void startConnection() {
        // Kiểm tra nếu người dùng đã chọn một mục trong danh sách
        String selectedUser = listUsers.getSelectedValue();
        if (selectedUser != null && !selectedUser.isEmpty()) {
            String[] parts = selectedUser.split(" - ");
            if (parts.length == 2) {
                String[] ipAndPort = parts[1].split(":");
                if (ipAndPort.length == 2) {
                    String ip = ipAndPort[0];
                    String portReceiveText = ipAndPort[1];

                    try {
                        int portReceive = Integer.parseInt(portReceiveText);

                        // Kiểm tra giá trị hợp lệ của cổng
                        int portSend = port; // Bạn có thể lấy port từ nơi khác nếu cần

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
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng từ danh sách.", 
                                          "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHienThi;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnKetNoi;
    private javax.swing.JTextArea btnThongBao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listUsers;
    // End of variables declaration//GEN-END:variables
}