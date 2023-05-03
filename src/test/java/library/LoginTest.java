package library;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    DbManager dbManager;
    Customer registeredCustomer;

    @BeforeEach
    protected void setUpDatabase() {
        dbManager = new DbManager();
        registeredCustomer = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        Login.register(dbManager, registeredCustomer);
    }

    @Test
    protected void register_NewCustomerIsAddedToDatabase_true() {
        Customer newCustomer = new Customer("Pepa", "Josef", "pepec@gmail.com",
                "password", "01.01.1980", "Pepova vila");
        Assertions.assertTrue(Login.register(dbManager, newCustomer));
    }

    @Test
    protected void register_NewCustomerWithUsedEmailNotAddedToDatabase_false() {
        Customer newCustomer = new Customer("Pepa", "Pracovity", "pepaprac@gmail.com",
                "password", "12.12.1980", "Jugoslavskych Partizanu 5");
        Assertions.assertFalse(Login.register(dbManager, newCustomer));
    }

    @Test
    protected void login_LoginReturnsRegisteredCustomer_registeredCustomer() {
        Assertions.assertEquals(registeredCustomer, Login.login(dbManager, "pepaprac@gmail.com", "password"));
    }

    @Test
    protected void login_LoginWithInvalidPassword_null() {
        Assertions.assertNull(Login.login(dbManager, "pepaprac@gmail.com", "wrongpassword"));
    }

}
