package connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListFriendDAO {
        private MyConnection connection;

        public ListFriendDAO() {
                connection = null;
        }

        public boolean insertFriend(String user, String friend){
                connection = new MyConnection();
                connection.executeUpdate("INSERT INTO `listfriend` (`user`,`friend`) " +
                        "VALUES ('"+user+"','"+friend+"')");
                connection.close();
                return true;
        }
        public boolean deleteFriend(String user, String friend){
                connection = new MyConnection();
                connection.executeUpdate("DELETE FROM `listfriend` WHERE user='" + user + "' AND friend='"+friend+"'");
                connection.close();
                return true;
        }
        public ArrayList<String> getListFriend(String user) {
                ArrayList<String> list = new ArrayList<>();
                connection = new MyConnection();
                try {
                        ResultSet resultSet = connection.executeQuery("select * from listfriend where user='" + user + "'");
                        while (resultSet.next()) {
                                list.add(resultSet.getString(3));
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                connection.close();
                return list;
        }
}
