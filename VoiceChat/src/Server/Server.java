package Server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    // Đăng ký User mới
    public boolean register(String username, String password, String ip, int port) {
        String sql = "INSERT INTO User (Id, Username, Password, IP, Port) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, ip);
            stmt.setInt(4, port);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đăng nhập User
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Đăng nhập thành công nếu có kết quả
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin User
    public boolean updateUser(int id, String newIp, int newPort) {
        String sql = "UPDATE User SET IP = ?, Port = ? WHERE Id = ?";
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newIp);
            stmt.setInt(2, newPort);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả Users
    public List<String> getAllUsers() {
        String sql = "SELECT * FROM User";
        List<String> users = new ArrayList<>();
        try (Connection conn = MyConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                users.add(rs.getString("Username") + " - " + rs.getString("IP") + ":" + rs.getInt("Port"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}