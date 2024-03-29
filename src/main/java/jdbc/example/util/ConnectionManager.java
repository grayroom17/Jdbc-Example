package main.java.jdbc.example.util;

import main.java.jdbc.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    private static final String URL = "db.example.url";
    private static final String USER = "db.example.user";
    private static final String PASSWORD = "db.example.password";

    private ConnectionManager() {
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL),
                    PropertiesUtil.get(USER),
                    PropertiesUtil.get(PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
