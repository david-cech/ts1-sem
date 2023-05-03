package library;

import accessories.MyDate;

import java.util.Objects;

public class Lease {
    Customer customer;
    long start;
    long end;
    Book book;
    Library leasedFrom;
    boolean returned;
    int timesNotified;

    public Lease(Customer customer, long start, long end, Book book, Library leasedFrom) {
        this.customer = customer;
        this.start = start;
        this.end = end;
        this.book = book;
        this.leasedFrom = leasedFrom;
        this.returned = false;
        this.timesNotified = 0;
    }

    public Book getBook() {
        return book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getEnd() {
        return end;
    }

    public long getStart() {
        return start;
    }

    public int getTimesNotified() {
        return timesNotified;
    }

    public boolean isReturned() {
        return returned;
    }

    public Library getLeasedFrom() {
        return leasedFrom;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lease lease = (Lease) o;
        return start == lease.start && end == lease.end && returned == lease.returned && timesNotified == lease.timesNotified && customer.equals(lease.customer) && book.equals(lease.book) && leasedFrom.equals(lease.leasedFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, start, end, book, leasedFrom, returned, timesNotified);
    }

    @Override
    public String toString() {
        String returnedString = returned ? "book has been returned" : "book has not been returned";
        return "Customer " + customer.getId() + " leased " + book.toString() + " from library " +
                leasedFrom.toString() + " duration " + MyDate.getDateString(start) + " - " + MyDate.getDateString(end) + returnedString;
    }
}
