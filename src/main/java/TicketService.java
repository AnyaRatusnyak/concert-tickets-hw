import config.AppConfig;
import dao.TicketDataBaseDao;
import dao.UserDataBaseDao;
import model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import util.NullableWarningValidator;
import util.TicketValidator;
import java.time.LocalDate;
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
        Ticket clientTicket = ((Client) client).getTicket(1);
        clientTicket.setId(5);
        clientTicket.shared("+380971111111", "example@gmail.com");
        clientTicket.shared("+380971111111");
        clientTicket.print();

        User admin = new Admin(1);
        admin.printRole();
        System.out.println(((Admin) admin).checkTicket(clientTicket));

        Ticket ticket1 = new Ticket(1);
        Ticket ticket2 = new Ticket(1);
        System.out.println(ticket1.equals(ticket2));
        System.out.println(ticket1.hashCode() == ticket2.hashCode());

        NullableWarningValidator.checkNulls(clientTicket);
        FileWork fileWork = new FileWork();

        List<BusTicket> busTickets = fileWork.readFromFile("src/main/resources/tickets.json");
        TicketService.printTicketsInfo(busTickets);

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TicketDataBaseDao ticketDataBaseDao = context.getBean(TicketDataBaseDao.class);
        UserDataBaseDao userDataBaseDao = context.getBean(UserDataBaseDao.class);


        System.out.println(userDataBaseDao.get(2L));
        System.out.println(ticketDataBaseDao.getByUserId(2L));

        UserDataBase updateUser = userDataBaseDao.get(2L);
        updateUser.setName("Dimonchik");
        TicketDataBase updateTicket = new TicketDataBase();
        updateTicket.setId(5L);
        updateTicket.setTicketType(BusTicket.TicketType.DAY);
        updateTicket.setCreationDate(LocalDate.of(2024, 9, 20));
        updateTicket.setUser(userDataBaseDao.get(2L));

        userDataBaseDao.updateUserAndTickets(2L, updateUser, List.of(updateTicket));
        System.out.println(userDataBaseDao.get(2L));
        System.out.println(ticketDataBaseDao.getByUserId(2L));
        System.out.println(ticketDataBaseDao.getByUserId(2L));


    }

    private static void printTicketsInfo(List<BusTicket> busTickets) {
        TicketValidator validator = new TicketValidator();
        int size = busTickets.size();
        int validTickets = 0;
        for (BusTicket item : busTickets) {
            try {
                validator.validateBusTicket(item);
                validTickets++;
            } catch (IllegalArgumentException e) {
                System.out.println("Validation errors: " + e.getMessage());
            }
        }
        System.out.println("Total= " + size);
        System.out.println("Valid= " + validTickets);
        System.out.println("Most popular violation= " + validator.getMostFrequentError());
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
