package com.jdbc.example.ddl;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;

public class DdlExamples {

    private DdlExamples() {
    }

    public static void createTable() throws SQLException {
        String sql = """
                     create table if not exists employee(
                     id serial primary key,
                     first_name varchar,
                     last_name varchar,
                     birthdate date)
                     """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            System.out.println(result);
        }
    }

    public static void createDatabase() throws SQLException {
        var sql = """
                  create database example_database;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            System.out.println(result);
        }
    }

    public static void createSchema() throws SQLException {
        var sql = """
                  create schema if not exists example_schema;
                  """;
        try (var connection = ConnectionManager.openConnection();
             var statement = connection.createStatement()) {
            var result = statement.execute(sql);
            System.out.println(result);
        }
    }
}
