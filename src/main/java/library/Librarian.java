package library;

import accessories.MyDate;
import messages.LeaseCreated;
import messages.LeaseExpiredMessage;
import messages.LeaseWillExpire;

import java.util.List;

public class Librarian extends User {
    Library station;

    public Librarian(String firstName, String lastName, String email, String password, String dateOfBirth, Library station) {
        super(firstName, lastName, email, password, dateOfBirth);
        this.station = station;
    }

    public void messageCustomers() {
        List<Lease> leaseRecord = station.getLeaseRecord();
        for (Lease l : leaseRecord) {
            if (l.returned) {
                continue;
            }
            if (System.currentTimeMillis() <= l.end && System.currentTimeMillis() + 3 * MyDate.dayInMilis() >= l.end) {
                l.getCustomer().sendMessage(new LeaseWillExpire(l.end, this.email));
            } else if (System.currentTimeMillis() >= l.end) {
                l.getCustomer().sendMessage(new LeaseExpiredMessage(l.end, 2, this.email));
            }
        }
    }

    public boolean leaseBook(Customer c, Book b) {
        return leaseBook(c, b, System.currentTimeMillis(), System.currentTimeMillis() + 28 * MyDate.dayInMilis());
    }

    public boolean leaseBook(Customer c, Book b, long start, long end) {
        if (station.getBookCount(b) <= 0) { //no books available
            return false;
        }

        if (start < System.currentTimeMillis() - 3600000L || start > System.currentTimeMillis() - 7 * MyDate.dayInMilis()) { //invalid start of lease
            return false;
        }

        if (start >= end) { //lease ends sooner than begins
            return false;
        }

        if (start + 180 * MyDate.dayInMilis() <= end) { //lease longer than 180 days
            return false;
        }

        if (c.getNumberOfActiveLeases() > 9) { //too many active leases
            return false;
        }

        for (Lease l : c.getActiveLeases()) { //customer has expired lease
            if (l.getEnd() < System.currentTimeMillis()) {
                return false;
            }
        }

        Lease l = new Lease(c, start, end, b, this.station);
        this.station.decreaseBookCount(b);
        this.station.recordLease(l);
        c.addLeaseToActiveLeases(l);
        c.sendMessage(new LeaseCreated(l, this.email));

        return true;
    }

    public boolean extendLease(Lease l, long newEnd) {
        if (newEnd > l.getEnd() && newEnd <= l.getStart() + 180 * MyDate.dayInMilis() && l.getLeasedFrom().equals(this.station)) {
            return true;
        }
        return false;
    }

    public boolean endLease(Lease l) {
        if (!l.getLeasedFrom().equals(this.station)) {
            return false;
        }

        this.station.increaseBookCount(l.getBook());
        Customer c = l.getCustomer();
        c.removeLeaseFromActiveLeases(l);
        l.setReturned(true);
        return true;
    }

    public Library getStation() {
        return station;
    }
}
