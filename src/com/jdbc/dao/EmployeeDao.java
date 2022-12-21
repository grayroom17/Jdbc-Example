package com.jdbc.dao;

public class EmployeeDao {
    private static volatile EmployeeDao instance;

    private EmployeeDao() {
    }

    public static EmployeeDao getDao() {
        if (instance == null) {
            synchronized (EmployeeDao.class) {
                if (instance == null) {
                    instance = new EmployeeDao();
                }
            }
        }
        return instance;
    }
}
