package ticketsproject.service;

import ticketsproject.model.TicketDataBase;

public interface TicketDataBaseService {
    TicketDataBase findById(Long id);
}
