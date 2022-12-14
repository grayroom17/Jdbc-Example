package com.jdbc.example.types;

import com.jdbc.example.util.ConnectionManager;

import java.io.IOException;
import java.sql.SQLException;

public class BlobExample {
    public static void main(String[] args) throws SQLException, IOException {
        try {
        tryBlob();
        } catch (Exception e){
            e.printStackTrace();
        }
        tryBytea();
    }

    private static void tryBlob() throws SQLException, IOException {
        var sql = """
                  update employee
                  set photo = ?
                  where first_name like 'Ivan'
                  and last_name like 'Ivanovich';
                  """;
        try (
                var connection = ConnectionManager.openConnection();
                var preparedStatement = connection.prepareStatement(sql)) {
            var blob = connection.createBlob();
            blob.setBytes(1, BlobExample.class.getClassLoader().getResourceAsStream("photo.jpg").readAllBytes());
            preparedStatement.setBlob(1, blob);
            preparedStatement.executeUpdate();
        }
    }

    private static void tryBytea() throws SQLException, IOException {
        var sql = """
                  update employee
                  set photo = ?
                  where first_name like 'Ivan'
                  and last_name like 'Ivanovich';
                  """;
        try (
                var connection = ConnectionManager.openConnection();
                var preparedStatement = connection.prepareStatement(sql)) {
            var bytes = BlobExample.class.getClassLoader().getResourceAsStream("photo.jpg").readAllBytes();
            preparedStatement.setBytes(1, bytes);
            preparedStatement.executeUpdate();
        }
        System.out.println("Photo has successful saved");
    }
}
