package ticketsproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticketsproject.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
