package programm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    public final String HOST = "localhost";
    public final String PORT = "3306";
    public final String DB_NAME = "java_db";
    public final String LOGIN = "mysql";
    public final String PASS = "mysql";

    private Connection dbConn = null;

    private Connection getDbCon() throws SQLException, ClassNotFoundException {
        String connStr ="jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public void isConn() throws SQLException,ClassNotFoundException {
        dbConn = getDbCon();
        System.out.println(dbConn.isValid(1000));
    }

    public void setOrders() throws SQLException, ClassNotFoundException {
    String sql = "SELECT `id` FROM `users` WHERE `login` = ? LIMIT 1";

        PreparedStatement prUsers = getDbCon().prepareStatement(sql);
        prUsers.setString(1,"john");

        ResultSet resUser = prUsers.executeQuery();

        int user_id = 0;

        while (resUser.next())
            user_id = resUser.getInt("id");

        sql = "SELECT `id` FROM `items` WHERE `title` = ?";

        PreparedStatement prItems = getDbCon().prepareStatement(sql);
        prItems.setString(1,"hats");

        ResultSet resItem = prItems.executeQuery();

        List<Integer> arr_items_id = new ArrayList<>();

        while (resItem.next()) {
            arr_items_id.add(resItem.getInt("id"));

            for (int item_id : arr_items_id) {
                sql = "INSERT INTO `orders` (user_id, item_id) VALUES (?, ?)";

                PreparedStatement prOrders = getDbCon().prepareStatement(sql);
                prOrders.setInt(1, user_id);
                prOrders.setInt(2, item_id);

                prOrders.executeUpdate();
            }
            sql = "SELECT * FROM `orders`";
            Statement statement = getDbCon().createStatement();
            ResultSet result = statement.executeQuery(sql);

            System.out.println("Все закакзы\n");

            while (result.next()) {
                sql = "SELECT `login` FROM `users` WHERE `id` = ?";
                PreparedStatement prUsersOrder = getDbCon().prepareStatement(sql);
                prUsersOrder.setInt(1, result.getInt("user_id"));

                ResultSet resUserOrder = prUsersOrder.executeQuery();
                String useLogin = "";
                while (resUserOrder.next())
                    useLogin = resUserOrder.getString("login");

                sql = "SELECT `title` FROM `items` WHERE `id` = ?";
                PreparedStatement prTovarOrder = getDbCon().prepareStatement(sql);
                prTovarOrder.setInt(1,result.getInt("item_id"));

                ResultSet resTovarOrder =prTovarOrder.executeQuery();

                String tovarname = "";

                while (resTovarOrder.next())
                    tovarname =resTovarOrder.getString("title");

                System.out.println(useLogin + " - " + tovarname);

            }
        }
    }
}
