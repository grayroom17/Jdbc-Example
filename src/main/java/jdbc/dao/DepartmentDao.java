package main.java.jdbc.dao;

import main.java.jdbc.entity.Department;
import main.java.jdbc.exception.DaoException;
import main.java.jdbc.util.ConnectionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DepartmentDao implements Dao<Long, Department> {
    private static volatile DepartmentDao instance;
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

    private DepartmentDao() {
    }

    public static DepartmentDao getInstance() {
        if (instance == null) {
            synchronized (EmployeeDao.class) {
                if (instance == null) {
                    instance = new DepartmentDao();
                }
            }
        }
        return instance;
    }

    @Override
    public Department save(Department entity) {
        return null;
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();

            Department department = null;
            if (resultSet.next()) {
                department = new Department();
                department.buildByResultSet(resultSet);
            }

            return Optional.ofNullable(department);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateById(Department entity) {

    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
