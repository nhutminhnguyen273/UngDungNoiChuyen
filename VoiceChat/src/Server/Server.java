package Server;

import java.io.*;
import java.net.*;
import java.sql.*;


public class Server {
    public static DBAccess dbAccess;
    
    public static void main(String[] args) {
        int port = 12345; // Cổng để Server lắng nghe
        dbAccess = new DBAccess(); // Khởi tạo kết nối cơ sở dữ liệu

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server đang chạy trên cổng " + port);

            while (true) {
                // Chấp nhận kết nối từ Client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Kết nối từ: " + clientSocket.getInetAddress().getHostAddress());

                // Xử lý yêu cầu từ Client trong một luồng riêng
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi chạy Server: " + e.getMessage());
        }
    }
    
        // Lớp xử lý từng Client
    private static class ClientHandler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Thiết lập luồng đọc/ghi
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Đọc lệnh từ Client
                String command;
                while ((command = in.readLine()) != null) {
                    System.out.println("Nhận lệnh từ Client: " + command);

                    // Phân tích và xử lý lệnh
                    if (command.startsWith("QUERY")) {
                        String query = command.substring(6); // Bỏ từ "QUERY "
                        ResultSet rs = dbAccess.query(query);
                        if (rs != null) {
                            try {
                                while (rs.next()) {
                                    out.println(rs.getString(1)); // Trả kết quả cột đầu tiên
                                }
                            } catch (SQLException e) {
                                out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
                                e.printStackTrace();
                            }
                        } else {
                            out.println("Kết quả truy vấn null hoặc không hợp lệ.");
                        }
                    } else if (command.startsWith("UPDATE")) {
                        String update = command.substring(7); // Bỏ từ "UPDATE "
                        int rowsAffected = dbAccess.update(update);
                        out.println("Hàng bị ảnh hưởng: " + rowsAffected);
                    }else if (command.startsWith("REGISTER")) {
                        String[] parts = command.split(";");
                        if (parts.length == 5) {
                            String id = parts[0];
                            String username = parts[1];
                            String password = parts[2];
                            String ip = parts[3];
                            int port = Integer.parseInt(parts[4]);

                            String sql = "INSERT INTO user (Id, Username, Password, IP, Port) VALUES (?, ?, ?, ?, ?)";
                            int result = dbAccess.update(sql, id, username, password, ip, port);
                            out.println(result > 0 ? "SUCCESS" : "FAILURE");
                        } else {
                            out.println("INVALID_COMMAND");
                        }
                    }
                    else if (command.equals("EXIT")) {
                        out.println("Đóng kết nối.");
                        break;
                    } else {
                        out.println("Lệnh không hợp lệ.");
                    }
                }
            } catch (IOException e) {
                System.err.println("Lỗi trong ClientHandler: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Không thể đóng socket: " + e.getMessage());
                }
            }
        }
    }
}
