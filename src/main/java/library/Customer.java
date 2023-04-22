package library;

import messages.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Customer extends User {
    String address;
    List<Message> inbox;
    Set<Lease> activeLeases;

    public Customer(String firstName, String lastName, String email, String password, String dateOfBirth, String address) {
        super(firstName, lastName, email, password, dateOfBirth);
        this.address = address;
        this.inbox = new ArrayList<>();
        this.activeLeases = new HashSet<>();
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

    public Set<Lease> getActiveLeases() {
        return activeLeases;
    }

    public int getId() {
        return id;
    }
}
