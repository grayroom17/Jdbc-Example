package com.jdbc.example.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetAdditionalOptions {
    public static void selectWithExecuteUpdate() throws SQLException {
        var sql = """
                  select *
                  from employee;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            var result = statement.executeQuery(sql);
            result.next();
            printResult(result);
            result.next();
            printResult(result);
            result.previous();
            printResult(result);
            result.last();
            printResult(result);
            result.updateString("first_name", "Bulkin");
            result.updateString("last_name", "Genadiy");
            result.updateRow();
            printResult(result);
            result.first();
            printResult(result);
        }
    }

    private static void printResult(ResultSet result) throws SQLException {
        System.out.println(result.getString("last_name") + " " +
                           result.getString("first_name") + " " +
                           result.getDate("birthdate"));
        System.out.println("_____");
    }
}
