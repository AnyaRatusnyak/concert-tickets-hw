import java.util.NoSuchElementException;

public class TicketService {
    public static void main(String[] args) {
        Ticket[] tickets = new Ticket[10];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket("ID" + (i + 1),
                    "Red Hall",
                    123,
                    false,
                    "A",
                    1.000,
                    200.00);
            System.out.println(tickets[i]);
        }
    }
}
