/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import entity.TaiKhoan;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author TienDung
 */
public class TaiKhoanBUS {

    private TaiKhoanDAO tkDAO;
    private ArrayList<TaiKhoan> dstk;

    public TaiKhoanBUS() {
        tkDAO = new TaiKhoanDAO();
        dstk = tkDAO.docDanhSachTaiKhoan();
    }

    //Lay danh sach nhan vien tu DAO
    public ArrayList<TaiKhoan> docDanhSachTaiKhoan() {
        return dstk;
    }

    public boolean dangNhap(TaiKhoan tk) throws SQLException {
       return tkDAO.dangNhap(tk);
    }

    //TK can them
    public boolean dangKi(TaiKhoan tk) {
        //Kiem tra username da co hay chua
        for (int i = 0; i < dstk.size(); i++) {
            TaiKhoan tkt = dstk.get(i);
            if (tk.getUserName().compareTo(tkt.getUserName()) == 0) {
                
                return false;
            }
        }
        //them tai khoan moi vao SQL
        tkDAO.dangki(tk);
        return true;
    }
    
    
    public static void main(String[] args) throws SQLException {
        TaiKhoanBUS tb = new TaiKhoanBUS();
        TaiKhoan tkt =new TaiKhoan();
        tkt.setPassWord("123456");
        tkt.setUserName("dung@gmail.com");
       
        
        if(tb.dangNhap(tkt)){
            JOptionPane.showMessageDialog(null, "Đăng Nhập thành công");
        }else
            JOptionPane.showMessageDialog(null, "Sai thông tin đăng nhập.");
            
    }
    

}
