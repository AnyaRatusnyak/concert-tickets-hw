package ticketsproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ticketsproject.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {
    public static final int ID = 3;
    public static final int NON_EXISTING_ID = 20;
    public static final int EXPECTED_TICKETS_LENGTH = 10;
    public static final int EXPECTED_LENGTH = 0;
    public static final String VALID_SECTOR = "A";
    public static final String INVALID_SECTOR = "Z";
    private Ticket[] tickets;
    private List<BusTicket> busTickets;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        tickets = new Ticket[10];
        for (int i = 0; i < tickets.length; i++) {
            ticket = new Ticket(i + 1,
                    "Red Hall",
                    123,
                    false,
                    "A",
                    1.000,
                    200.00);
            tickets[i] = ticket;
        }
        BusTicket validTicket1 = new BusTicket(1, BusTicket.TicketClass.CLA, BusTicket.TicketType.YEAR,"2025-01-01",10);
        BusTicket validTicket2 = new BusTicket(2, BusTicket.TicketClass.STD, BusTicket.TicketType.DAY,"2025-08-01",20);
        busTickets = new ArrayList<>();
        busTickets.add(validTicket1);
        busTickets.add(validTicket2);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStream() {
        System.setOut(originalOut);
    }

    @Test
    void findById_ExistingId_ReturnsTicket() {
        Ticket ticket = TicketService.findById(ID, tickets);
        assertNotNull(ticket);
        assertEquals(ID, ticket.getId());
    }

    @Test
    void findById_NonExistingId_ThrowsEntityNotFoundException() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            TicketService.findById(NON_EXISTING_ID, tickets);
        });
        assertEquals("No ticket found with ID: " + NON_EXISTING_ID, exception.getMessage());
    }

    @Test
    void findByStadiumSector_validSector_returnsTickets() {
        Ticket[] result = TicketService.findByStadiumSector(VALID_SECTOR, tickets);
        int actualLength = result.length;
        assertEquals(EXPECTED_TICKETS_LENGTH, actualLength);
    }

    @Test
    void findByStadiumSector_invalidSector_returnsEmptyArray() {
        Ticket[] result = TicketService.findByStadiumSector( INVALID_SECTOR, tickets);
        int actualLength = result.length;
        assertEquals(EXPECTED_LENGTH, actualLength);
    }

    @Test
    void printTicketsInfo_allValidTickets_noErrors() {
        TicketService.printTicketsInfo(busTickets);
        assertEquals("Total= 2\nValid= 2\nMost popular violation= No errors recorded".replaceAll("\\s", ""),
                outContent.toString().replaceAll("\\s", ""));
    }

    @Test
    void printTicketsInfo_noTickets() {
        List<BusTicket> emptyList = new ArrayList<>();
        TicketService.printTicketsInfo(emptyList);
        assertEquals("Total= 0\nValid= 0\nMost popular violation= No errors recorded".replaceAll("\\s", ""),
                outContent.toString().replaceAll("\\s", ""));
    }
}
