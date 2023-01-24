package main.java.jdbc.example.types;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class BlobExample {
    private static final Logger LOG = LoggerFactory.getLogger(BlobExample.class);

    public static void main(String[] args) throws SQLException, IOException {
        try {
            tryBlob();
        } catch (Exception e) {
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
            blob.setBytes(1, Objects.requireNonNull(BlobExample.class.getClassLoader().getResourceAsStream("photo.jpg")).readAllBytes());
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
            var bytes = Objects.requireNonNull(BlobExample.class.getClassLoader().getResourceAsStream("photo.jpg")).readAllBytes();
            preparedStatement.setBytes(1, bytes);
            preparedStatement.executeUpdate();
        }
        LOG.info("Photo has successful saved");
    }
}
