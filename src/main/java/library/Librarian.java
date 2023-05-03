package library;

import accessories.MyDate;
import messages.LeaseCreatedMessage;
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
            if (l.isReturned() || l.getTimesNotified() >= 2) {
                continue;
            }
            if (l.getTimesNotified() == 0 && System.currentTimeMillis() <= l.getEnd() && System.currentTimeMillis() + 7 * MyDate.dayInMilis() >= l.getEnd()) {
                l.getCustomer().sendMessage(new LeaseWillExpire(l.getEnd(), this.email));
            } else if (l.getTimesNotified() == 1 && System.currentTimeMillis() >= l.getEnd()) {
                l.getCustomer().sendMessage(new LeaseExpiredMessage(l.getEnd(), 2, this.email));
            }
        }
    }

    public Lease leaseBook(Customer c, Book b) {
        return leaseBook(c, b, System.currentTimeMillis(), System.currentTimeMillis() + 28 * MyDate.dayInMilis());
    }

    public Lease leaseBook(Customer c, Book b, long start, long end) {
        if (station.getBookCount(b) <= 0) { //no books available
            return null;
        }

        if (start < System.currentTimeMillis() - 60000L || start > System.currentTimeMillis() + 7 * MyDate.dayInMilis()) { //invalid start of lease
            return null;
        }

        if (end - start < 7 * MyDate.dayInMilis()) { //lease is too short
            return null;
        }

        if (start + 180 * MyDate.dayInMilis() <= end) { //lease longer than 180 days
            return null;
        }

        if (c.getNumberOfActiveLeases() > 9) { //too many active leases
            return null;
        }

        for (Lease l : c.getActiveLeases()) { //customer has expired lease
            if (l.getEnd() < System.currentTimeMillis()) {
                return null;
            }
        }

        Lease l = new Lease(c, start, end, b, this.station);
        this.station.decreaseBookCount(b);
        this.station.recordLease(l);
        c.addLeaseToActiveLeases(l);
        c.sendMessage(new LeaseCreatedMessage(l, this.email));

        return l;
    }

    public void buyBook(Book b) {
        station.increaseBookCount(b);
    }

    public boolean extendLease(Lease l, long newEnd) {
        if (newEnd > l.getEnd() && newEnd <= l.getStart() + 180 * MyDate.dayInMilis() && l.getLeasedFrom().equals(this.station)) {
            l.setEnd(newEnd);
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
