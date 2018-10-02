package com.github.artemzi.services;

import com.github.artemzi.dao.DAO;
import com.github.artemzi.dao.Factory;
import com.github.artemzi.exceptions.DAOException;
import com.github.artemzi.pojo.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.artemzi.dao.Utils.prepareStatement;

public class TaskService extends DAO<Task> {

    private Factory connectionManager;
    private static final String SQL_INSERT = "INSERT INTO tasks VALUES (DEFAULT, ?)";
    private static final String SQL_DELETE = "DELETE FROM tasks WHERE name=?";

    public TaskService() {
        this.connectionManager = Factory.getInstance("javabase.jdbc");
    }

    public boolean addTask(String task) {
        Object[] values = {
                task
        };

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = prepareStatement(connection, SQL_INSERT, false, values)) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating task failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return true;
    }

    public boolean deleteTask(String task) {
        try (
                Connection connection = connectionManager.getConnection();
                PreparedStatement statement = prepareStatement(connection, SQL_DELETE, false, task);
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
