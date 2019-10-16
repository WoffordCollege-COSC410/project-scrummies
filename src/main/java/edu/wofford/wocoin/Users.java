package edu.wofford.wocoin;

import edu.wofford.wocoin.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {
    //feature 1 allows administrators to add users to the database
    public static void AddUser(String filename, String user) {
        String url = "jdbc:sqlite:" + filename;
        String[] sqls = {""};

        try (Connection conn = DriverManager.getConnection(url)) {
            for (String sql : sqls) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                // Wait for one second so that timestamps are different.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
