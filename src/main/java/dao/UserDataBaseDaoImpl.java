package dao;

import model.UserDataBase;
import util.ConnectionUtil;

import java.sql.*;


public class UserDataBaseDaoImpl implements UserDataBaseDao {
    private static final String SQL_INSERT = "INSERT INTO user_data_base (name, creation_date) VALUES (?, ?)";
    private static final String SQL_SELECT = "SELECT * FROM user_data_base WHERE id = ?";
    private static final String SQL_DELETE_TICKETS = "DELETE FROM ticket_data_base WHERE user_id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM user_data_base WHERE id = ?";

    @Override
    public UserDataBase save(UserDataBase userDataBase) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userDataBase.getName());
            statement.setDate(2, java.sql.Date.valueOf(userDataBase.getCreationDate()));
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Inserted 0 rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                userDataBase.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Can't add a new user to DB", e);
        }
        return userDataBase;
    }

    @Override
    public UserDataBase get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userName = resultSet.getString("name");
                Date date = resultSet.getObject("creation_date", Date.class);

                UserDataBase userDataBase = new UserDataBase();
                userDataBase.setId(id);
                userDataBase.setName(userName);
                userDataBase.setCreationDate(date.toLocalDate());
                return userDataBase;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement deleteTicketsStatement = connection.prepareStatement(SQL_DELETE_TICKETS);
                 PreparedStatement deleteUserStatement = connection.prepareStatement(SQL_DELETE_USER)) {

                deleteTicketsStatement.setLong(1, id);
                deleteTicketsStatement.executeUpdate();

                deleteUserStatement.setLong(1, id);
                deleteUserStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Transaction failed. Rolled back.", e);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't create connection to DB", e);
        }
        return true;
    }
}
