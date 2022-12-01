package com.jdbc.example.statement.execute.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectExamples {
    public static void selectWithExecuteQuery() throws SQLException {
        var sql = """
                  select *
                  from employee;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeQuery(sql);
            while (result.next()){
                System.out.println(result.getInt("id"));
                System.out.println(result.getString("last_name"));
                System.out.println(result.getString("first_name"));
                System.out.println(result.getDate("birthdate"));
                System.out.println("_____");
            }
        }
    }
}
