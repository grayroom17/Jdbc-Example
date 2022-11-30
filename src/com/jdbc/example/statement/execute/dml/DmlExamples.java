package com.jdbc.example.statement.execute.dml;

import com.jdbc.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DmlExamples {

    private DmlExamples() {
    }

    public static void insertWithExecute() throws SQLException {
        var sql = """
                  insert into employee(first_name,last_name,birthdate)
                  values
                  ('Ivanov', 'Ivan', '1983-10-02'),
                  ('Petrov', 'Petr', '1975-05-10');
                  insert into employee(first_name,last_name,birthdate)
                  values
                  ('Vlasov', 'Stepan', '193-11-17'),
                  ('Pupkin', 'Vladimir', '1998-12-01'),
                  ('Dudkin', 'Sergey', '1993-03-08');
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            var numberOfAddedRows = statement.getUpdateCount();
            System.out.println(result);
            System.out.println("Количество добавленных строк = " + numberOfAddedRows);
        }
    }

    public static void insertWithExecuteUpdate() throws SQLException {
        var sql = """
                  insert into employee(last_name,first_name,birthdate)
                  values
                  ('Ivanov', 'Ivan', '1983-10-02'),
                  ('Petrov', 'Petr', '1975-05-10');
                  insert into employee(last_name,first_name,birthdate)
                  values
                  ('Vlasov', 'Stepan', '193-11-17'),
                  ('Pupkin', 'Vladimir', '1998-12-01'),
                  ('Dudkin', 'Sergey', '1993-03-08');
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeUpdate(sql);
            System.out.println(result);
        }
    }

    public static void updateWithExecuteUpdate() throws SQLException {
        var sql = """
                  update employee
                  set first_name = 'Sergey', last_name = 'Pushkin', birthdate = '1799-05-26'
                  where id = 1;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeUpdate(sql);
            System.out.println(result);
        }
    }

    public static void deleteWithExecuteUpdate() throws SQLException {
        var sql = """
                  delete from employee
                  where id between 2 and 23;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeUpdate(sql);
            System.out.println(result);
        }
    }
}
