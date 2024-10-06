package ticketsproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketsproject.model.BusTicket;
import ticketsproject.model.TicketDataBase;

import java.util.List;

public interface TicketDataBaseDao extends JpaRepository<TicketDataBase, Long> {
}
