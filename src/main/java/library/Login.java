package library;

import java.util.List;

public class Login {

    public static User login(String email, String password) {
        DbManager dbManager = DbManager.getInstance();
        List<User> users = dbManager.getUsers();

        for (User u : users) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }

    public static boolean register(User u) {
        DbManager dbManager = DbManager.getInstance();
        List<User> users = dbManager.getUsers();

        for (User user : users) {
            if (user.getEmail().equals(u.getEmail())) {
                return false;
            }
        }

        users.add(u);

        return true;
    }

    public static boolean registerCustomer(String firstName, String lastName, String email, String password, String dateOfBirth, String address) {
        return Login.register(new Customer(firstName, lastName, email, password, dateOfBirth, address));
    }

    public static boolean registerLibrarian(String firstName, String lastName, String email, String password, String dateOfBirth, Library station) {
        return Login.register(new Librarian(firstName, lastName, email, password, dateOfBirth, station));
    }
}
