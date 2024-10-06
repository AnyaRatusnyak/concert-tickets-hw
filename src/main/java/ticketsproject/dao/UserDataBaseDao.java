package ticketsproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketsproject.model.TicketDataBase;
import ticketsproject.model.UserDataBase;

import java.util.List;

public interface UserDataBaseDao extends JpaRepository<UserDataBase, Long> {
}
