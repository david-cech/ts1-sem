package library;

import messages.Message;

import java.util.*;

public class Customer extends User {
    String address;
    List<Message> inbox;
    List<Lease> activeLeases;

    public Customer(String firstName, String lastName, String email, String password, String dateOfBirth, String address) {
        super(firstName, lastName, email, password, dateOfBirth);
        this.address = address;
        this.inbox = new ArrayList<>();
        this.activeLeases = new ArrayList<>();
    }

    public void sendMessage(Message message) {
        inbox.add(message);
    }

    public void addLeaseToActiveLeases(Lease l) {
        activeLeases.add(l);
    }

    public void removeLeaseFromActiveLeases(Lease l) {
        activeLeases.remove(l);
    }

    public int getNumberOfActiveLeases() {
        return activeLeases.size();
    }

    public List<Message> getInbox() {
        return inbox;
    }

    public List<Lease> getActiveLeases() {
        return activeLeases;
    }

    public int getId() {
        return id;
    }

    public void setActiveLeases(List<Lease> activeLeases) {
        this.activeLeases = activeLeases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
