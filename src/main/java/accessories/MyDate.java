package accessories;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    static final long milis = 86400000;

    public static long dayInMilis() {
        return milis;
    }

    public static String getDateString(long timestamp) {
        if (timestamp < 0) {
            return "Invalid timestamp";
        }
        DateFormat obj = new SimpleDateFormat("dd. MM. yyyy");
        Date res = new Date(timestamp);
        return obj.format(res);
    }
}
