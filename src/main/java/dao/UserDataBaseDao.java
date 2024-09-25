package dao;

import model.TicketDataBase;
import model.UserDataBase;

import java.util.List;

public interface UserDataBaseDao {
    UserDataBase save(UserDataBase userDataBase);

    UserDataBase get(Long id);

    boolean delete(Long id);

    UserDataBase updateUserAndTickets(Long userId, UserDataBase updatedUser, List<TicketDataBase> updatedTickets);
}
