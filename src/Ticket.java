import annotation.NullableWarning;

import java.util.Objects;

public class Ticket extends IdentifiableEntity {
    @NullableWarning
    private final String concertHall;
    private final int eventCode;
    private long time;
    private final boolean isPromo;
    @NullableWarning
    private String stadiumSector;
    private final double weight;
    private final double price;

    private static final double MAX_WEIGHT = 1.000;


    public Ticket(int id, String concertHall, int eventCode, boolean isPromo, String stadiumSector, double weight, double price) {
        super(id);
        validateFields(
                concertHall,
                eventCode,
                stadiumSector,
                weight
        );
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = System.currentTimeMillis() / 1000;
        this.isPromo = isPromo;
        this.stadiumSector = stadiumSector;
        this.weight = weight;
        this.price = price;
    }

    public Ticket(String concertHall, int eventCode) {
        super(0);
        validateFields(
                concertHall,
                eventCode,
                null,
                0.00
        );
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.time = System.currentTimeMillis() / 1000;
        this.isPromo = false;
        this.weight = 0.00;
        this.price = 0.00;
    }

    public Ticket() {
        super(0);

        this.concertHall = null;
        this.eventCode = 0;
        this.time = System.currentTimeMillis() / 1000;
        this.isPromo = false;
        this.weight = 0.00;
        this.price = 0.00;
    }

    public String getStadiumSector() {
        return stadiumSector;
    }

    public double getPrice() {
        return price;
    }

    public void shared(String phoneNumber) {
        System.out.println("Ticket shared by phone: " + phoneNumber);
    }

    public void shared(String phoneNumber, String email) {
        System.out.println("Ticket shared by phone: " + phoneNumber + " and by e-mail: " + email);
    }

    @Override
    public void print() {
        System.out.println("Ticket Details:");
        System.out.println(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(concertHall, eventCode, time, isPromo, stadiumSector, weight, price, id);
    }

    @Override
    public boolean equals(Object ticket) {
        if (ticket == this) {
            return true;
        }
        if (ticket == null) {
            return false;
        }
        if (ticket.getClass().equals(Ticket.class)) {
            Ticket current = (Ticket) ticket;
            return eventCode == current.eventCode &&
                    time == current.time &&
                    isPromo == current.isPromo &&
                    Double.compare(current.weight, weight) == 0 &&
                    Double.compare(current.price, price) == 0 &&
                    Objects.equals(concertHall, current.concertHall) &&
                    Objects.equals(stadiumSector, current.stadiumSector) &&
                    Objects.equals(id, current.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "concertHall='" + concertHall + '\'' +
                ", eventCode=" + eventCode +
                ", time=" + time +
                ", isPromo=" + isPromo +
                ", stadiumSector='" + stadiumSector + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", id=" + id +
                '}';
    }

    private void validateFields(
            String concertHall,
            int eventCode,
            String stadiumSector,
            double weight
    ) {
        if (concertHall.length() > 10) {
            throw new IllegalArgumentException(
                    "Concert hall name must contain max 10 characters."
            );
        }


        if (eventCode < 100 || eventCode > 999) {
            throw new IllegalArgumentException(
                    "Event code must be a 3-digit number."
            );
        }

        this.time = System.currentTimeMillis() / 1000;

        if (stadiumSector != null && (stadiumSector.length() != 1 ||
                stadiumSector.charAt(0) < 'A' || stadiumSector.charAt(0) > 'C')) {
            throw new IllegalArgumentException(
                    "Stadium sector must be a letter between 'A' and 'C'."
            );
        }


        if (weight > MAX_WEIGHT) {
            throw new IllegalArgumentException("The maximum allowed weight limit - 1.000 kg.");
        }
    }
}
