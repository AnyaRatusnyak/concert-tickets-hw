public class TicketService {
    public static void main(String[] args) {
        Ticket empty = new Ticket();
        Ticket full = new Ticket(
                "1234",
                "Red Hall",
                123,
                false,
                "A",
                1.000,
                200.00
        );

        Ticket limited = new Ticket(
                "Red Hall",
                123);
        System.out.println(empty);
        System.out.println(full);
        System.out.println(limited);
    }
}
