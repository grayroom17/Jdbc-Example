package com.jdbc.example.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementExample {
    public static void main(String[] args) throws SQLException {
//        getEmployeeIdsByBirthdateBetween(LocalDate.of(1990, 1, 1).atStartOfDay(), LocalDateTime.now());
        getEmployeeIdsByName("Sergey or 1=1");
    }

    public static void getEmployeeIdsByBirthdateBetween(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        var sql = """
                  select id
                  from employee
                  where birthdate between ? and ?;
                  """;
        List<Integer> result = new ArrayList<>();

        try (var connection = ConnectionManager.openConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(startDate));
            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("id"));
            }
        }
        System.out.println(result);
    }

    public static void getEmployeeIdsByName(String name) throws SQLException {
        var sql = """
                  select id
                  from employee
                  where first_name = ?;
                  """;
        List<Integer> result = new ArrayList<>();

        try (var connection = ConnectionManager.openConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("id"));
            }
        }
        System.out.println(result);
    }
}
