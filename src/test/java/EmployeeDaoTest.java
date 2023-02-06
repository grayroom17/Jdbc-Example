package test.java;

import main.java.jdbc.dao.EmployeeDao;
import main.java.jdbc.entity.Department;
import main.java.jdbc.entity.Employee;
import main.java.jdbc.filter.EmployeeFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

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
        var funded = employeeDao.findAll(employeeFilter);
        assertTrue(funded.isEmpty());
        var saved = employeeDao.save(testEmployee);
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(testEmployee.getFirstName(), saved.getFirstName());
        assertEquals(testEmployee.getLastName(), saved.getLastName());
        assertEquals(testEmployee.getBirthdate(), saved.getBirthdate());
        assertEquals(testEmployee.getSalary(), saved.getSalary());
        assertEquals(testEmployee.getDepartment(), saved.getDepartment());
        var foundedEmployee = employeeDao.findById(saved.getId());
        assertTrue(foundedEmployee.isPresent());
        var employee = foundedEmployee.get();
        assertEquals(saved.getId(), employee.getId());
        assertEquals(saved.getFirstName(), employee.getFirstName());
        assertEquals(saved.getLastName(), employee.getLastName());
        assertEquals(saved.getBirthdate(), employee.getBirthdate());
        assertEquals(saved.getSalary(), employee.getSalary());
        assertEquals(saved.getDepartment(), employee.getDepartment());
        employeeDao.deleteById(saved.getId());
    }

    @Test
    void findAll() {
    }

    @Test
    void testFindAll() {
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

    @Test
    void buildByResultSet() {
    }
}