package Client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class frmLogin extends javax.swing.JFrame {

    public static String ipUser;
    public static int port;
    public static String USERNAME;

    public frmLogin() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
// In frmLogin.java, replace the initComponents() method with:

private void initComponents() {
    // Apply modern UI
    ModernUI.applyModernUI();

    // Initialize components with modern style
    txtUsername = ModernUI.createTextField("Nhập tên đăng nhập");
    txtPassword = ModernUI.createPasswordField();
    jLabel1 = ModernUI.createLabel("Username");
    jLabel2 = ModernUI.createLabel("Mật khẩu");
    btnDangNhap = ModernUI.createPrimaryButton("Đăng nhập");
    btnDangKy = ModernUI.createSecondaryButton("Đăng ký");
    lbDangNhap = new JLabel("ĐĂNG NHẬP");
    lbDangNhap.setFont(ModernUI.TITLE_FONT);
    lbDangNhap.setForeground(ModernUI.PRIMARY_DARK);

    // Create main panel with shadow border
    JPanel mainPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
             // Paint white background with rounded corners
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 20, 20);

            // Paint shadow
            int shadowSize = 5;
            for (int i = 0; i < shadowSize; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i));
                g2.drawRoundRect(i, i, getWidth()-2*i, getHeight()-2*i, 20, 20);
            }

            g2.dispose();
        }
    };
    mainPanel.setOpaque(false);
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 30, 10, 30);

    // Add login header with icon
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setOpaque(false);
    
    ImageIcon originalIcon2 = new ImageIcon("D:\\Learning\\Hutech\\Lap trinh mang may tinh\\Do an\\image\\icon\\login_icon.png");

    Image scaledImage = originalIcon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

    ImageIcon userIcon = new ImageIcon(scaledImage);    JLabel iconLabel = new JLabel();
    
    
    iconLabel.setIcon(userIcon);
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(15));
    headerPanel.add(lbDangNhap);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(30, 30, 30, 30);
    mainPanel.add(headerPanel, gbc);

    // Add components with proper spacing
    gbc.insets = new Insets(5, 30, 5, 30);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = 1;

    // Username section
    gbc.gridy = 1;
    mainPanel.add(jLabel1, gbc);
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainPanel.add(txtUsername, gbc);

    // Password section
    gbc.gridy = 3;
    gbc.fill = GridBagConstraints.NONE;
    mainPanel.add(jLabel2, gbc);
    gbc.gridy = 4;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainPanel.add(txtPassword, gbc);

    // Buttons panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(btnDangNhap);
    buttonPanel.add(btnDangKy);

    gbc.gridy = 5;
    gbc.insets = new Insets(30, 30, 20, 30);
    mainPanel.add(buttonPanel, gbc);

    // Add event handlers
    btnDangNhap.addActionListener(evt -> btnDangNhapActionPerformed(evt));
    btnDangKy.addActionListener(evt -> btnDangKyActionPerformed(evt));

    // Setup main frame
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    getContentPane().setBackground(ModernUI.BACKGROUND_COLOR);
    setLayout(new GridBagLayout());
    
    // Add main panel to frame with padding
    GridBagConstraints frameGbc = new GridBagConstraints();
    frameGbc.insets = new Insets(20, 20, 20, 20);
    add(mainPanel, frameGbc);

    // Additional frame settings
    setMinimumSize(new Dimension(400, 500));
    setLocationRelativeTo(null);
    pack();

    // Add hover effect to buttons
    addButtonHoverEffect(btnDangNhap);
    addButtonHoverEffect(btnDangKy);
}

private void addButtonHoverEffect(JButton button) {
    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    });
}

   // Add animation effect for error messages
private void showError(String message) {
    JPanel errorPanel = new JPanel();
    errorPanel.setBackground(new Color(255, 82, 82));
    JLabel errorLabel = new JLabel(message);
    errorLabel.setForeground(Color.WHITE);
    errorLabel.setFont(ModernUI.REGULAR_FONT);
    errorPanel.add(errorLabel);
    
    // Add to frame with animation
    // ... (animation code here)
}

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
