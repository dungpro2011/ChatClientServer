package view;

import client.Client;
import entity.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ThemBan extends javax.swing.JDialog {
    public int WIDTH;
    private String clientName;
    private JTextField searchInput;
    private Client cl;
    private JPanel userProfile = new JPanel();
    private JLabel lbUserName, lbHoTen, lbGioiTinh, lbNgaySinh;
    private JButton btnAdd = new JButton("Add");
    private JButton btnDelFr = new JButton("Delete");
    private boolean isAdded;

    public ThemBan(Client cl,String clientName) throws HeadlessException {
        WIDTH = 500;
        this.cl = cl;
        isAdded = false;
        this.clientName = clientName;
        initComponents();
    }
    public void setIsAdded(boolean isAdded){
        this.isAdded = isAdded;
    }
    private void initComponents() {
        setTitle("ADD FRIEND");
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(null);
        setBounds(new Rectangle(600, 300,WIDTH, 400));
        JPanel userView = new JPanel(null);
        userView.setBounds(new Rectangle(0, 0,WIDTH, 400));
        JPanel nav = new JPanel(null);
        nav.setBackground(new Color(204, 204, 255));
        nav.setBounds(new Rectangle(0,0,WIDTH,50));
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(new Rectangle(360,10,100,30));
        searchInput = new JTextField("Type in user email...");
        searchInput.setBounds(new Rectangle(50,10,200,30));
        nav.add(btnSearch);
        nav.add(searchInput);
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        //clear placeholder
        searchInput.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                searchInput.setText("");
            }
        });
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnDelFr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });
        userProfile.setBackground(new Color(201, 199, 193));
        userView.add(nav);
        userView.add(userProfile);
        add(userView);
    }
    //delete friend
    private void btnDelActionPerformed(java.awt.event.ActionEvent evt){
        try {
            String friend = lbUserName.getText().substring(10); //get username cua friend
            cl.sendString("delFriend#" + friend);
            String res = cl.getResult();
            if (res.equals("deleted")) {
                JOptionPane.showMessageDialog(rootPane, "Đã xóa bạn");
                btnDelFr.setEnabled(false);
                btnAdd.setText("Add");
                btnAdd.setEnabled(true);
            }
            else {
                JOptionPane.showMessageDialog(rootPane,"Xóa không thành công");
                btnDelFr.setEnabled(true);
                btnAdd.setText("Added");
                btnAdd.setEnabled(false);
            }
            validate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //add friend
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt){
        try {
            String friend = lbUserName.getText().substring(10); //get username cua friend
            cl.sendString("addFriend#" + friend);
            String res = cl.getResult();
            if (res.equals("added")) {
                JOptionPane.showMessageDialog(rootPane, "Đã thêm bạn");
                btnAdd.setText("Added");
                btnDelFr.setEnabled(true);
                btnAdd.setEnabled(false);
            }
            else {
                JOptionPane.showMessageDialog(rootPane,"Thêm không thành công");
                btnAdd.setEnabled(true);
                btnDelFr.setEnabled(false);
            }
            validate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        try {
            userProfile.removeAll();
            String input = searchInput.getText();
            cl.sendString("searchuser#" + input);

            TaiKhoan userFound = (TaiKhoan) cl.getUser();
            cl.sendString("isInContact#" + input);
            String res = cl.getResult();
            if(res.equals("yes")) setIsAdded(true);
            else setIsAdded(false);
            //System.out.println(userFound.toString());
            userProfile.setLayout(null);
            userProfile.setBounds(new Rectangle(55, 80, 390, 200));
            if(userFound.getUserName() != null && !userFound.getUserName().isBlank())
            {

                lbNgaySinh = new JLabel("DoB: " + userFound.getNgaySinh());
                lbHoTen = new JLabel("FullName: "+userFound.getHoTen());
                lbGioiTinh = new JLabel("Sex: " + userFound.getGioiTinh());
                lbUserName = new JLabel("Username: " + userFound.getUserName());

                lbUserName.setBounds(20, 20, 180, 30);
                lbHoTen.setBounds(20, 60, 180, 30);
                lbGioiTinh.setBounds(20, 100, 180, 30);
                lbNgaySinh.setBounds(20, 140, 180, 30);
                btnAdd.setBounds(300,80,80,30);
                btnDelFr.setBounds(210,80,80,30);

                if (isAdded) {
                    btnAdd.setEnabled(false); //disabled button add
                    btnDelFr.setEnabled(true);
                    btnAdd.setText("Added");
                }
                else {
                    btnAdd.setText("Add");
                    btnDelFr.setEnabled(false);
                    btnAdd.setEnabled(true);
                }

                if (!userFound.getUserName().equals(clientName)) {
                    userProfile.add(btnDelFr);
                    userProfile.add(btnAdd);
                }
                userProfile.add(lbUserName);
                userProfile.add(lbHoTen);
                userProfile.add(lbNgaySinh);
                userProfile.add(lbGioiTinh);
            }
            else {
                JLabel error = new JLabel("User not found");
                error.setBounds(20, 85, 200, 30);
                error.setForeground(Color.red);
                userProfile.add(error);
            }
            //refresh
            validate();
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
