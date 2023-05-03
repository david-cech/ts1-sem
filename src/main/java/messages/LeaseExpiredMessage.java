package messages;

import accessories.MyDate;

public class LeaseExpiredMessage extends Message {

    public LeaseExpiredMessage(long expiry, int rate, String sender) {
        super("Your lease has expired on " + MyDate.getDateString(expiry) +
                " you will be fine at rate " + rate + "$ per day", sender);
    }

    public LeaseExpiredMessage(long expiry, int rate, String sender, long timestamp) {
        super("Your lease has expired on " + MyDate.getDateString(expiry) +
                " you will be fine at rate " + rate + "$ per day", sender, timestamp);
    }

}
