import library.Customer;
import library.DbManager;
import library.Login;
import library.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DbManager dbManager = DbManager.getInstance();

        List<User> users = dbManager.getUsers();
        users.add(new Customer("Jon", "Doe", "jondoe@gmail.com", "password", "15. 02. 1990", "Forest avenue 12"));

        User u = Login.login("jondoe@gmail.com", "password");
        if (u instanceof Customer) {
            System.out.println("Ok");
        }
    }
}
