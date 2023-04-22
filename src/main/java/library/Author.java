package library;

import accessories.IdGenerator;

public class Author {
    int id;
    String firstName;
    String lastName;
    String dateOfBirth;

    public Author(String firstName, String lastName, String dateOfBirth) {
        this.id = IdGenerator.getId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

}
