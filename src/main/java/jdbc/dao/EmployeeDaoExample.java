package main.java.jdbc.dao;

import main.java.jdbc.entity.Employee;
import main.java.jdbc.filter.EmployeeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class EmployeeDaoExample {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeDaoExample.class);

    public static void main(String[] args) {
        var employeeDao = EmployeeDao.getInstance();
        var employee = new Employee("Ivan",
                "Semenovich",
                LocalDate.of(1990, 10, 18),
                null,
                999999L);
        var savedEmployee = employeeDao.save(employee);
        LOG.info("{}", savedEmployee);

        savedEmployee.setSalary(300L);
        employeeDao.updateById(savedEmployee);

        var updatedEmployee = employeeDao.findById(savedEmployee.getId());
        LOG.info("{}", updatedEmployee);

        employeeDao.deleteById(savedEmployee.getId());

        var employees = employeeDao.findAll();
        LOG.info("{}", employees);

        var employeeFilter = new EmployeeFilter(3, 0, null, null, LocalDate.of(1799, 5, 26), null, null);
        var filteredEmployees = employeeDao.findAll(employeeFilter);
        LOG.info("{}", filteredEmployees);

        var employeeFilter1 = new EmployeeFilter(3, 3, null, null, null, null, null);
        var filteredEmployees1 = employeeDao.findAll(employeeFilter1);
        LOG.info("{}", filteredEmployees1);
    }
}
