/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.TaiKhoanBUS;
import entity.TaiKhoan;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TienDung
 */
public class ServerThread extends Thread {

    public Socket clienSocket;
    public Server server;
    public TaiKhoan t = new TaiKhoan();
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean run;
    private TaiKhoanBUS tkBUS;

    public ServerThread(Server server, Socket client) {
        try {
            this.server = server;
            this.clienSocket = client;
            tkBUS = new TaiKhoanBUS();
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            run = true;
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerThread() {
        tkBUS = new TaiKhoanBUS();

    }

    @Override
    public void run() {
        while (run) {
            try {
                Object o = receive();
                if (o instanceof TaiKhoan) {
                    t = (TaiKhoan) o;
                    if (server.listUser.containsKey(t.getUserName())) {
                        send("not");
                    }

                    if (checkDN(t)) {
                        server.showMessage(t.getUserName() + " da ket noi");
                        server.listUser.put(t.getUserName(), this);
                        send("dangnhap");
                        while (run) {
                            String res = (String) receive();
                            switch (res) {
                                case "exit": // exit
                                    run = false;
                                    server.listUser.remove(t.getUserName());
                                    server.showMessage(t.getUserName() + " da thoat!");
                                    exit();
                                    break;
                                case "viewchat": //load view chat
                                    send(t.getUserName());
                                    break;
                                case "chat"://gửi tin nhắn
                                    break;
                            }
                        }
                    } else {
                        send("thatbai");
                    }
                } else {
                    String res = (String) o;
                    switch (res) {
                        case "exit":
                            run = false;
                            exit();
                            break;
                        case "dangki":
                            TaiKhoan tk = (TaiKhoan) receive();
                            if (checkDK(tk)) {
                                send("dangnhap");
                                server.showMessage(tk.getUserName() + " da ket noi");
                                server.listUser.put(tk.getUserName(), this);
                                while (run) {
                                    String s = (String) receive();
                                    switch (s) {
                                        case "exit": // exit
                                            run = false;
                                            server.listUser.remove(tk.getUserName());
                                            server.showMessage(tk.getUserName() + "da thoat!");
                                            exit();
                                            break;
                                        case "viewchat": //load view chat

                                            break;
                                    }
                                }
                            }
                            send("tontai");
                            break;

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void send(String s) {
        try {
            oos.writeObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object receive() throws ClassNotFoundException {
        Object o = null;
        try {
            o = ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return o;
    }

    private void exit() {
        try {
            ois.close();
            oos.close();
            //clienSocket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkDN(TaiKhoan tk) throws SQLException {
        return tkBUS.dangNhap(tk);
    }

    public boolean checkDK(TaiKhoan tk) {
        return tkBUS.dangKi(tk);
    }

//    public static void main(String[] args) {
//        ServerThread se = new ServerThread();
//        TaiKhoan tk = new TaiKhoan();
//        tk.setUserName("dung@gmail.com");
//        tk.setPassWord("123456");
//        if (se.checkDK(tk)) {
//            JOptionPane.showMessageDialog(null, "Đăng Nhập thành công");
//        } else {
//            JOptionPane.showMessageDialog(null, "Failed");
//        }
//
//    }
}
