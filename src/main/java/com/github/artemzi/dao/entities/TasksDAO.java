package com.github.artemzi.dao.entities;

import com.github.artemzi.dao.DAO;
import com.github.artemzi.dao.Factory;
import com.github.artemzi.exceptions.DAOException;
import com.github.artemzi.pojo.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.artemzi.dao.Utils.prepareStatement;

public class TasksDAO extends DAO<Task> {
    private Factory connectionManager;
    private static final String SQL_FIND_BY_ID = "SELECT id, name from tasks WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO tasks VALUES (DEFAULT, ?)";
    private static final String SQL_UPDATE = "UPDATE tasks SET name=? WHERE id=?";
    private static final String SQL_DELETE = "DELETE FROM tasks WHERE id=?";

    public TasksDAO(Factory factory) {
        this.connectionManager = factory;
    }

    @Override
    public boolean add(Task item) {
        Object[] values = {
                item.getId(),
                item.getName()
        };

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating task failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating task failed, no generated id key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public Task getById(int id) {
        return find(connectionManager, SQL_FIND_BY_ID, id);
    }

    @Override
    public boolean update(Task item) {
        Object[] values = {
                item.getId(),
                item.getName()
        };

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = prepareStatement(connection, SQL_UPDATE, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating student failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, id);
        ) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Deleting task failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    protected Task map(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}

