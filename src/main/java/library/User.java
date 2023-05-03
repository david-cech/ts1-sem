package library;

import accessories.IdGenerator;

import java.util.Objects;

public abstract class User {
    int id;
    String firstName;
    String lastName;
    String dateOfBirth;
    String email;
    String password;

    public User(String firstName, String lastName, String email, String password, String dateOfBirth) {
        this.id = IdGenerator.getId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
