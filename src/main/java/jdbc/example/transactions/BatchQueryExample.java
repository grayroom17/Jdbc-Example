package main.java.jdbc.example.transactions;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchQueryExample {
    private static final Logger LOG = LoggerFactory.getLogger(BatchQueryExample.class);

    public static void main(String[] args) throws SQLException {
        tryBatchQuery();
    }

    private static void tryBatchQuery() throws SQLException {
        var addEmployees = """
                           insert into employee(last_name,first_name,birthdate,salary,department_id)
                           values
                           ('Ivanov', 'Ivan', '1995-04-27',999999,2),
                           ('Petrushkin', 'Boris', '1996-03-30',777777,2);
                           """;
        var deleteEmployee = """
                                 delete from employee
                                 where last_name = %s
                                 and first_name = %s
                                 and birthdate = %s
                                 and department_id = %d
                             """.formatted("'Ivanov'", "'Ivan'", "'1995-04-27'", 2);
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.openConnection();
            connection.setAutoCommit(false);

            statement = connection.createStatement();

            statement.addBatch(addEmployees);
            statement.addBatch(deleteEmployee);

            var result = statement.executeBatch();

            for (int j : result) {
                LOG.info("{}", j);
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        if (statement != null) {
            statement.close();
        }
    }
}
