package dao;

import model.BusTicket;
import model.TicketDataBase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;
import java.util.List;

public class TicketDataBaseDaoImpl implements TicketDataBaseDao {

    @Override
    public TicketDataBase save(TicketDataBase ticketDataBase) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(ticketDataBase);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while saving a ticket");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ticketDataBase;
    }

    @Override
    public TicketDataBase get(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(TicketDataBase.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get ticket with id " + id);
        }
    }

    @Override
    public List<TicketDataBase> getByUserId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<TicketDataBase> query = session.createQuery(
                    "FROM TicketDataBase t WHERE t.user.id = :userId", TicketDataBase.class);
            query.setParameter("userId", id);

            return query.list();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get tickets for user with id " + id, e);
        }
    }

    @Override
    public TicketDataBase updateTicketType(Long id, BusTicket.TicketType ticketType) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            TicketDataBase ticketDataBase = session.get(TicketDataBase.class, id);
            if (ticketDataBase == null) {
                throw new RuntimeException("Ticket with ID: " + id + " not found");
            }
            ticketDataBase.setTicketType(ticketType);
            session.update(ticketDataBase);
            transaction.commit();
            return ticketDataBase;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while updating ticket type for ticket with ID: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            TicketDataBase ticketDataBase = session.get(TicketDataBase.class, id);
            if (ticketDataBase == null) {
                return false;
            }
            session.delete(ticketDataBase);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while deleting ticket with ID: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
