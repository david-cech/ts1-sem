package library;

import java.util.List;

public class Login {

    public static User login(DbManager dbManager, String email, String password) {
        List<User> users = dbManager.getUsers();

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }

    public static boolean register(DbManager dbManager, User u) {
        List<User> users = dbManager.getUsers();

        for (User user : users) {
            if (user.getEmail().equals(u.getEmail())) {
                return false;
            }
        }

        users.add(u);

        return true;
    }
}
