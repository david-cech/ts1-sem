package library;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private List<User> users;
    private List<Library> libraries;

    public DbManager() {
        this.users = new ArrayList<>();
        this.libraries = new ArrayList<>();
    }

    public void addLibrary(Library l){
        this.libraries.add(l);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Library> getLibraries() {
        return libraries;
    }
}
