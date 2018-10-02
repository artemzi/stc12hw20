package com.github.artemzi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.github.artemzi.dao.Utils.prepareStatement;

public abstract class DAO<T> {
    public abstract boolean add(T item);

    public abstract T getById(int id);

    public abstract boolean update(T item);

    public abstract boolean deleteById(int id);

    /**
     * Map the current row of the given ResultSet to an Object.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Object.
     * @return The mapped Object from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    protected abstract T map(ResultSet resultSet) throws SQLException;

    /**
     * Method can be used with any sql SELECT query.
     * just pass required sql and give expected params
     *
     * @param connectionManager connection instance
     * @param sql SELECT query
     * @param values PreparedStatement query params
     * @return T
     */
    protected T find(Factory connectionManager, String sql, Object... values) {
        T obj = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = prepareStatement(connection, sql, false, values);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                obj = map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
