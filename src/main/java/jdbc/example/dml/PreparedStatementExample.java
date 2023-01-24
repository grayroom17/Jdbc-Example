package main.java.jdbc.example.dml;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementExample {
    private static final Logger LOG = LoggerFactory.getLogger(PreparedStatementExample.class);

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
            LOG.info("{}", preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(endDate));
            LOG.info("{}", preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("id"));
            }
        }
        LOG.info("{}", result);
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
            LOG.info("{}", preparedStatement);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt("id"));
            }
        }
        LOG.info("{}", result);
    }
}
