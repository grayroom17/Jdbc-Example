package com.jdbc.example.util;

import com.jdbc.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    public static final String URL = "db.example.url";
    public static final String USER = "db.example.user";
    public static final String PASSWORD = "db.example.password";

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
