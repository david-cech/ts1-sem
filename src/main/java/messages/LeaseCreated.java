package messages;

import accessories.MyDate;
import library.Lease;

public class LeaseCreated extends Message {
    public LeaseCreated(Lease l, String sender) {
        super("You have leased book " + l.getBook().toString() + " in library " + l.getLeasedFrom() + " lease through " + MyDate.getDateString(l.getEnd()), sender);
    }
}
