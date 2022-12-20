package com.jdbc.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {
    private static final String USER = "db.example.user";
    private static final String PASSWORD = "db.example.password";
    private static final String URL = "db.example.url";
    private static final String POOL_SIZE = "db.example.poolsize";
    private static final Integer DEFAULT_POOL_SIZE = 5;
    private static final BlockingQueue<Connection> pool;
    private static final List<Connection> originalConnections;

    static {
        var size = PropertiesUtil.get(POOL_SIZE) == null ? DEFAULT_POOL_SIZE : Integer.parseInt(PropertiesUtil.get(POOL_SIZE));
        pool = new ArrayBlockingQueue<>(size);
        originalConnections = new ArrayList<>(size);

        while (pool.remainingCapacity() != 0) {
            var connection = openConnection();
            var proxyConnection = (Connection) Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            pool.add((Connection) proxy) :
                            method.invoke(connection, args));
            pool.add(proxyConnection);
            originalConnections.add(connection);
        }
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(URL),
                    PropertiesUtil.get(USER),
                    PropertiesUtil.get(PASSWORD));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void closeConnections() {
        originalConnections.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }
}
