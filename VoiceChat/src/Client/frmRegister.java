package Client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.*;
import java.net.*;
import java.util.Random;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class frmRegister extends javax.swing.JFrame {


    public frmRegister() {
        initComponents();
    }


    @SuppressWarnings("unchecked")

private void initComponents() {
    // Apply modern UI
    ModernUI.applyModernUI();

    // Initialize components with modern style
    txtUsername = ModernUI.createTextField("Nhập tên đăng nhập");
    txtPassword = ModernUI.createPasswordField();
    txtConfirmPass = ModernUI.createPasswordField();
    jLabel1 = ModernUI.createLabel("Username");
    jLabel2 = ModernUI.createLabel("Mật khẩu");
    jLabel3 = ModernUI.createLabel("Xác nhận mật khẩu");
    btnDangKy = ModernUI.createPrimaryButton("Đăng ký");
    btnThoat = ModernUI.createSecondaryButton("Thoát");
    jLabel4 = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
    jLabel4.setFont(ModernUI.TITLE_FONT);
    jLabel4.setForeground(ModernUI.PRIMARY_DARK);

    // Create main panel with gradient background and shadow
    JPanel mainPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create gradient background
            GradientPaint gp = new GradientPaint(
                0, 0, Color.WHITE,
                0, getHeight(), new Color(245, 245, 245)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(5, 5, getWidth()-10, getHeight()-10, 20, 20);

            // Add subtle shadow
            int shadowSize = 5;
            for (int i = 0; i < shadowSize; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i * 4));
                g2.drawRoundRect(i, i, getWidth()-2*i, getHeight()-2*i, 20, 20);
            }

            g2.dispose();
        }
    };
    mainPanel.setOpaque(false);
    mainPanel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 30, 10, 30);

    // Add header with icon
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    headerPanel.setOpaque(false);
    
    ImageIcon originalIcon = new ImageIcon("F:\\HOCTAPTHATCHAMCHITAONENTUONGLAITUOISANG\\Nam 4\\MON_HOC\\LTM\\BAOCAO_VOIP\\UngDungNoiChuyen\\image\\icon\\signup_icon.png");

    Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

    ImageIcon registerIcon = new ImageIcon(scaledImage);    JLabel iconLabel = new JLabel();
    
    iconLabel.setIcon(registerIcon);
    headerPanel.add(iconLabel);
    headerPanel.add(Box.createHorizontalStrut(15));
    headerPanel.add(jLabel4);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(30, 30, 30, 30);
    mainPanel.add(headerPanel, gbc);

    // Add form fields
    gbc.insets = new Insets(5, 30, 5, 30);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Username section
    gbc.gridy = 1;
    mainPanel.add(createFieldPanel(jLabel1, txtUsername), gbc);

    // Password section
    gbc.gridy = 2;
    mainPanel.add(createFieldPanel(jLabel2, txtPassword), gbc);

    // Confirm password section
    gbc.gridy = 3;
    mainPanel.add(createFieldPanel(jLabel3, txtConfirmPass), gbc);

    // Buttons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(btnDangKy);
    buttonPanel.add(btnThoat);

    gbc.gridy = 4;
    gbc.insets = new Insets(30, 30, 20, 30);
    mainPanel.add(buttonPanel, gbc);

    // Add event handlers
    btnDangKy.addActionListener(evt -> btnDangKyActionPerformed(evt));
    btnThoat.addActionListener(evt -> btnThoatActionPerformed(evt));

    // Password strength indicator
    JProgressBar strengthIndicator = new JProgressBar(0, 100);
    strengthIndicator.setStringPainted(true);
    strengthIndicator.setString("Độ mạnh mật khẩu");
    gbc.gridy = 5;
    gbc.insets = new Insets(10, 30, 20, 30);
    mainPanel.add(strengthIndicator, gbc);

    // Add password strength listener
    txtPassword.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void changedUpdate(DocumentEvent e) { updateStrength(); }
        @Override
        public void removeUpdate(DocumentEvent e) { updateStrength(); }
        @Override
        public void insertUpdate(DocumentEvent e) { updateStrength(); }

        private void updateStrength() {
            String password = new String(txtPassword.getPassword());
            int strength = calculatePasswordStrength(password);
            strengthIndicator.setValue(strength);
            
            if (strength < 30) {
                strengthIndicator.setForeground(ModernUI.ERROR_COLOR);
            } else if (strength < 70) {
                strengthIndicator.setForeground(Color.ORANGE);
            } else {
                strengthIndicator.setForeground(ModernUI.SUCCESS_COLOR);
            }
        }
    });

    // Setup main frame
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    getContentPane().setBackground(ModernUI.BACKGROUND_COLOR);
    setLayout(new GridBagLayout());
    
    // Add main panel to frame
    GridBagConstraints frameGbc = new GridBagConstraints();
    frameGbc.insets = new Insets(20, 20, 20, 20);
    add(mainPanel, frameGbc);

    // Final frame settings
    setMinimumSize(new Dimension(450, 600));
    setLocationRelativeTo(null);
    pack();
}

private JPanel createFieldPanel(JLabel label, JComponent field) {
    JPanel panel = new JPanel(new GridLayout(2, 1, 5, 5));
    panel.setOpaque(false);
    panel.add(label);
    panel.add(field);
    return panel;
}

private int calculatePasswordStrength(String password) {
    int strength = 0;
    
    if (password.length() >= 8) strength += 25;
    if (password.matches(".*[A-Z].*")) strength += 25;
    if (password.matches(".*[a-z].*")) strength += 25;
    if (password.matches(".*[0-9].*")) strength += 25;
    if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) strength += 25;
    
    return Math.min(100, strength);
}

    private void btnDangKyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangKyActionPerformed
        try {
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPass.getPassword());
            String ip = InetAddress.getLocalHost().getHostAddress();
            Random random = new Random();
            int port = 1000 + random.nextInt(9000);

            // Kiểm tra thông tin nhập liệu
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
                return;
            }
            
            // Gửi thông tin đăng ký đến server
<<<<<<< HEAD
            try (Socket socket = new Socket("192.168.1.1", 12345); // Địa chỉ và cổng server
=======
            try (Socket socket = new Socket("192.168.0.105", 12345); // Địa chỉ và cổng server
>>>>>>> origin/tan
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // Chuỗi yêu cầu dạng "REGISTER,username,password,ip,port"
                String request = String.format("REGISTER,%s,%s,%s,%d", username, password, ip, port);
                out.println(request); // Gửi yêu cầu

                // Đọc phản hồi từ server
                String response = in.readLine();
                if ("REGISTER_SUCCESS".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                    clearFields(); // Xóa các trường nhập liệu sau khi đăng ký thành công
                } else if ("REGISTER_FAILED".equals(response)) {
                    JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Tên tài khoản đã tồn tại.");
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
    }//GEN-LAST:event_btnDangKyActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtConfirmPass.setText("");
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
