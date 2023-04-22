package library;

import library.Author;

public class Book {
    Author author;
    String title;
    String isbn;
    String releaseDate;
    String publisher;

    public Book(Author author, String title, String isbn, String releaseDate, String publisher) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
