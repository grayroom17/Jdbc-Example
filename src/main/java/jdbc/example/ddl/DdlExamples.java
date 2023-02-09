package main.java.jdbc.example.ddl;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DdlExamples {

    private static final Logger LOG = LoggerFactory.getLogger(DdlExamples.class);

    private DdlExamples() {
    }

    public static void createTable() throws SQLException {
        String sql = """
                     create table employee
                                     (
                                         id            serial
                                             constraint employee_pkey
                                                 primary key,
                                         first_name    varchar,
                                         last_name     varchar,
                                         birthdate     date,
                                         department_id integer
                                             constraint fk_employee_department
                                                 references department,
                                         salary        integer,
                                         photo         bytea
                                     );
                      """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            LOG.info("Result : {}", result);
        }
    }

    public static void createDatabase() throws SQLException {
        var sql = """
                  create database example_database;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            LOG.info("Result : {}", result);
        }
    }

    public static void createSchema() throws SQLException {
        var sql = """
                  create schema if not exists example_schema;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            LOG.info("Result : {}", result);
        }
    }
}
