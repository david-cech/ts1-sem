package library;

import messages.Message;

import java.util.List;
import java.util.Map;

public class UI {

    public static void printCustomerInboxMessages(Customer c) {
        for (Message m : c.getInbox()) {
            System.out.println(m.getContent());
        }
    }

    public static void printCustomerActiveLeases(Customer c) {
        for (Lease l : c.getActiveLeases()) {
            System.out.println(l.getBook().toString() +
                    " leased through " + l.getStart() + " - " + l.getEnd());
        }
    }

    public static void printLibraries() {
        List<Library> libraries = DbManager.getInstance().getLibraries();
        for (Library l : libraries) {
            System.out.println(l.toString());
        }
    }

    public static void printAvailableBooks(Library l) {
        Map<Book, Integer> bookArchive = l.getArchive();
        for (Map.Entry<Book, Integer> entry : bookArchive.entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println(entry.getKey().toString() + " " + entry.getValue() + " copies available");
            }
        }
    }

    public static void printAllLeasedBooksByLibrary(Librarian l) {
        List<Lease> leaseRecord = l.getStation().getLeaseRecord();

        for (Lease lease : leaseRecord) {
            System.out.println(lease.toString());
        }
    }
}
