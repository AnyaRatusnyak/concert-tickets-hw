import util.NullableWarningValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TicketService {
    public static void main(String[] args) {
        Ticket[] tickets = new Ticket[10];
        for (int i = 0; i < tickets.length; i++) {
            tickets[i] = new Ticket(i + 1,
                    "Red Hall",
                    123,
                    false,
                    "A",
                    1.000,
                    200.00);
            System.out.println(tickets[i]);
        }
        int searchId = 3;
        try {
            Ticket ticketById = findById(searchId, tickets);
            System.out.println("Found ticket: " + ticketById);
        } catch (NoSuchElementException e) {
            System.out.println("Ticket with id " + searchId + " not found.");
        }

        String sector = "A";
        Ticket[] ticketsBySector = findByStadiumSector(sector, tickets);
        System.out.println("Tickets in sector " + sector + ":");
        for (Ticket ticket : ticketsBySector) {
            System.out.println(ticket);
        }

        User client = new Client(1);
        client.printRole();
        Ticket clientTicket = ((Client)client).getTicket();
        clientTicket.setId(5);
        clientTicket.shared("+380971111111", "example@gmail.com");
        clientTicket.shared("+380971111111");
        clientTicket.print();

        User admin = new Admin(1);
        admin.printRole();
        System.out.println(((Admin)admin).checkTicket(clientTicket));

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        System.out.println(ticket1.equals(ticket2));
        System.out.println(ticket1.hashCode() == ticket2.hashCode());

        NullableWarningValidator.checkNulls(clientTicket);
    }

    private static Ticket findById(int id, Ticket[] tickets) {
        for (Ticket ticket : tickets) {
            if (ticket.getId() == id) {
                return ticket;
            }
        }
        throw new NoSuchElementException("No ticket found with ID: " + id);
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