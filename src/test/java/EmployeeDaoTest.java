package test.java;

import main.java.jdbc.dao.EmployeeDao;
import main.java.jdbc.entity.Employee;
import main.java.jdbc.filter.EmployeeFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {
    public static final String DATA_SQL = "data.sql";
    private static Connection connection;
    private static EmployeeDao employeeDao;

    {
        connection = TestConnectionManager.getConnection();
        employeeDao = EmployeeDao.getInstance();
        employeeDao.setConnection(connection);
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
        var expected = EmployeeDao.getInstance();
        var actual = EmployeeDao.getInstance();
        Assertions.assertSame(expected, actual);
    }

    @Test
    void save() {
        var testEmployee = new Employee("test", "test", LocalDate.of(1953, 3, 15), null, 9999999L);
        var employeeFilter = new EmployeeFilter(500,
                0,
                testEmployee.getFirstName(),
                testEmployee.getLastName(),
                testEmployee.getBirthdate(),
                null,
                testEmployee.getSalary());
        var founded = employeeDao.findAll(employeeFilter);
        assertTrue(founded.isEmpty());
        var saved = employeeDao.save(testEmployee);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEmployeeFields(testEmployee, saved);
        var optionalEmployee = employeeDao.findById(saved.getId());
        assertTrue(optionalEmployee.isPresent());
        var employee = optionalEmployee.orElseThrow();
        assertEmployeeFields(saved, employee);
    }

    @Test
    void findAll() {
        var employees = employeeDao.findAll();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
        assertEquals(12, employees.size());
    }

    @Test
    void findAllWithFilter() {
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        var employeeFilter = new EmployeeFilter(500,
                0,
                saved.getFirstName(),
                saved.getLastName(),
                saved.getBirthdate(),
                null,
                saved.getSalary());
        var founded = employeeDao.findAll(employeeFilter);
        assertNotNull(founded);
        assertFalse(founded.isEmpty());
        assertEquals(1, founded.size());
        assertEmployeeFields(saved, founded.get(0));
    }

    @Test
    void findById() {
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        var optionalEmployee = employeeDao.findById(saved.getId());
        assertTrue(optionalEmployee.isPresent());
        var employee = optionalEmployee.orElseThrow();
        assertEmployeeFields(saved, employee);
    }

    @Test
    void updateById() {
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        saved.setFirstName("testUser");
        saved.setLastName("testLastName");
        saved.setBirthdate(LocalDate.of(1999, 1, 1));
        saved.setSalary(500L);
        employeeDao.updateById(saved);
        var optionalEmployee = employeeDao.findById(saved.getId());
        assertTrue(optionalEmployee.isPresent());
        var employee = optionalEmployee.orElseThrow();
        assertEmployeeFields(saved, employee);
    }

    @Test
    void deleteById() {
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        var founded = employeeDao.findById(saved.getId());
        assertTrue(founded.isPresent());
        var result = employeeDao.deleteById(saved.getId());
        assertTrue(result);
        founded = employeeDao.findById(saved.getId());
        assertFalse(founded.isPresent());
    }

    private void assertEmployeeFields(Employee expected, Employee actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getDepartment(), actual.getDepartment());
    }
}