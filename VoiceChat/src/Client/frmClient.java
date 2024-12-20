package Client;

import static Client.frmLogin.port;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class frmClient extends javax.swing.JFrame {
    
    private VoiceSender sender;
    private VoiceReceiver receiver;

    public frmClient() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
   // In frmClient.java, replace the initComponents() method with:

private void initComponents() {
    // Apply Modern UI
    ModernUI.applyModernUI();

    // Initialize components
    btnKetNoi = new ModernUI.ModernButton("Kết nối", true);
    btnHuy = new ModernUI.ModernButton("Hủy kết nối", false);
    listUsers = new ModernUI.ModernList();
    txtThongBao = new JTextArea();
    txtThongBao.setFont(ModernUI.REGULAR_FONT);
    btnHienThi = new ModernUI.ModernButton("Hiển thị", false);
    jLabel1 = new JLabel("VOICE CHAT", SwingConstants.CENTER);
    jLabel1.setFont(ModernUI.TITLE_FONT);
    jLabel1.setForeground(ModernUI.TEXT_COLOR);
    txtSearch = new ModernUI.ModernTextField("Nhập tên người dùng để tìm kiếm...");
    btnSearch = new ModernUI.ModernButton("Tìm kiếm", true);

    // Scroll panes
    jScrollPane1 = new ModernUI.ModernScrollPane(listUsers);
    jScrollPane2 = new ModernUI.ModernScrollPane(txtThongBao);

    // Frame settings
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setBackground(ModernUI.BACKGROUND_COLOR);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);

    // Title label
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(20, 5, 30, 5);
    add(jLabel1, gbc);

    // Search panel
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    searchPanel.setBackground(ModernUI.BACKGROUND_COLOR);
    txtSearch.setPreferredSize(new Dimension(300, 30));
    searchPanel.add(txtSearch);
    searchPanel.add(btnSearch);

    gbc.gridy = 1;
    add(searchPanel, gbc);

    // User list panel
    JPanel usersPanel = new JPanel(new BorderLayout(10, 0));
    usersPanel.setBackground(ModernUI.BACKGROUND_COLOR);
    usersPanel.add(btnHienThi, BorderLayout.WEST);

    JPanel listPanel = new JPanel(new BorderLayout(5, 5));
    listPanel.setBackground(ModernUI.BACKGROUND_COLOR);
    listPanel.add(new JLabel("Danh sách người dùng"), BorderLayout.NORTH);
    listPanel.add(jScrollPane1, BorderLayout.CENTER);
    usersPanel.add(listPanel, BorderLayout.CENTER);

    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    add(usersPanel, gbc);

    // Messages area
    JPanel messagesPanel = new JPanel(new BorderLayout(5, 5));
    messagesPanel.setBackground(ModernUI.BACKGROUND_COLOR);
    messagesPanel.add(new JLabel("Thông báo"), BorderLayout.NORTH);
    messagesPanel.add(jScrollPane2, BorderLayout.CENTER);

    gbc.gridy = 3;
    gbc.weighty = 0.5;
    add(messagesPanel, gbc);

    // Control buttons panel
    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    controlPanel.setBackground(ModernUI.BACKGROUND_COLOR);
    controlPanel.add(btnKetNoi);
    controlPanel.add(btnHuy);

    gbc.gridy = 4;
    gbc.weighty = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(controlPanel, gbc);

    // Add action listeners
    btnKetNoi.addActionListener(evt -> btnKetNoiActionPerformed(evt));
    btnHuy.addActionListener(evt -> btnHuyActionPerformed(evt));
    btnHienThi.addActionListener(evt -> btnHienThiActionPerformed(evt));
    btnSearch.addActionListener(evt -> btnSearchActionPerformed(evt));

    // Button initial states
    btnHuy.setEnabled(false);

    // Window settings
    setMinimumSize(new Dimension(600, 700));
    setLocationRelativeTo(null);
    pack();

    // Add window listener for cleanup
    addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            if (sender != null) sender.stopSending();
            if (receiver != null) receiver.stopReceiving();
        }
    });
}


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

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String user = fetchUserByName(txtSearch.getText());
        DefaultListModel<String> model = new DefaultListModel<>();
        if (user != null) {
            model.addElement(user);
        }
        listUsers.setModel(model);
    }//GEN-LAST:event_btnSearchActionPerformed

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
        try (Socket socket = new Socket("192.168.0.105", 12345);
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
    
    private String fetchUserByName(String username) {
        String userInfo = null;
        try (Socket socket = new Socket("192.168.0.105", 12345);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("GET_USER," + username);

            String response = in.readLine();
            if (response != null && response.startsWith("USER_INFO,")) {
                userInfo = response.substring("USER_INFO,".length());
            } else if (response != null && response.equals("USER_NOT_FOUND")) {
                System.out.println("User not found.");
            } else {
                System.out.println("Invalid response from server.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

   
    
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
                        int portSend = port;

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
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHienThi;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnKetNoi;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listUsers;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextArea txtThongBao;
    // End of variables declaration//GEN-END:variables
}