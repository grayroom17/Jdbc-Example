package main.java.jdbc.example.dml;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Statement;

public class GeneratedKeysExample {
    private static final Logger LOG = LoggerFactory.getLogger(GeneratedKeysExample.class);

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
            if (generatedKeys.next()) {
                LOG.info("{}", generatedKeys.getInt("id"));
            }
        }
    }
}
