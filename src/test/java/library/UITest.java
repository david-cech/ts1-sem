package library;

import accessories.MyDate;
import messages.LeaseCreatedMessage;
import messages.LeaseWillExpire;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UITest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    protected void setUpOutputStream() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    protected void printCustomerInboxMessages_PrintsInboxMessages_MessagesStrings() {
        Customer c = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        Author author = new Author("Ctimir", "Smutny", "02.02.2002");
        Book book = new Book(author, "Ucebnice Javy", "978-3-16-148410-0", "16.07.2019", "Java Publishing");
        Library lib = new Library("NTK", "Technická 2710/6");
        long now = System.currentTimeMillis();
        long end = System.currentTimeMillis() + 14 * MyDate.dayInMilis();
        Lease lease = new Lease(c, now, end, book, lib);
        c.sendMessage(new LeaseCreatedMessage(lease, "librarian@gmail.com", now));
        c.sendMessage(new LeaseWillExpire(end, "librarian@gmail.com", now));
        UI.printCustomerInboxMessages(c);
        Assertions.assertEquals("librarian@gmail.com at " + MyDate.getDateString(now) + ": You have leased book" +
                " Ucebnice Javy by Ctimir Smutny in library NTK located at Technická 2710/6 lease through " +
                MyDate.getDateString(end) + "\n" + "librarian@gmail.com at " + MyDate.getDateString(now) + ": Your" +
                " lease ends on " + MyDate.getDateString(end) + "\n", outContent.toString());
    }

    @Test
    protected void printCustomerInboxMessages_PrintsEmptyInbox_EmptyInboxString() {
        Customer c = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        UI.printCustomerInboxMessages(c);
        Assertions.assertEquals("No messages in inbox\n", outContent.toString());
    }

    @Test
    protected void printLibraries_PrintsLibraries_LibrariesStrings() {
        DbManager dbManager = new DbManager();
        dbManager.addLibrary(new Library("NTK", "Technická 2710/6"));
        dbManager.addLibrary(new Library("Ústřední knihovna", "Mariánské nám. 98/1"));
        UI.printLibraries(dbManager);
        Assertions.assertEquals("NTK located at Technická 2710/6\nÚstřední knihovna located at Mariánské nám. 98/1\n",
                outContent.toString());
    }

    @Test
    protected void printLibraries_PrintNoLibraries_NoLibrariesString() {
        DbManager dbManager = new DbManager();
        UI.printLibraries(dbManager);
        Assertions.assertEquals("No libraries in database\n", outContent.toString());
    }

    @AfterEach
    protected void restoreOutputStream() {
        System.setOut(originalOut);
    }

}
