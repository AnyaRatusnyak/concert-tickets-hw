package ticketsproject.service;

import ticketsproject.model.TicketInfo;

public interface TicketInfoService {
    TicketInfo findById(Long id);
}
