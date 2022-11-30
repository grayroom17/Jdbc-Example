package com.jdbc.example;

import com.jdbc.example.statement.execute.dml.DmlExamples;

import java.sql.SQLException;

public class JdbcExample {
    public static void main(String[] args) throws SQLException {
        DmlExamples.deleteWithExecuteUpdate();
    }
}
