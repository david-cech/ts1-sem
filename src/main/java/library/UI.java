package library;

import accessories.MyDate;
import messages.Message;

import java.util.List;
import java.util.Map;

public class UI {

    public static void printCustomerInboxMessages(Customer c) {
        if (c.getInbox().size() == 0) {
            System.out.println("No messages in inbox");
        } else {
            for (Message m : c.getInbox()) {
                System.out.println(m.getSender() + " at " + MyDate.getDateString(m.getSentTimestamp()) + ": " + m.getContent());
            }
        }
    }

    public static void printCustomerActiveLeases(Customer c) {
        if (c.getActiveLeases().size() == 0) {
            System.out.println("No active leases");
        } else {
            for (Lease l : c.getActiveLeases()) {
                System.out.println(l.getBook().toString() +
                        " leased through " + MyDate.getDateString(l.getStart()) + " - " + MyDate.getDateString(l.getEnd()));
            }
        }
    }

    public static void printLibraries(DbManager manager) {
        List<Library> libraries = manager.getLibraries();
        if (libraries.size() == 0) {
            System.out.println("No libraries in database");
        } else {
            for (Library l : libraries) {
                System.out.println(l.toString());
            }
        }
    }

    public static void printAvailableBooks(Library l) {
        Map<Book, Integer> bookArchive = l.getArchive();
        boolean anyAvailable = false;
        for (Map.Entry<Book, Integer> entry : bookArchive.entrySet()) {
            if (entry.getValue() > 0) {
                anyAvailable = true;
                System.out.println(entry.getKey().toString() + " " + entry.getValue() + " copies available");
            }
        }

        if (!anyAvailable) {
            System.out.println("No available books at library " + l.getName());
        }
    }

    public static void printAllLeasedBooksByLibrary(Librarian l) {
        List<Lease> leaseRecord = l.getStation().getLeaseRecord();

        if (leaseRecord.size() == 0) {
            System.out.println("No recorded leases at " + l.getStation().getName());
        } else {
            for (Lease lease : leaseRecord) {
                System.out.println(lease.toString());
            }
        }
    }
}
