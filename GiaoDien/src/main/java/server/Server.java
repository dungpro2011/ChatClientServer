/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author TienDung
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TienDung
 */
public class Server {

    private static Socket socket = null;
    private static ServerSocket server = null;
    private static BufferedWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(5000);
            System.out.println("Server started... \n"
                    + "Waitting client connecting...");
            socket = server.accept();
            System.out.println("Client connected is port " + socket.getPort() + " ip is " + socket.getInetAddress());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String line = in.readLine();
                if (line.equals("bye")) {
                    break;
                }
                System.out.println("Server get: " + line);
                StringBuilder newline = new StringBuilder();
                newline.append(line);
                line = newline.reverse().toString();
                out.write(line);
                out.newLine();
                out.flush();
            }
            System.out.println("Server closed connection");
            in.close();
            out.close();
            socket.close();
            server.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
