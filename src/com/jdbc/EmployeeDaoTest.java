package com.jdbc;

import com.jdbc.dao.EmployeeDao;
import com.jdbc.entity.Employee;
import com.jdbc.filter.EmployeeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class EmployeeDaoTest {
    private static final Logger log = LoggerFactory.getLogger(EmployeeDaoTest.class);

    public static void main(String[] args) {
        var employeeDao = EmployeeDao.getInstance();
        var employee = new Employee("Ivan",
                "Semonovich",
                LocalDate.of(1990, 10, 18),
                null,
                999999L);
        var savedEmployee = employeeDao.save(employee);
        log.info("{}", savedEmployee);

        savedEmployee.setSalary(300L);
        employeeDao.updateById(savedEmployee);

        var updatedEmployee = employeeDao.findById(savedEmployee.getId());
        log.info("{}", updatedEmployee);

        employeeDao.deleteById(savedEmployee.getId());

        var employees = employeeDao.findAll();
        log.info("{}", employees);

        var employeeFilter = new EmployeeFilter(3, 0, null, null, LocalDate.of(1799, 5, 26), null, null);
        var filteredEmployees = employeeDao.findAll(employeeFilter);
        log.info("{}", filteredEmployees);

        var employeeFilter1 = new EmployeeFilter(3, 3, null, null, null, null, null);
        var filteredEmployees1 = employeeDao.findAll(employeeFilter1);
        log.info("{}", filteredEmployees1);

    }
}
