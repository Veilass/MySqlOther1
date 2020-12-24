package programm;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DB db = new DB();

        try {
            db.setOrders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
