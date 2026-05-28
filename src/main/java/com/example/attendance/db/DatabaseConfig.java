package com.example.attendance.db;

import org.springframework.stereotype.Component;
import java.sql.Connection;

@Component
public class DatabaseConfig {
    private static DatabaseConfig instance;
    private Database db;
    private Connection connection;

    public DatabaseConfig() {
        if (instance == null) {
            instance = this;
            initDb();
        }
    }

    private void initDb() {
        try {
            java.nio.file.Path dbFile = java.nio.file.Path.of(System.getProperty("user.home"), "attendance.db");
            String url = "jdbc:sqlite:" + dbFile.toString();
            db = new Database(url);
            db.init();
            connection = db.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Database getDatabase() {
        return db;
    }

    public Connection getConnection() throws java.sql.SQLException {
        if (connection == null || connection.isClosed()) {
            connection = db.getConnection();
        }
        return connection;
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
}
