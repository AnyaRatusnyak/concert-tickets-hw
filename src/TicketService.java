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
        String searchId = "ID3";
        try {
            Ticket ticketById = findById(searchId, tickets);
            System.out.println("Found ticket: " + ticketById);
        } catch (NoSuchElementException e) {
            System.out.println("Ticket with id " + searchId + " not found.");
        }
    }

    private static Ticket findById(String id, Ticket[] tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        throw new NoSuchElementException("No ticket found with ID: " + id);
    }
}