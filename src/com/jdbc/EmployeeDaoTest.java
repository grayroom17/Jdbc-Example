package com.jdbc;

import com.jdbc.dao.EmployeeDao;
import com.jdbc.entity.Employee;

import java.time.LocalDate;

public class EmployeeDaoTest {
    public static void main(String[] args) {
        var employeeDao = EmployeeDao.getInstance();
        var employee = new Employee("Ivan",
                "Semonovich",
                LocalDate.of(1990, 10, 18),
                1L,
                999999L);
        var savedEmployee = employeeDao.save(employee);
        System.out.println(savedEmployee);

        savedEmployee.setSalary(300L);
        employeeDao.updateById(savedEmployee);

        var updatedEmployee = employeeDao.findById(savedEmployee.getId());
        System.out.println(updatedEmployee);

        employeeDao.deleteById(savedEmployee.getId());

        var employees = employeeDao.findAll();
        System.out.println(employees);

        var employeeFilter = new EmployeeFilter(3, 0,null,null, LocalDate.of(1799,05,26),null,null);
        var filteredEmployees = employeeDao.findAll(employeeFilter);
        System.out.println(filteredEmployees);

        var employeeFilter1 = new EmployeeFilter(3, 3,null,null, null,null,null);
        var filteredEmployees1 = employeeDao.findAll(employeeFilter1);
        System.out.println(filteredEmployees1);
    }
}
