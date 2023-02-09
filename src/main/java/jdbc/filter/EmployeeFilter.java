package main.java.jdbc.filter;

import java.time.LocalDate;

public class EmployeeFilter {
    private final int limit;
    private final int offset;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthdate;
    private final Long departmentId;
    private final Long salary;

    public EmployeeFilter(int limit,
                          int offset,
                          String firstName,
                          String lastName,
                          LocalDate birthdate,
                          Long departmentId,
                          Long salary) {
        this.limit = limit;
        this.offset = offset;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getSalary() {
        return salary;
    }
}
