package com.pao.laboratory12.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws IOException, SQLException {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream("src/com/pao/laboratory12/resources/db.properties")) {
            if (is == null) {
                throw new IOException("Nu gasesc db.properties in resources/");
            }
            props.load(is);
        }
        String url  = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String pass = props.getProperty("db.password");

        this.connection = DriverManager.getConnection(url, user, pass);

        try (var stmt = connection.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        } catch (SQLException ignored) {
        }
    }

    public static synchronized DatabaseConnection getInstance()
            throws IOException, SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
    public void initSchema() throws SQLException {
        try (var stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS loan;");
            stmt.executeUpdate("DROP TABLE IF EXISTS book;");
            stmt.executeUpdate("DROP TABLE IF EXISTS reader;");
            stmt.executeUpdate("DROP TABLE IF EXISTS author;");

            stmt.executeUpdate("CREATE TABLE author (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200) NOT NULL, country VARCHAR(100));");
            stmt.executeUpdate("CREATE TABLE book (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(300) NOT NULL, author_id INTEGER NOT NULL, available INTEGER NOT NULL DEFAULT 1, FOREIGN KEY (author_id) REFERENCES author(id));");
            stmt.executeUpdate("CREATE TABLE reader (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200) NOT NULL, email VARCHAR(200));");
            stmt.executeUpdate("CREATE TABLE loan (id INTEGER PRIMARY KEY AUTOINCREMENT, book_id INTEGER NOT NULL, reader_id INTEGER NOT NULL, loan_date VARCHAR(20) NOT NULL, return_date VARCHAR(20), FOREIGN KEY (book_id) REFERENCES book(id), FOREIGN KEY (reader_id) REFERENCES reader(id));");
        }
    }

    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}