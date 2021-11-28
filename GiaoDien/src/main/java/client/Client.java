/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.TaiKhoan;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;
import view.DangNhap1;

/**
 *
 * @author TienDung
 */
public class Client {

    private int port;
    private String host;
    public Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DangNhap1 dn;
    private boolean run;

    public Client(DangNhap1 dn) {
        this.dn = dn;
        this.ois = ois;
        host = "localhost";
        port = 5002;
        openSocket();

    }

    public void openSocket() {
        try {
            socket = new Socket(host, port);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối");
            System.exit(0);
        }
    }

//    public int checkDangNhap() {
//        String res = (String) getResult();
//        if (res.equals("not")) {
//            return 0;
//        } else if (res.equals("dangnhap")) {
//            return 1;
//        } else {
//            return 2;
//        }
//
//    }

    public boolean checkDangKy() {
        String res = (String) getResult();
        if (res.compareTo("dangnhap") == 0) {
            return true;
        }
        return false;
    }

    //gửi TaiKhoan qua server
    public void sendTaiKhoan(TaiKhoan tk) {
        try {
            oos.writeObject(tk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //gửi chuỗi qua server
    public void sendString(String str) {
        try {
            oos.writeObject(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //nhận kq từ server
    public String getResult() {
        try {
            String res = (String) ois.readObject();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //thoat khỏi Frame + đóng socket
    public void exit() {
        try {
            sendString("exit");
            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);

    }
}
