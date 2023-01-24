package main.java.jdbc.example.transactions;

import main.java.jdbc.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionExample {
    public static void main(String[] args) throws SQLException {
        tryTransaction(2);
    }

    private static void tryTransaction(Integer departmentId) throws SQLException {
        var deleteDepartmentSql = """
                                      delete from department
                                      where id = ?;
                                  """;
        var deleteEmployeeSql = """
                                    delete from employee
                                    where department_id = ?;
                                """;
        Connection connection = null;
        PreparedStatement deleteDepartmentPreparedStatement = null;
        PreparedStatement deleteEmployeePreparedStatement = null;
        try {
            connection = ConnectionManager.openConnection();
            deleteDepartmentPreparedStatement = connection.prepareStatement(deleteDepartmentSql);
            deleteEmployeePreparedStatement = connection.prepareStatement(deleteEmployeeSql);

            connection.setAutoCommit(false);

            deleteEmployeePreparedStatement.setInt(1, departmentId);
            deleteDepartmentPreparedStatement.setInt(1, departmentId);

            deleteEmployeePreparedStatement.executeUpdate();

            if (true) {
                throw new RuntimeException();
            }

            deleteDepartmentPreparedStatement.executeUpdate();

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
                throw e;
            }
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        if (deleteDepartmentPreparedStatement != null) {
            deleteDepartmentPreparedStatement.close();
        }
        if (deleteEmployeePreparedStatement != null) {
            deleteEmployeePreparedStatement.close();
        }
    }
}
