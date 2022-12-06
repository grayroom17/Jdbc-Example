package com.jdbc.example.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;

public class SqlInjectionsExample {
    public static void main(String[] args) throws SQLException {
        getEmployeesByDepartment("2 OR 1=1");
    }

    public static void getEmployeesByDepartment(String departmentId) throws SQLException {
        var sql = """
                  select id
                  from employee
                  where department_id = %s;
                  """.formatted(departmentId);
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
            }
        }
    }
}
