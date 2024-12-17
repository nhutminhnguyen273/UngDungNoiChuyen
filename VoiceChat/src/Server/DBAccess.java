package Server;

import java.sql.*;

public class DBAccess {
    public int update(String sql, Object... params) {
        try (Connection con = MyConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet query(String sql, Object... params) {
        try (Connection con = MyConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
