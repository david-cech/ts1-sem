package messages;

import accessories.MyDate;
import library.Lease;

public class LeaseCreatedMessage extends Message {
    public LeaseCreatedMessage(Lease l, String sender) {
        super("You have leased book " + l.getBook().toString() + " in library " + l.getLeasedFrom() + " lease through " + MyDate.getDateString(l.getEnd()), sender);
    }

    public LeaseCreatedMessage(Lease l, String sender, long timestamp) {
        super("You have leased book " + l.getBook().toString() + " in library "
                + l.getLeasedFrom() + " lease through " + MyDate.getDateString(l.getEnd()), sender, timestamp);
    }
}
