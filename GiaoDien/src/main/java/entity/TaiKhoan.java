/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author TienDung
 */
public class TaiKhoan implements Serializable{
    private String userName;
    private String passWord;
    private String hoTen;
    private String gioiTinh;
    private String ngaySinh;

    public TaiKhoan() {
       this.userName = "";
        this.passWord = "";
        this.hoTen = "";
        this.gioiTinh = "";
        this.ngaySinh = "";
    }

    public TaiKhoan(String userName, String passWord, String hoTen, String gioiTinh, String ngaySinh) {
        this.userName = userName;
        this.passWord = passWord;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public boolean setNgaySinhKT(String ngaySinh) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            formatter.parse(ngaySinh);
            this.ngaySinh = ngaySinh;
            return true;
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Nhập ngày sinh YYYY-MM-DD");
            return false;
        }
    }
    
    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
