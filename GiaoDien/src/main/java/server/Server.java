package server;

import entity.TaiKhoan;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;


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

    private static int port =5002;
    private ServerSocket server;
    
    public Map<String, ServerThread> listUser;

    public Server() {
        go();
    }

    public void go() {
        try {
            listUser = new TreeMap<String, ServerThread>();
            server = new ServerSocket(port);
            System.out.println("May chu bat dau phuc vu.");
            while (true) {                
                Socket client = server.accept();
                new ServerThread(this, client);
            }
        } catch (IOException e) {
            showMessage("Khong the khoi dong may chu!!!");
        }
    }
    
    public String getAllName(){
        Set<String> set = listUser.keySet();
        String userName = "";
                   
        return userName;
    }     
    
    public void showMessage(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) throws IOException {
        
        
       Server ser = new Server();
        
    }
}
