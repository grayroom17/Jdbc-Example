package main.java.jdbc.example.dml;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class SqlInjectionsExample {
    private static final Logger LOG = LoggerFactory.getLogger(SqlInjectionsExample.class);

    public static void main(String[] args) throws SQLException {
        getEmployeesByDepartment("2 OR 1=1");
    }

    public static void getEmployeesByDepartment(String departmentId) throws SQLException {
        var sql = """
                  select id
                  from employee
                  where department_id = %s;
                  """.formatted(departmentId);
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                LOG.info("{}", resultSet.getInt("id"));
            }
        }
    }
}
