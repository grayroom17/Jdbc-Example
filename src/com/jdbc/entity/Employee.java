package com.jdbc.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Long departmentId;
    private Long salary;
    private byte[] photo;

    public Employee() {
    }

    public Employee(Long id,
                    String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Long departmentId,
                    Long salary,
                    byte[] photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.departmentId = departmentId;
        this.salary = salary;
        this.photo = photo;
    }

    public Employee(Long id,
                    String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Long departmentId,
                    Long salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public Employee(String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Long departmentId,
                    Long salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Employee{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthdate=" + birthdate +
               ", departmentId=" + departmentId +
               ", salary=" + salary +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id)
               && firstName.equals(employee.firstName)
               && lastName.equals(employee.lastName)
               && birthdate.equals(employee.birthdate)
               && departmentId.equals(employee.departmentId)
               && salary.equals(employee.salary)
               && Arrays.equals(photo, employee.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id,
                firstName,
                lastName,
                birthdate,
                departmentId,
                salary);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    public Employee buildByResultSet(ResultSet resultSet) throws SQLException {
        this.setId(resultSet.getLong("id"));
        this.setFirstName(resultSet.getString("first_name"));
        this.setLastName(resultSet.getString("last_name"));
        this.setBirthdate(LocalDate.from(resultSet.getTimestamp("birthdate").toLocalDateTime()));
        this.setSalary(resultSet.getLong("salary"));
        this.setDepartmentId(resultSet.getLong("department_id"));
        return  this;
    }
}
