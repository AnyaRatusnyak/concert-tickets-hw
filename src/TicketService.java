import java.util.ArrayList;
import java.util.List;
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

        String sector = "A";
        Ticket[] ticketsBySector = findByStadiumSector(sector, tickets);
        System.out.println("Tickets in sector " + sector + ":");
        for (Ticket ticket : ticketsBySector) {
            System.out.println(ticket);
        }
    }

    private static Ticket[] findByStadiumSector(String sector, Ticket[] tickets) {
        List<Ticket> ticketsInSector = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getStadiumSector().equals(sector)) {
                ticketsInSector.add(ticket);
            }
        }
        return ticketsInSector.toArray(new Ticket[0]);
    }
}