package com.jdbc.dao;

import com.jdbc.entity.Employee;
import com.jdbc.exception.DaoException;
import com.jdbc.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDao {
    private static volatile EmployeeDao instance;
    private static final String SAVE = """
                                       insert into employee (first_name, last_name, birthdate, department_id, salary)
                                       values (?,?,?,?,?);
                                       """;

    private static final String FIND_ALL = """
                                           select first_name,
                                             last_name,
                                             birthdate,
                                             salary,
                                             department_id
                                             from employee
                                           """;

    private static final String FIND_BY_ID = FIND_ALL + """
                                                        where id = ?;
                                                        """;

    private static final String UPDATE = """
                                         update employee
                                         set first_name = ?,
                                         last_name = ?,
                                         birthdate = ?,
                                         salary = ?,
                                         department_id =?
                                         WHERE id = ?;
                                         """;

    private static final String DELETE = """
                                         delete from employee
                                         where id = ?;
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

    public List<Employee> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL)) {

            var resultSet = preparedStatement.executeQuery();

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                var employee = new Employee();
                employee.buildByResultSet(resultSet);
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Optional<Employee> findById(Long id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();

            Employee employee = null;
            if (resultSet.next()) {
                employee = new Employee();
                employee.buildByResultSet(resultSet);
            }

            return Optional.ofNullable(employee);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void updateById(Employee employee) {
        try (var connection = ConnectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(employee.getBirthdate().atStartOfDay()));
            preparedStatement.setLong(4, employee.getSalary());
            preparedStatement.setLong(5, employee.getDepartmentId());
            preparedStatement.setLong(6, employee.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
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


}
