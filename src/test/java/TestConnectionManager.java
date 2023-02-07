package test.java;

import main.java.jdbc.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionManager {
    private static final String URL = "db.test.url";
    private static final String USER = "db.test.user";
    private static final String PASSWORD = "db.test.password";

    private TestConnectionManager() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL),
                    PropertiesUtil.get(USER),
                    PropertiesUtil.get(PASSWORD));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
