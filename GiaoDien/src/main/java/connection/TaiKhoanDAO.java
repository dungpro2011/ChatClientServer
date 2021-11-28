/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import entity.TaiKhoan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author TienDung
 */
public class TaiKhoanDAO {

    private MyConnection connection;

    public TaiKhoanDAO() {
        connection = null;
    }

    public ArrayList<TaiKhoan> docDanhSachTaiKhoan() {
        connection = new MyConnection();
        ArrayList<TaiKhoan> dstk = new ArrayList<>();
        TaiKhoan tk;
        try {
            ResultSet resultSet = connection.executeQuery("select * from user");
            while (resultSet.next()) {
                tk = new TaiKhoan();
                tk.setUserName(resultSet.getString(1));
                tk.setPassWord(resultSet.getString(2));
                tk.setHoTen(resultSet.getString(3));
                tk.setGioiTinh(resultSet.getString(4));
                tk.setNgaySinh(resultSet.getString(5));
                dstk.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return dstk;
    }
    //Đăng nhập 
    public boolean dangNhap(TaiKhoan tk) throws SQLException {
        connection = new MyConnection();
        ResultSet rs = connection.executeQuery("SELECT username,password FROM user WHERE username="
                + "'" + tk.getUserName() + "' AND password = '" + tk.getPassWord() + "'");
        
        if (rs.next()) {
            connection.close();
            return true;
        } else {
            connection.close();
            return false;
        }
        
    }
    //Đăng kí TaiKhoan
    public boolean dangki(TaiKhoan tk) {
        connection = new MyConnection();
        connection.executeUpdate("insert into user(username,password,hoten,gioitinh,ngaysinh )\n"
                + "value ('" + tk.getUserName() + "','" + tk.getPassWord() + "','" + tk.getHoTen() + "',"
                + "'" + tk.getGioiTinh() + "','" + tk.getNgaySinh() + "')");
        connection.close();
        return true;
    }
    //Update TaiKhoan
    public boolean updateUser(TaiKhoan tk) {
        connection = new MyConnection();
        connection.executeUpdate("update user set username = '" + tk.getUserName() + "', password = '" + tk.getPassWord() + "', hoten = '" + tk.getHoTen() + "',"
                + " gioitinh = '" + tk.getGioiTinh() + "', ngaysinh = '" + tk.getNgaySinh() + "'");
        connection.close();
        return true;
    }


}
