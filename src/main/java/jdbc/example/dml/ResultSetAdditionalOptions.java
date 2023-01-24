package main.java.jdbc.example.dml;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetAdditionalOptions {
    private static final Logger LOG = LoggerFactory.getLogger(ResultSetAdditionalOptions.class);

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
        LOG.info("{} {} {}",
                result.getString("last_name"),
                result.getString("first_name"),
                result.getDate("birthdate"));
        LOG.info("_____");
    }
}
