package com.jdbc;

import com.jdbc.dao.EmployeeDao;
import com.jdbc.entity.Employee;

import java.time.LocalDate;
import java.util.List;

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
    }
}
