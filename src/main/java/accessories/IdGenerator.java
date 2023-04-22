package accessories;

public class IdGenerator {

    private static int ctr = 0;

    public static int getId() {
        return ctr++;
    }
}
