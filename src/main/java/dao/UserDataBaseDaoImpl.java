package dao;

import jakarta.persistence.Query;
import model.TicketDataBase;
import model.UserDataBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDataBaseDaoImpl implements UserDataBaseDao {
    private final SessionFactory sessionFactory;
    private final TicketDataBaseDao ticketDataBaseDao;
    private static final String QUERY_DELETE_TICKETS = "DELETE FROM TicketDataBase t WHERE t.user.id = :userId";

    @Autowired
    public UserDataBaseDaoImpl(SessionFactory sessionFactory, TicketDataBaseDao ticketDataBaseDao) {
        this.sessionFactory = sessionFactory;
        this.ticketDataBaseDao = ticketDataBaseDao;
    }

    @Override
    public UserDataBase save(UserDataBase userDataBase) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(userDataBase);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while saving a user");
        }
        return userDataBase;
    }

    @Override
    public UserDataBase get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(UserDataBase.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get user with id " + id);
        }
    }

    @Override
    public UserDataBase updateUserAndTickets(Long userId, UserDataBase updatedUser, List<TicketDataBase> updatedTickets) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            UserDataBase user = session.get(UserDataBase.class, userId);
            if (user == null) {
                throw new RuntimeException("User with id " + userId + " does not exist");
            }
            user.setName(updatedUser.getName());
            user.setCreationDate(updatedUser.getCreationDate());
            session.update(user);
            List<TicketDataBase> existingTickets = ticketDataBaseDao.getByUserId(userId);
            for (TicketDataBase ticket : existingTickets) {
                for (TicketDataBase updatedTicket : updatedTickets) {
                    if (ticket.getId().equals(updatedTicket.getId())) {
                        ticket.setTicketType(updatedTicket.getTicketType());
                        ticket.setCreationDate(updatedTicket.getCreationDate());
                        session.update(ticket);
                    }
                }
            }
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while updating user and tickets for user with id " + userId, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query deleteTicketsQuery = session.createQuery(QUERY_DELETE_TICKETS);
            deleteTicketsQuery.setParameter("userId", id);
            deleteTicketsQuery.executeUpdate();
            UserDataBase user = session.get(UserDataBase.class, id);
            if (user == null) {
                throw new RuntimeException("User with id " + id + " does not exist");
            }
            session.delete(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting user with id " + id, e);
        }
    }
}
