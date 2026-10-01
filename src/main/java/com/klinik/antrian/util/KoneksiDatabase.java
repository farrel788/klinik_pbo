package com.klinik.antrian.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL      = "jdbc:mysql://localhost:3306/klinik_antrian";
    private static final String USER     = "root";
    private static final String PASSWORD = "";

    private static KoneksiDatabase instance;
    private Connection connection;

    private KoneksiDatabase() {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL tidak ditemukan!");
        }
    }

    public static synchronized KoneksiDatabase getInstance() {
        if (instance == null) instance = new KoneksiDatabase();
        return instance;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed())
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }

    public void tutupKoneksi() {
        try { if (connection != null && !connection.isClosed()) connection.close(); }
        catch (SQLException e) { System.err.println("Gagal tutup koneksi: " + e.getMessage()); }
    }
}