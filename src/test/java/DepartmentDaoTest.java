package test.java;

import main.java.jdbc.dao.DepartmentDao;
import main.java.jdbc.entity.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentDaoTest {
    public static final String DATA_SQL = "data.sql";
    private static Connection connection;
    private static DepartmentDao departmentDao;

    {
        connection = TestConnectionManager.getConnection();
        departmentDao = DepartmentDao.getInstance();
        departmentDao.setConnection(connection);
    }

    @BeforeEach
    public void setup() {
        try (var statement = connection.createStatement()) {
            statement.execute(new String(EmployeeDaoTest.class.getClassLoader().getResourceAsStream(DATA_SQL).readAllBytes()));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInstance() {
    }

    @Test
    void save() {
        var testDepartment = new Department("test", "test", null);
//        var employeeFilter = new EmployeeFilter(500,
//                0,
//                testDepartment.getFirstName(),
//                testDepartment.getLastName(),
//                testDepartment.getBirthdate(),
//                null,
//                testDepartment.getSalary());
        var founded = departmentDao.findAll();
        assertFalse(founded.stream().anyMatch(department -> department.getName().equals("test")
                                                            && department.getCity().equals("test")
                                                            && department.getLocationId() == null));

        var saved = departmentDao.save(testDepartment);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertDepartmentFields(testDepartment, saved);
        var optionalDepartment = departmentDao.findById(saved.getId());
        assertTrue(optionalDepartment.isPresent());
        var department = optionalDepartment.orElseThrow();
        assertDepartmentFields(saved, department);
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

    private void assertDepartmentFields(Department expected, Department actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getLocationId(), actual.getLocationId());
    }
}