package library;

import accessories.MyDate;
import messages.LeaseCreatedMessage;
import messages.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mockConstruction;

public class LibrarianTest {

    Librarian librarian;
    static Author author = new Author("Ctimir", "Smutny", "02.02.2002");
    static Book book = new Book(author, "Ucebnice Javy", "978-3-16-148410-0", "16.07.2019", "Java Publishing");

    @BeforeEach
    protected void setUpLibrarianAndLibrary() {
        Library l = new Library("NTK", "Technická 2710/6");
        Librarian librarian = new Librarian("Jan", "Novak", "jan.novak@gmail.com", "password",
                "01.01.1990", l);
        librarian.buyBook(book);
        this.librarian = librarian;
    }

    @Test
    protected void leaseBook_LeaseBookAddsLeaseToCustomerActiveLeases_addLeaseToActiveLeasesCalled() {
        Customer mockCustomer = Mockito.mock(Customer.class);
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() + 14 * MyDate.dayInMilis();
        Lease expected = new Lease(mockCustomer, start, end, book, librarian.getStation());
        librarian.leaseBook(mockCustomer, book, start, end);
        Mockito.verify(mockCustomer).addLeaseToActiveLeases(expected);
    }

    @Test
    protected void leaseBook_LeaseBookCreatesLeaseCreatedMessage_createsLeaseCreatedMessage() {
        Customer c = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        try (MockedConstruction<LeaseCreatedMessage> mocked = mockConstruction(LeaseCreatedMessage.class)) {
            librarian.leaseBook(c, book);

            Assertions.assertEquals(1, mocked.constructed().size());
        }
    }

    @Test
    protected void leaseBook_LeaseBookSendsLeaseCreatedMessage_sendMessageCalled() {
        Customer mockCustomer = Mockito.mock(Customer.class);
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis() + 14 * MyDate.dayInMilis();
        librarian.leaseBook(mockCustomer, book, start, end);
        Mockito.verify(mockCustomer).sendMessage(ArgumentMatchers.any(LeaseCreatedMessage.class));
    }

    @Test
    protected void leaseBook_ValidInput_Lease() {
        Customer belowFreshCustomer = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        List<Lease> belowFreshLeases = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Lease temp = new Lease(belowFreshCustomer, System.currentTimeMillis() - (21 + i) * MyDate.dayInMilis(),
                    System.currentTimeMillis() + 7 * MyDate.dayInMilis(), book, librarian.getStation());
            temp.setReturned(true);
            belowFreshLeases.add(temp);
        }
        belowFreshCustomer.setActiveLeases(belowFreshLeases);

        Assertions.assertNotNull(librarian.leaseBook(belowFreshCustomer, book, System.currentTimeMillis(),
                System.currentTimeMillis() + 7 * MyDate.dayInMilis()));
    }

    @ParameterizedTest
    @MethodSource("invalidArgumentsProvider")
    protected void leaseBook_InvalidInput_null(Customer c, Book b, long start, long end) {
        Assertions.assertNull(librarian.leaseBook(c, b, start, end));
    }


    public static Stream<Arguments> invalidArgumentsProvider() {
        Library oneBookLib = new Library("NTK", "Technická 2710/6");
        Book availableBook = book;
        Book unavailableBook = new Book(author, "Ucebnice Pyhonu", "978-3-16-148230-0", "14.11.2021", "Python Publishing");

        Customer belowFreshCustomer = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        List<Lease> belowFreshLeases = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            belowFreshLeases.add(new Lease(belowFreshCustomer, System.currentTimeMillis() - (21 + i) * MyDate.dayInMilis(),
                    System.currentTimeMillis() + 1000, availableBook, oneBookLib));
        }
        belowFreshCustomer.setActiveLeases(belowFreshLeases);

        Customer belowExpiredCustomer = new Customer("Pepa", "Lenivy", "pepalen@gmail.com",
                "password", "12.03.1999", "Arabská 33");
        List<Lease> belowExpiredleases = new ArrayList<>();
        belowExpiredleases.add(new Lease(belowExpiredCustomer, System.currentTimeMillis() - 21 * MyDate.dayInMilis(),
                System.currentTimeMillis() - 1000, availableBook, oneBookLib));
        for (int i = 1; i < 9; i++) {
            belowExpiredleases.add(new Lease(belowExpiredCustomer, System.currentTimeMillis() - (21 + i) * MyDate.dayInMilis(),
                    System.currentTimeMillis() + 1000, availableBook, oneBookLib));
        }
        belowExpiredCustomer.setActiveLeases(belowExpiredleases);

        Customer aboveExpiredCustomer = new Customer("Pepa", "Lenochod", "pepalenoch@gmail.com",
                "password", "12.05.1969", "Ománská 63");
        List<Lease> aboveExpiredleases = new ArrayList<>();
        aboveExpiredleases.add(new Lease(aboveExpiredCustomer, System.currentTimeMillis() - 21 * MyDate.dayInMilis(),
                System.currentTimeMillis() - 1000, availableBook, oneBookLib));
        for (int i = 1; i < 10; i++) {
            aboveExpiredleases.add(new Lease(aboveExpiredCustomer, System.currentTimeMillis() - (21 + i) * MyDate.dayInMilis(),
                    System.currentTimeMillis() + 1000, availableBook, oneBookLib));
        }
        aboveExpiredCustomer.setActiveLeases(aboveExpiredleases);


        Customer aboveFreshCustomer = new Customer("Pepa", "Ctivy", "pepacti@gmail.com",
                "password", "19.07.1978", "Jelimánova 55");
        List<Lease> aboveFreshLeases = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            aboveFreshLeases.add(new Lease(aboveFreshCustomer, System.currentTimeMillis() - (21 + i) * MyDate.dayInMilis(),
                    System.currentTimeMillis() + 1000, availableBook, oneBookLib));
        }
        aboveFreshCustomer.setActiveLeases(aboveFreshLeases);

        long yesterday = System.currentTimeMillis() - 1;
        long now = System.currentTimeMillis();
        long inAWeek = System.currentTimeMillis() + 7 * MyDate.dayInMilis();
        long lessThanAWeek = inAWeek - 1;
        long laterThanAWeek = inAWeek + 1;
        long inHalfAYear = System.currentTimeMillis() + 180 * MyDate.dayInMilis();
        long laterThanHalfAYear = inHalfAYear + 1;

        return Stream.of(
                Arguments.of(aboveExpiredCustomer, unavailableBook, now, lessThanAWeek), //f
                Arguments.of(belowFreshCustomer, unavailableBook, now, inHalfAYear), //f
                Arguments.of(aboveExpiredCustomer, availableBook, now, laterThanHalfAYear), //f

                Arguments.of(aboveFreshCustomer, unavailableBook, inAWeek, inAWeek), //f
                Arguments.of(belowExpiredCustomer, availableBook, inAWeek, lessThanAWeek), //f
                Arguments.of(aboveExpiredCustomer, availableBook, inAWeek, inHalfAYear), //f
                Arguments.of(belowFreshCustomer, unavailableBook, inAWeek, laterThanHalfAYear), //f

                Arguments.of(aboveExpiredCustomer, availableBook, laterThanAWeek, inAWeek), //f
                Arguments.of(belowFreshCustomer, unavailableBook, laterThanAWeek, lessThanAWeek), //f
                Arguments.of(belowExpiredCustomer, availableBook, laterThanAWeek, inHalfAYear), //f
                Arguments.of(belowExpiredCustomer, availableBook, laterThanAWeek, laterThanHalfAYear), //f

                Arguments.of(aboveExpiredCustomer, availableBook, yesterday, inAWeek), //f
                Arguments.of(belowFreshCustomer, unavailableBook, yesterday, lessThanAWeek), //f
                Arguments.of(belowFreshCustomer, unavailableBook, yesterday, inHalfAYear), //f
                Arguments.of(belowExpiredCustomer, unavailableBook, yesterday, laterThanHalfAYear) //f
        );
    }

    @Test
    protected void noLeaseFailureProcessTest() {
        DbManager dbManager = new DbManager();
        Customer c = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        dbManager.getUsers().add(c);
        User u = Login.login(dbManager, "pepapor@gmail.com", "password");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(c.getId(), u.getId());

        Customer logged = (Customer) u;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        UI.printCustomerActiveLeases(logged);
        Assertions.assertEquals("No active leases\n", outContent.toString());
        outContent.reset();

        Assertions.assertNotNull(librarian.leaseBook(logged, book));
        Assertions.assertEquals(1, logged.getInbox().size());

        Message received = logged.getInbox().get(0);

        UI.printCustomerInboxMessages(logged);

        Assertions.assertEquals(received.getSender() + " at " + MyDate.getDateString(received.getSentTimestamp())
                + ": " + received.getContent() + "\n", outContent.toString());

        System.setOut(originalOut);
    }

    @Test
    protected void multipleLeaseFailuresProcessTest() {
        Book otherBook = new Book(author, "Ucebnice Pyhonu", "978-3-16-148230-0",
                "14.11.2021", "Python Publishing");
        DbManager dbManager = new DbManager();
        Customer c = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        dbManager.getUsers().add(c);
        User u = Login.login(dbManager, "pepapor@gmail.com", "password");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(c.getId(), u.getId());

        Customer logged = (Customer) u;

        List<Lease> leases = new ArrayList<>();
        Lease bookLease = new Lease(logged, System.currentTimeMillis() - 21 * MyDate.dayInMilis(),
                System.currentTimeMillis() + 7 * MyDate.dayInMilis(), book, librarian.getStation());
        Lease otherBookLease = new Lease(logged, System.currentTimeMillis() - 21 * MyDate.dayInMilis(),
                System.currentTimeMillis() + 7 * MyDate.dayInMilis(), otherBook, librarian.getStation());
        leases.add(bookLease);
        leases.add(otherBookLease);
        logged.setActiveLeases(leases);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        UI.printCustomerActiveLeases(logged);

        Assertions.assertEquals(book.toString() + " leased through " + MyDate.getDateString(bookLease.getStart()) +
                        " - " + MyDate.getDateString(bookLease.getEnd()) + "\n" + otherBook.toString() + " leased through " +
                        MyDate.getDateString(otherBookLease.getStart()) + " - " + MyDate.getDateString(otherBookLease.getEnd()) + "\n",
                outContent.toString());
        outContent.reset();

        Assertions.assertTrue(librarian.endLease(bookLease));
        Assertions.assertEquals(2, librarian.getStation().getBookCount(book));
        Assertions.assertTrue(bookLease.isReturned());
        Assertions.assertEquals(1, logged.getNumberOfActiveLeases());


        Assertions.assertNull(librarian.leaseBook(logged, otherBook));

        UI.printCustomerActiveLeases(logged);
        Assertions.assertEquals(otherBook.toString() + " leased through " +
                        MyDate.getDateString(otherBookLease.getStart()) + " - " +
                        MyDate.getDateString(otherBookLease.getEnd()) + "\n",
                outContent.toString());
        outContent.reset();

        Assertions.assertNull(librarian.leaseBook(logged, otherBook));

        UI.printAvailableBooks(librarian.getStation());
        Assertions.assertEquals(book.toString() + " 2 copies available\n", outContent.toString());
        outContent.reset();

        Assertions.assertNull(librarian.leaseBook(logged, otherBook));

        UI.printCustomerActiveLeases(logged);
        Assertions.assertEquals(otherBook.toString() + " leased through " +
                        MyDate.getDateString(otherBookLease.getStart()) + " - " +
                        MyDate.getDateString(otherBookLease.getEnd()) + "\n",
                outContent.toString());
        outContent.reset();

        Assertions.assertTrue(librarian.endLease(otherBookLease));
        Assertions.assertEquals(1, librarian.getStation().getBookCount(otherBook));
        Assertions.assertTrue(otherBookLease.isReturned());
        Assertions.assertEquals(0, logged.getNumberOfActiveLeases());

        Assertions.assertNotNull(librarian.leaseBook(logged, otherBook));

        System.setOut(originalOut);
    }

    @Test
    protected void oneLeaseFailureProcessTest() {
        DbManager dbManager = new DbManager();
        Customer c = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        Login.register(dbManager, c);
        Assertions.assertEquals(1, dbManager.getUsers().size());
        Assertions.assertEquals(c.getId(), dbManager.getUsers().get(0).getId());
        User u = Login.login(dbManager, "pepapor@gmail.com", "password");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(c.getId(), u.getId());

        Customer logged = (Customer) u;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        UI.printAvailableBooks(librarian.getStation());
        Assertions.assertEquals(book.toString() + " 1 copies available\n", outContent.toString());
        outContent.reset();

        Book unavailableBook = new Book(author, "Ucebnice Pyhonu", "978-3-16-148230-0",
                "14.11.2021", "Python Publishing");

        Assertions.assertNull(librarian.leaseBook(logged, unavailableBook));

        UI.printAvailableBooks(librarian.getStation());
        Assertions.assertEquals(book.toString() + " 1 copies available\n", outContent.toString());
        outContent.reset();

        Assertions.assertNotNull(librarian.leaseBook(logged, book));
        Assertions.assertEquals(1, logged.getInbox().size());

        Message received = logged.getInbox().get(0);

        UI.printCustomerInboxMessages(logged);

        Assertions.assertEquals(received.getSender() + " at " + MyDate.getDateString(received.getSentTimestamp())
                + ": " + received.getContent() + "\n", outContent.toString());

        System.setOut(originalOut);
    }

    @Test
    protected void extendLeaseProcessTest() {
        DbManager dbManager = new DbManager();
        Customer c = new Customer("Pepa", "Poradny", "pepapor@gmail.com",
                "password", "09.09.1981", "Smíchovská 48");
        List<Lease> leases = new ArrayList<>();
        Lease bookLease = new Lease(c, System.currentTimeMillis() - 21 * MyDate.dayInMilis(),
                System.currentTimeMillis() + 7 * MyDate.dayInMilis(), book, librarian.getStation());
        leases.add(bookLease);
        c.setActiveLeases(leases);
        dbManager.getUsers().add(c);

        User u = Login.login(dbManager, "pepapor@gmail.com", "password");
        Assertions.assertNotNull(u);
        Assertions.assertEquals(c.getId(), u.getId());

        Customer logged = (Customer) u;

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        UI.printCustomerActiveLeases(logged);
        Assertions.assertEquals(book.toString() + " leased through " +
                MyDate.getDateString(bookLease.getStart()) + " - " +
                MyDate.getDateString(bookLease.getEnd()) + "\n", outContent.toString());
        outContent.reset();

        long newEnd = System.currentTimeMillis() + 21 * MyDate.dayInMilis();
        Assertions.assertTrue(librarian.extendLease(bookLease, newEnd));

        UI.printCustomerActiveLeases(logged);
        Assertions.assertEquals(book.toString() + " leased through " +
                MyDate.getDateString(bookLease.getStart()) + " - " +
                MyDate.getDateString(newEnd) + "\n", outContent.toString());
        outContent.reset();

        System.setOut(originalOut);
    }
}
