package com.jdbc.dao;

import com.jdbc.entity.Employee;
import com.jdbc.exception.DaoException;
import com.jdbc.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class EmployeeDao {
    private static volatile EmployeeDao instance;
    private static final String DELETE = """
                                         delete from employee
                                         where id = ?;
                                         """;

    private static final String SAVE = """
                                       insert into employee (first_name, last_name, birthdate, department_id, salary)
                                       values (?,?,?,?,?);
                                       """;

    private EmployeeDao() {
    }

    public static EmployeeDao getInstance() {
        if (instance == null) {
            synchronized (EmployeeDao.class) {
                if (instance == null) {
                    instance = new EmployeeDao();
                }
            }
        }
        return instance;
    }

    public boolean deleteById(Long id) {
        try (var connection = ConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Employee save(Employee employee) {
        try (final var connection = ConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(employee.getBirthdate().atStartOfDay()));
            preparedStatement.setLong(4, employee.getDepartmentId());
            preparedStatement.setLong(5, employee.getSalary());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                employee.setId(generatedKeys.getLong("id"));
            }
            return employee;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
