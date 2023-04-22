package library;

import accessories.MyDate;

public class Lease {
    Customer customer;
    long start;
    long end;
    Book book;
    Library leasedFrom;
    boolean returned;

    public Lease(Customer customer, long start, long end, Book book, Library leasedFrom) {
        this.customer = customer;
        this.start = start;
        this.end = end;
        this.book = book;
        this.leasedFrom = leasedFrom;
        this.returned = false;
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
    public String toString() {
        String returnedString = returned ? "book has been returned" : "book has not been returned";
        return "Customer " + customer.getId() + " leased " + book.toString() + " from library " +
                leasedFrom.toString() + " duration " + MyDate.getDateString(start) + " - " + MyDate.getDateString(end) + returnedString;
    }
}
