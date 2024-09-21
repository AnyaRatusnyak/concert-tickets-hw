package dao;

import model.BusTicket;
import model.TicketDataBase;
import util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDataBaseDaoImpl implements TicketDataBaseDao {
    @Override
    public TicketDataBase save(TicketDataBase ticketDataBase) {
        String sql = "INSERT INTO ticket_data_base (user_id, ticket_type, creation_date) VALUES (?, ?::tickettype, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
        String sql = "SELECT * FROM ticket_data_base WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        String sql = "SELECT * FROM ticket_data_base INNER JOIN user_data_base ON ticket_data_base.user_id = user_data_base.id WHERE user_data_base.id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        String sql = "UPDATE ticket_data_base SET ticket_type = ?::tickettype WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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
        String sql = "DELETE FROM ticket_data_base WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
    }
}
