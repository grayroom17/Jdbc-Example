package com.jdbc.example;

import com.jdbc.example.statement.execute.StatementExecuteDdlExamples;
import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;

public class JdbcExample {
    public static void main(String[] args) throws SQLException {
        StatementExecuteDdlExamples.createTable();
    }
}
