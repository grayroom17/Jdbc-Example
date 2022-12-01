package com.jdbc.example.statement.execute.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;

public class GeneratedKeysExample {
    public static void main(String[] args) throws SQLException {
        insertWithExecuteUpdate();
    }
    public static void insertWithExecuteUpdate() throws SQLException {
        var sql = """
                  insert into employee(last_name, first_name, birthdate,salary, department_id)
                  values
                  ('Shatov', 'Alexander', '1991-01-01', 500000, 2)
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()){
                System.out.println(generatedKeys.getInt("id"));
            }
        }
    }
}
