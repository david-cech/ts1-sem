package library;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private static DbManager dbManager = null;

    private List<User> users;
    private List<Library> libraries;

    private DbManager() {
        this.users = new ArrayList<>();
        this.libraries = new ArrayList<>();
    }

    public static DbManager getInstance() {
        if (dbManager == null) {
            dbManager = new DbManager();
        }
        return dbManager;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Library> getLibraries() {
        return libraries;
    }
}
