package dao;

import model.BusTicket;
import model.TicketDataBase;

import java.util.List;

public interface TicketDataBaseDao {
    TicketDataBase save(TicketDataBase ticketDataBase);

    TicketDataBase get(Long id);

    List<TicketDataBase> getByUserId(Long id);

    TicketDataBase updateTicketType(Long id, BusTicket.TicketType ticketType);

    boolean delete(Long id);
}
