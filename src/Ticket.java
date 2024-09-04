public class Ticket {
    private String id;
    private String concertHall;
    private int eventCode;
    private long time;
    private boolean isPromo;
    private String stadiumSector;
    private double weight;
    private double price;

    private static final double MAX_WEIGHT = 1.000;

    public Ticket(
            String id,
            String concertHall,
            int eventCode,
            boolean isPromo,
            String stadiumSector,
            double weight,
            double price
    ) {
        validateAndInitialize(
                id,
                concertHall,
                eventCode,
                isPromo,
                stadiumSector,
                weight,
                price
        );
    }

    public Ticket(
            String concertHall,
            int eventCode
    ) {
        validateAndInitialize(
                null,
                concertHall,
                eventCode,
                false,
                null,
                0.00,
                0.00
        );
    }

    public Ticket() {
        this.time = System.currentTimeMillis() / 1000;
    }

    private void validateAndInitialize(
            String id,
            String concertHall,
            int eventCode,
            boolean isPromo,
            String stadiumSector,
            double weight,
            double price
    ) {
        if (id != null && id.length() > 4) {
            throw new IllegalArgumentException(
                    "ID must contain max 4 characters or digits."
            );
        }
        this.id = id;

        if (concertHall.length() > 10) {
            throw new IllegalArgumentException(
                    "Concert hall name must contain max 10 characters."
            );
        }
        this.concertHall = concertHall;

        if (eventCode < 100 || eventCode > 999) {
            throw new IllegalArgumentException(
                    "Event code must be a 3-digit number."
            );
        }
        this.eventCode = eventCode;

        this.time = System.currentTimeMillis() / 1000;
        this.isPromo = isPromo;

        if (stadiumSector != null && (stadiumSector.length() != 1 ||
                stadiumSector.charAt(0) < 'A' || stadiumSector.charAt(0) > 'C')) {
            throw new IllegalArgumentException(
                    "Stadium sector must be a letter between 'A' and 'C'."
            );
        }
        this.stadiumSector = stadiumSector;

        if (weight > MAX_WEIGHT) {
            throw new IllegalArgumentException("The maximum allowed weight limit - 1.000 kg.");
        }
        this.weight = weight;
        this.price = price;
    }

    public String getId() {
        return id;
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
