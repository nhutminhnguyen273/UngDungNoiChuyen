package Server;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerMain {
    
    public static void main(String[] args) {
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running on port 12345...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                // Xử lý client
                new Thread(() -> handleClient(socket, server)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket socket, Server server) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String command;
            while ((command = in.readLine()) != null) {
                String[] parts = command.split(",");
                String action = parts[0];
                switch (action) {
                    case "REGISTER":
                        boolean registered = server.register(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                        out.println(registered ? "REGISTER_SUCCESS" : "REGISTER_FAILED");
                        break;
                    case "LOGIN":
                        int port = server.login(parts[1], parts[2]);
                        if (port != -1) {
                            out.println("LOGIN_SUCCESS," + port); // Trả về thành công kèm port
                        } else {
                            out.println("LOGIN_FAILED");
                        }
                        break;
                    case "UPDATE":
                        boolean updated = server.updateUser(Integer.parseInt(parts[1]), parts[2], Integer.parseInt(parts[3]));
                        out.println(updated ? "UPDATE_SUCCESS" : "UPDATE_FAILED");
                        break;
                    case "GET_USERS":
                        List<String> users = server.getAllUsers();
                        out.println(String.join(";", users));
                        break;
                    case "UPDATE_IP":
                        boolean ipUpdated = server.updateIp(parts[1], parts[2]);
                        out.println(ipUpdated ? "UPDATE_SUCCESS" : "UPDATE_FAILED");
                        break;
                    default:
                        out.println("INVALID_COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}
