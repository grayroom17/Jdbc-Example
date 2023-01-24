package main.java.jdbc.example.dml;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class SelectExamples {
    private static final Logger LOG = LoggerFactory.getLogger(SelectExamples.class);

    public static void selectWithExecuteQuery() throws SQLException {
        var sql = """
                  select *
                  from employee;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.executeQuery(sql);
            while (result.next()) {
                LOG.info("{}", result.getInt("id"));
                LOG.info("{}", result.getString("last_name"));
                LOG.info("{}", result.getString("first_name"));
                LOG.info("{}", result.getDate("birthdate"));
                LOG.info("_____");
            }
        }
    }
}
