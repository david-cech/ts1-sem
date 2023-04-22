package messages;

import accessories.MyDate;

public class LeaseWillExpire extends Message {

    public LeaseWillExpire(long expiry, String sender) {
        super("Your lease ends on " + MyDate.getDateString(expiry), sender);
    }

}
