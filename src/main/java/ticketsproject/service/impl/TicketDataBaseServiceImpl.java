package ticketsproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ticketsproject.dao.TicketDataBaseDao;
import ticketsproject.model.TicketDataBase;
import ticketsproject.service.TicketDataBaseService;

@Service
@RequiredArgsConstructor
public class TicketDataBaseServiceImpl implements TicketDataBaseService {
    private final TicketDataBaseDao ticketDataBaseDao;

    @Override
    public TicketDataBase findById(Long id) {
        return ticketDataBaseDao.findById(id).get();
    }
}
