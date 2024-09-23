package model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketDataBase {
    private Long id;
    private BusTicket.TicketType ticketType;
    private LocalDate creationDate;
    private Long userId;
}
