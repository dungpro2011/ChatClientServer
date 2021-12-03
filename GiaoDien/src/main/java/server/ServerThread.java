/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import connection.ListFriendBUS;
import connection.TaiKhoanBUS;
import entity.TaiKhoan;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
    //get list friend
    private ListFriendBUS lfBUS;
    private ArrayList<String> listFriend;

    public ServerThread(Server server, Socket client) {
        try {
            this.server = server;
            this.clienSocket = client;
            tkBUS = new TaiKhoanBUS();
            lfBUS = new ListFriendBUS();
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
                            String req = (String) receive();
                            StringTokenizer str = new StringTokenizer(req,"#");//tach cu phap tu client
                            String res = str.nextToken();
                            listFriend = lfBUS.getListFriend(t.getUserName()); // return list friend cua user
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

                                //thinh
                                case "searchuser":
                                    String data = "";
                                    while (str.hasMoreTokens()){
                                        data = str.nextToken();
                                    }
                                    TaiKhoan result = new TaiKhoan();
                                    ArrayList<TaiKhoan> userArr = tkBUS.docDanhSachTaiKhoan();
                                    for (TaiKhoan user : userArr){
                                        if (user.getUserName().equals(data)){
                                            result = user;
                                            break;
                                        }
                                    }
                                    server.showMessage("search-users");
                                    server.showMessage(result.getUserName());
                                    send(result);
                                    break;
                                case "isInContact":
                                    String userNameFriend = "";
                                    while (str.hasMoreTokens()){
                                        userNameFriend = str.nextToken();
                                    }
                                    boolean isFriendInContact = listFriend.contains(userNameFriend);
                                    server.showMessage("checkFriend");
                                    if (isFriendInContact) send("yes");
                                    else send("no");
                                    break;
                                case "addFriend":
                                    String addFriendUserName = "";
                                    while (str.hasMoreTokens()){
                                        addFriendUserName = str.nextToken();
                                    }
                                    boolean isAdded = lfBUS.insertFriend(t.getUserName(),addFriendUserName);
                                    server.showMessage("addFriend");
                                    if (isAdded) send("added");
                                    else send("notadded");
                                    break;
                                case "delFriend":
                                    String delFriendUserName = "";
                                    while (str.hasMoreTokens()){
                                        delFriendUserName = str.nextToken();
                                    }
                                    boolean isDeleted = lfBUS.deleteFriend(t.getUserName(),delFriendUserName);
                                    server.showMessage("delFriend");
                                    if (isDeleted) send("deleted");
                                    else send("notdelete");
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

    private void send(Object s) {
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
