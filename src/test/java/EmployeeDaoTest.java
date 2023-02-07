package test.java;

import main.java.jdbc.dao.EmployeeDao;
import main.java.jdbc.entity.Employee;
import main.java.jdbc.filter.EmployeeFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {

    @Test
    void getInstance() {
        var expected = EmployeeDao.getInstance();
        var actual = EmployeeDao.getInstance();
        Assertions.assertSame(expected, actual);
    }

    @Test
    void save() {
        var testEmployee = new Employee("test", "test", LocalDate.of(1953, 3, 15), null, 9999999L);
        var employeeDao = EmployeeDao.getInstance();
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
        assertEquals(testEmployee.getFirstName(), saved.getFirstName());
        assertEquals(testEmployee.getLastName(), saved.getLastName());
        assertEquals(testEmployee.getBirthdate(), saved.getBirthdate());
        assertEquals(testEmployee.getSalary(), saved.getSalary());
        assertEquals(testEmployee.getDepartment(), saved.getDepartment());
        var optionalEmployee = employeeDao.findById(saved.getId());
        assertTrue(optionalEmployee.isPresent());
        var employee = optionalEmployee.orElseThrow();
        assertEmployeeFields(saved, employee);
        employeeDao.deleteById(saved.getId());
    }

    @Test
    void findAll() {
        var employeeDao = EmployeeDao.getInstance();
        var employees = employeeDao.findAll();
        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    void findAllWithFilter() {
        var employeeDao = EmployeeDao.getInstance();
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
        assertEquals(saved.getId(), founded.get(0).getId());
        assertEquals(saved.getFirstName(), founded.get(0).getFirstName());
        assertEquals(saved.getLastName(), founded.get(0).getLastName());
        assertEquals(saved.getBirthdate(), founded.get(0).getBirthdate());
        assertEquals(saved.getSalary(), founded.get(0).getSalary());
        employeeDao.deleteById(saved.getId());
    }

    @Test
    void findById() {
        var employeeDao = EmployeeDao.getInstance();
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        var optionalEmployee = employeeDao.findById(saved.getId());
        assertTrue(optionalEmployee.isPresent());
        var employee = optionalEmployee.orElseThrow();
        assertEmployeeFields(saved, employee);
        employeeDao.deleteById(saved.getId());
    }

    @Test
    void updateById() {
        var employeeDao = EmployeeDao.getInstance();
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
        employeeDao.deleteById(saved.getId());
    }

    @Test
    void deleteById() {
        var employeeDao = EmployeeDao.getInstance();
        var saved = employeeDao.save(new Employee("test",
                "test",
                LocalDate.of(1953, 3, 15),
                null,
                9999999L));
        var founded = employeeDao.findById(saved.getId());
        assertTrue(founded.isPresent());
        employeeDao.deleteById(saved.getId());
        founded = employeeDao.findById(saved.getId());
        assertFalse(founded.isPresent());
    }

    private void assertEmployeeFields(Employee expected, Employee actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getBirthdate(), actual.getBirthdate());
        assertEquals(expected.getSalary(), actual.getSalary());
    }
}