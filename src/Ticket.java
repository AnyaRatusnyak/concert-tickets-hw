import java.math.BigDecimal;

public class Ticket {
    private String id;
    private String concertHall;
    private int eventCode;
    private long time;
    private boolean isPromo;
    private String stadiumSector;
    private BigDecimal weight;
    private double price;

    private static final BigDecimal MAX_WEIGHT = new BigDecimal("1.000");

    public Ticket(
            String id,
            String concertHall,
            int eventCode,
            boolean isPromo,
            String stadiumSector,
            BigDecimal weight,
            double price
    ) {
        if (id.length() > 4) {
            throw new IllegalArgumentException("ID must contain max 4 characters or digits.");
        }
        this.id = id;
        if ( concertHall.length() > 10) {
            throw new IllegalArgumentException("Concert hall name must contain max 10 characters.");
        }
        this.concertHall = concertHall;
        if (eventCode < 100 || eventCode > 999) {
            throw new IllegalArgumentException("Event code must be a 3-digit number.");
        }
        this.eventCode = eventCode;
        this.time = System.currentTimeMillis() / 1000;
        this.isPromo = isPromo;
        if ( stadiumSector.length() != 1 || stadiumSector.charAt(0) < 'A' || stadiumSector.charAt(0) > 'C') {
            throw new IllegalArgumentException("Stadium sector must be a letter between 'A' and 'C'.");
        }
        this.stadiumSector = stadiumSector;
        if (weight.compareTo(MAX_WEIGHT) > 0) {
            throw new IllegalArgumentException("The maximum allowed weight limit - 1.000 kg.");
        }
        this.weight = weight;
        this.price = price;
    }

    public Ticket(
            String concertHall,
            int eventCode
    ) {
        if ( concertHall.length() > 10) {
            throw new IllegalArgumentException("Concert hall name must contain max 10 characters.");
        }
        this.concertHall = concertHall;
        if (eventCode < 100 || eventCode > 999) {
            throw new IllegalArgumentException("Event code must be a 3-digit number.");
        }
        this.eventCode = eventCode;
        this.time = System.currentTimeMillis();
    }

    public Ticket() {
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", time=" + time +
                ", isPromo=" + isPromo +
                ", stadiumSector='" + stadiumSector + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                '}';
    }
}
