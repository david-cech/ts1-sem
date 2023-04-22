package accessories;

import accessories.MyDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyDateTest {

    @Test
    public void getDateString_ValidDate_DateString(){
        Assertions.assertEquals("21. 04. 2023", MyDate.getDateString(1682064982881L));
    }

    @Test
    public void getDateString_InvalidDate_DateString(){
        Assertions.assertEquals("Invalid timestamp", MyDate.getDateString(-209213));
    }
}
