package main.java.jdbc.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Department department;
    private Long salary;
    private byte[] photo;

    public Employee() {
    }

    @SuppressWarnings("unused")
    public Employee(Long id,
                    String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Department department,
                    Long salary,
                    byte[] photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.department = department;
        this.salary = salary;
        this.photo = photo;
    }

    @SuppressWarnings("unused")
    public Employee(Long id,
                    String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Department department,
                    Long salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.department = department;
        this.salary = salary;
    }

    public Employee(String firstName,
                    String lastName,
                    LocalDate birthdate,
                    Department department,
                    Long salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @SuppressWarnings("unused")
    public byte[] getPhoto() {
        return photo;
    }

    @SuppressWarnings("unused")
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
               ", department=" + department +
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
               && department.equals(employee.department)
               && salary.equals(employee.salary)
               && Arrays.equals(photo, employee.photo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id,
                firstName,
                lastName,
                birthdate,
                department,
                salary);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
