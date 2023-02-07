package main.java.jdbc.dao;

import main.java.jdbc.entity.Employee;
import main.java.jdbc.exception.DaoException;
import main.java.jdbc.filter.EmployeeFilter;
import main.java.jdbc.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDao implements Dao<Long, Employee> {
    private static volatile EmployeeDao instance;
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();

    private static final String SAVE = """
                                       insert into employee (first_name, last_name, birthdate, department_id, salary)
                                       values (?,?,?,?,?);
                                       """;

    private static final String FIND_ALL = """
                                           select id,
                                             first_name,
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
            preparedStatement.setObject(4, employee.getDepartment());
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
                var employee = buildByResultSet(resultSet);
                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Employee> findAll(EmployeeFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.getFirstName() != null) {
            whereSql.add("first_name like ?");
            parameters.add("%" + filter.getFirstName() + "%");
        }
        if (filter.getLastName() != null) {
            whereSql.add("last_name like ?");
            parameters.add("%" + filter.getLastName() + "%");
        }
        if (filter.getBirthdate() != null) {
            whereSql.add("birthdate = ?");
            parameters.add(filter.getBirthdate());
        }
        if (filter.getDepartmentId() != null) {
            whereSql.add("department_id = ?");
            parameters.add(filter.getDepartmentId());
        }
        if (filter.getSalary() != null) {
            whereSql.add("salary = ?");
            parameters.add(filter.getSalary());
        }
        parameters.add(filter.getLimit());
        parameters.add(filter.getOffset());

        String additionalParameters;
        if (!whereSql.isEmpty()) {
            additionalParameters = whereSql.stream()
                    .collect(Collectors.joining(" and ", " where ", " limit ? offset ? "));
        } else {
            additionalParameters = """
                                   limit ?
                                   offset ?
                                   """;
        }

        var sql = FIND_ALL + additionalParameters;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            var resultSet = preparedStatement.executeQuery();

            List<Employee> employees = new ArrayList<>();
            while (resultSet.next()) {
                var employee = buildByResultSet(resultSet);
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
                employee = buildByResultSet(resultSet);
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
            preparedStatement.setObject(5, employee.getDepartment());
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

    private Employee buildByResultSet(ResultSet resultSet) throws SQLException {
        var employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setBirthdate(LocalDate.from(resultSet.getTimestamp("birthdate").toLocalDateTime()));
        employee.setSalary(resultSet.getLong("salary"));
        var department = departmentDao.findById(resultSet.getLong("department_id")).orElse(null);
        employee.setDepartment(department);
        return employee;
    }
}
