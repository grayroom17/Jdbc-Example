package com.jdbc.example.metadata;

import com.jdbc.example.util.ConnectionManager;

import java.sql.SQLException;

public class MetaDataExample {
    public static void main(String[] args) throws SQLException {
        checkMetaData();
    }

    public static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.openConnection()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                var catalog = catalogs.getString("TABLE_CAT");
                System.out.println("DB: " + catalog);
                System.out.println();

                var schemas = metaData.getSchemas(catalog, "public");
                while (schemas.next()) {
                    var schema = schemas.getString("TABLE_SCHEM");
                    System.out.println("Schema: " + schema);
                    var tables = metaData.getTables(catalog,
                            schema,
                            "%",
                            new String[]{"TABLE"});
                    System.out.println();

                    while (tables.next()) {
                        var table = tables.getString("TABLE_NAME");
                        if (table.equals("employee")) {
                            System.out.println("Table: " + table);
                            System.out.println();

                            var columns = metaData.getColumns(catalog,
                                    schema,
                                    table,
                                    null);
                            while (columns.next()) {
                                var columnName = columns.getString("COLUMN_NAME");
                                var typeName = columns.getString("TYPE_NAME");
                                System.out.println("Column: " + columnName + " " + typeName);
                            }
                        }
                    }
                }
            }
        }
    }
}
