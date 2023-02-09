package main.java.jdbc.dao;

import main.java.jdbc.entity.Department;
import main.java.jdbc.exception.DaoException;
import main.java.jdbc.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DepartmentDao implements Dao<Long, Department> {
    private static DepartmentDao instance;
    private Connection connection = ConnectionManager.getConnection();

    private static final String SAVE = """
                                       insert into department (name, city, location_id)
                                       values (?, ? , ?)
                                       """;

    private static final String FIND_ALL = """
                                           select id,
                                                  name,
                                                  city,
                                                  location_id
                                             from department
                                           """;
    private static final String FIND_BY_ID = FIND_ALL + """
                                                        where id = ?;
                                                        """;

    private static final String UPDATE = """
                                         update department
                                         SET name = ?,
                                         city = ?,
                                         location_id = ?
                                         where id = ?
                                         """;

    private static final String DELETE = """
                                         delete from department
                                         where id = ?
                                         """;

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        if (instance == null) {
            instance = new DepartmentDao();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Department save(Department department) {
        try (var preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, department.getName());
            preparedStatement.setString(2, department.getCity());
            preparedStatement.setObject(3, department.getLocationId());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                department.setId(generatedKeys.getLong("id"));
            }
            return department;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Department> findAll() {
        try (var preparedStatement = connection.prepareStatement(FIND_ALL)) {
            var resultSet = preparedStatement.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                var department = buildByResultSet(resultSet);
                departments.add(department);
            }
            return departments;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();

            Department department = null;
            if (resultSet.next()) {
                department = buildByResultSet(resultSet);
            }

            return Optional.ofNullable(department);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateById(Department entity) {
        try (var preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getCity());
            preparedStatement.setObject(3, entity.getLocationId());
            preparedStatement.setObject(4, entity.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (var preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Department buildByResultSet(ResultSet resultSet) throws SQLException {
        var department = new Department();
        department.setId(resultSet.getLong("id"));
        department.setName(resultSet.getString("name"));
        department.setCity(resultSet.getString("city"));
        department.setLocationId(resultSet.getLong("location_id") == 0 ? null : resultSet.getLong("location_id"));
        return department;
    }
}
