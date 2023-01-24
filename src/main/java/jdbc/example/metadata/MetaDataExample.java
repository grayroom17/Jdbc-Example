package main.java.jdbc.example.metadata;

import main.java.jdbc.example.util.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class MetaDataExample {
    private static final Logger LOG = LoggerFactory.getLogger(MetaDataExample.class);

    public static void main(String[] args) throws SQLException {
        checkMetaData();
    }

    public static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.openConnection()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString("TABLE_CAT");
                LOG.info("DB: {}", catalog);
                LOG.info("");

                var schemas = metaData.getSchemas(catalog, "public");
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    LOG.info("Schema: {}", schema);
                    var tables = metaData.getTables(catalog,
                            schema,
                            "%",
                            new String[]{"TABLE"});
                    LOG.info("");

                    while (tables.next()) {
                        var table = tables.getString("TABLE_NAME");
                        if (table.equals("employee")) {
                            LOG.info("Table: {}", table);
                            LOG.info("");

                            var columns = metaData.getColumns(catalog,
                                    schema,
                                    table,
                                    null);
                            while (columns.next()) {
                                var columnName = columns.getString("COLUMN_NAME");
                                var typeName = columns.getString("TYPE_NAME");
                                LOG.info("Column: {} {}", columnName, typeName);
                            }
                        }
                    }
                }
            }
        }
    }
}
