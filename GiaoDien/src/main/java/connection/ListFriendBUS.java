package connection;

import entity.TaiKhoan;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListFriendBUS {
    private ListFriendDAO lfDAO;
    private ArrayList<String> listFriend;

    public ListFriendBUS() {
        lfDAO = new ListFriendDAO();
    }

    //Lay danh sach ban be tu DAO
    public ArrayList<String> getListFriend(String user) {
        return lfDAO.getListFriend(user);
    }
    public boolean insertFriend(String user , String friend){
        lfDAO.insertFriend(user,friend);
        return true;
    }
    public boolean deleteFriend(String user , String friend){
        lfDAO.deleteFriend(user,friend);
        return true;
    }
}
