package ticketsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketsproject.model.TicketInfo;

public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
}
