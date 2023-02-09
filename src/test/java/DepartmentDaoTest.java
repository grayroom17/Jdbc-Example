package test.java;

import main.java.jdbc.dao.DepartmentDao;
import main.java.jdbc.dao.EmployeeDao;
import main.java.jdbc.entity.Department;
import main.java.jdbc.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoTest {
    public static final String DATA_SQL = "data.sql";
    private Connection connection;
    private DepartmentDao dao;
    private EmployeeDao employeeDao;


    @BeforeEach
    public void setup() {
        initConnection();
        try (var statement = connection.createStatement()) {
            statement.execute(new String(Objects.requireNonNull(EmployeeDaoTest.class.getClassLoader().getResourceAsStream(DATA_SQL)).readAllBytes()));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initConnection() {
        connection = TestConnectionManager.getConnection();
        dao = DepartmentDao.getInstance();
        employeeDao = EmployeeDao.getInstance();
        dao.setConnection(connection);
        employeeDao.setConnection(connection);
    }

    @AfterEach
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Test
    void getInstance() {
        var expected = DepartmentDao.getInstance();
        var actual = DepartmentDao.getInstance();
        Assertions.assertSame(expected, actual);
    }

    @Test
    void save() {
        var testDepartment = new Department("test", "test", null);
        var founded = dao.findAll();
        assertFalse(founded.stream().anyMatch(department -> department.getName().equals("test")
                                                            && department.getCity().equals("test")
                                                            && department.getLocationId() == null));

        var saved = dao.save(testDepartment);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertDepartmentFields(testDepartment, saved);
        var optionalDepartment = dao.findById(saved.getId());
        assertTrue(optionalDepartment.isPresent());
        var department = optionalDepartment.orElseThrow();
        assertDepartmentFields(saved, department);
    }

    @Test
    void findAll() {
        var departments = dao.findAll();

        assertNotNull(departments);
        assertFalse(departments.isEmpty());
        assertEquals(6, departments.size());
    }

    @Test
    void findById() {
        var founded = dao.findById(1L);

        assertTrue(founded.isPresent());
        var department = founded.orElseThrow();
        assertEquals(1L, department.getId());
        assertEquals("Testing", department.getName());
        assertEquals("Melbourne", department.getCity());
        assertNull(department.getLocationId());
    }

    @Test
    void updateById() {
        var department = new Department(1L, "TestName", "TestCity", null);
        var founded = dao.findById(1L).orElseThrow();
        assertEquals(1L, founded.getId());
        assertNotEquals(department.getName(), founded.getName());
        assertNotEquals(department.getCity(), founded.getCity());
        assertEquals(department.getLocationId(), founded.getLocationId());

        dao.updateById(department);

        founded = dao.findById(1L).orElseThrow();
        assertEquals(1L, founded.getId());
        assertEquals(department.getName(), founded.getName());
        assertEquals(department.getCity(), founded.getCity());
        assertEquals(department.getLocationId(), founded.getLocationId());

    }

    @Test
    void deleteById() {
        var optionalDepartment = dao.findById(1L);
        assertTrue(optionalDepartment.isPresent());
        var employees = employeeDao.findAll().stream()
                .filter(employee -> employee.getDepartment().getId().equals(1L))
                .collect(Collectors.toList());
        employees.forEach(employee -> employeeDao.deleteById(employee.getId()));

        var result = dao.deleteById(1L);

        assertTrue(result);
        optionalDepartment = dao.findById(1L);
        assertFalse(optionalDepartment.isPresent());
    }

    private void assertDepartmentFields(Department expected, Department actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getLocationId(), actual.getLocationId());
    }
}