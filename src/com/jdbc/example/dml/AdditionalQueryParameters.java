package com.jdbc.example.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AdditionalQueryParameters {
    public static void main(String[] args) throws SQLException {
        getEmployeeIdsByBirthdateBetween(LocalDate.of(1000, 1, 1).atStartOfDay(), LocalDateTime.now());
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
            preparedStatement.setFetchSize(1);
            preparedStatement.setQueryTimeout(60);
            preparedStatement.setMaxRows(5);

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
}
