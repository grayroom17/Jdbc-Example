package com.jdbc.example;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;

public class JdbcExample {
    public static void main(String[] args) throws SQLException {
        try (var connection = ConnectionManager.openConnection()) {
            System.out.println(connection.getTransactionIsolation());
        }
    }
}
