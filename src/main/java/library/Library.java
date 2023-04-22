package library;

import java.util.*;

public class Library {
    String name;
    String address;
    Map<Book, Integer> archive;
    List<Lease> leaseRecord;
    List<Librarian> librarians;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.archive = new HashMap<>();
        this.leaseRecord = new ArrayList<>();
        this.librarians = new ArrayList<>();
    }

    public Library(String name, String address, Map<Book, Integer> archive, List<Lease> leaseRecord, List<Librarian> librarians) {
        this.name = name;
        this.address = address;
        this.archive = archive;
        this.leaseRecord = leaseRecord;
        this.librarians = librarians;
    }

    public int getBookCount(Book b) {
        if (!archive.containsKey(b)) {
            return 0;
        }

        return archive.get(b);
    }

    public void increaseBookCount(Book b) {
        if (archive.containsKey(b)) {
            int count = archive.get(b);
            archive.put(b, count + 1);
        } else {
            archive.put(b, 1);
        }
    }

    public void decreaseBookCount(Book b) {
        if (archive.containsKey(b) && archive.get(b) > 0) {
            int count = archive.get(b);
            archive.put(b, count - 1);
        }
    }

    public void recordLease(Lease l) {
        leaseRecord.add(l);
    }

    public List<Lease> getLeaseRecord() {
        return leaseRecord;
    }

    public Map<Book, Integer> getArchive() {
        return archive;
    }

    public List<Librarian> getLibrarians() {
        return librarians;
    }

    public boolean leaseBook(Customer c, Book b) {
        Random rand = new Random();
        Librarian librarian = librarians.get(rand.nextInt(librarians.size()));
        return librarian.leaseBook(c, b);
    }

    public boolean extendLease(Lease lease, long newEnd) {
        Random rand = new Random();
        Librarian librarian = librarians.get(rand.nextInt(librarians.size()));
        return librarian.extendLease(lease, newEnd);
    }

    public boolean endLease(Lease lease) {
        Random rand = new Random();
        Librarian librarian = librarians.get(rand.nextInt(librarians.size()));
        return librarian.endLease(lease);
    }

    @Override
    public String toString() {
        return "Library " + name + " located at " + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Library.class != o.getClass()) return false;
        Library library = (Library) o;
        return address.equals(library.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
