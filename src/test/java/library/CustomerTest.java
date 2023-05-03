package library;

import accessories.MyDate;
import messages.LeaseCreatedMessage;
import messages.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CustomerTest {

    Customer c;
    Lease activeLease;

    @BeforeEach
    public void setUpCustomer() {
        c = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        Author author = new Author("Ctimir", "Smutny", "02.02.2002");
        Book book = new Book(author, "Ucebnice Javy", "978-3-16-148410-0", "16.07.2019", "Java Publishing");
        Library lib = new Library("NTK", "Technická 2710/6");
        activeLease = new Lease(c, System.currentTimeMillis(), System.currentTimeMillis() + 14 * MyDate.dayInMilis(), book, lib);
    }

    @Test
    public void addLeaseToActiveLeases_ActiveLeasesContainsAddedLease_true() {
        c.addLeaseToActiveLeases(activeLease);
        Assertions.assertTrue(c.getActiveLeases().contains(activeLease));
    }

    @Test
    public void removeLeaseFromActiveLeases_ActiveLeasesDoesNotContainRemovedLease_false() {
        c.addLeaseToActiveLeases(activeLease);
        c.removeLeaseFromActiveLeases(activeLease);
        Assertions.assertFalse(c.getActiveLeases().contains(activeLease));
    }

    @Test
    public void removeLeaseFromActiveLeases_RemoveLeaseNotInActiveLeases_false() {
        Assertions.assertDoesNotThrow(() -> {
            c.removeLeaseFromActiveLeases(activeLease);
        });
    }

    @Test
    public void sendMessage_SentMessageAddedToInbox_true() {
        LeaseCreatedMessage message = new LeaseCreatedMessage(activeLease, "sender@gmail.com");
        c.sendMessage(message);
        List<Message> inbox = c.getInbox();
        Assertions.assertEquals(message, inbox.get(inbox.size() - 1));
    }
}
