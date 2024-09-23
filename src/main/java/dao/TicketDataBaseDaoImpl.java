package dao;

import model.BusTicket;
import model.TicketDataBase;
import util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDataBaseDaoImpl implements TicketDataBaseDao {
    private static final String SQL_INSERT = "INSERT INTO ticket_data_base (user_id, ticket_type, creation_date) VALUES (?, ?::tickettype, ?)";
    private static final String SQL_SELECT = "SELECT * FROM ticket_data_base WHERE id = ?";
    private static final String SQL_SELECT_TICKETS_USER_ID = "SELECT * FROM ticket_data_base INNER JOIN user_data_base ON ticket_data_base.user_id = user_data_base.id WHERE user_data_base.id = ?";
    private static final String SQL_UPDATE_TICKET_TYPE = "UPDATE ticket_data_base SET ticket_type = ?::tickettype WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM ticket_data_base WHERE id = ?";

    @Override
    public TicketDataBase save(TicketDataBase ticketDataBase) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ticketDataBase.getUserId());
            statement.setString(2, ticketDataBase.getTicketType().name());
            statement.setDate(3, java.sql.Date.valueOf(ticketDataBase.getCreationDate()));
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                ticketDataBase.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't add a new ticket to DB", e);
        }
        return ticketDataBase;
    }

    @Override
    public TicketDataBase get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String ticketTypeString = resultSet.getString("ticket_type");
                BusTicket.TicketType ticketType = BusTicket.TicketType.valueOf(ticketTypeString);
                Date date = resultSet.getObject("creation_date", Date.class);
                Long idUser = resultSet.getLong("user_id");

                TicketDataBase ticketDataBase = new TicketDataBase();
                ticketDataBase.setId(id);
                ticketDataBase.setTicketType(ticketType);
                ticketDataBase.setCreationDate(date.toLocalDate());
                ticketDataBase.setUserId(idUser);
                return ticketDataBase;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
        return null;
    }

    @Override
    public List<TicketDataBase> getByUserId(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TICKETS_USER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<TicketDataBase> tickets = new ArrayList<>();
            while (resultSet.next()) {
                String ticketTypeString = resultSet.getString("ticket_type");
                BusTicket.TicketType ticketType = BusTicket.TicketType.valueOf(ticketTypeString);
                Date date = resultSet.getObject("creation_date", Date.class);
                Long idUser = resultSet.getLong("user_id");
                Long idTicket = resultSet.getLong("id");

                TicketDataBase ticketDataBase = new TicketDataBase();
                ticketDataBase.setId(idTicket);
                ticketDataBase.setTicketType(ticketType);
                ticketDataBase.setCreationDate(date.toLocalDate());
                ticketDataBase.setUserId(idUser);
                tickets.add(ticketDataBase);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }

    @Override
    public TicketDataBase updateTicketType(Long id, BusTicket.TicketType ticketType) {
        TicketDataBase ticketDataBase = get(id);
        if (ticketDataBase == null) {
            throw new RuntimeException("Ticket with ID: " + id + " not found");
        }
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TICKET_TYPE)) {
            statement.setString(1, ticketType.name());
            statement.setLong(2, id);
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Updated 0 rows");
            }
            ticketDataBase.setTicketType(ticketType);
            return ticketDataBase;
        } catch (SQLException e) {
            throw new RuntimeException("Can't add a new ticket to DB", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
