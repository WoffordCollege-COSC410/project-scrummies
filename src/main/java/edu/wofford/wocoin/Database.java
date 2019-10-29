/**
 * The Database program implements a process for Feature01, specifically it runs the
 * menu for administrators to add a user.
 *
 *The menu gives you two initial options: 1. exit 2. Admin, if Admin is chosen
 * then the correct password must be entered in order to add a new user
 * with a unique username id and password
 *
 * @author Seth Ledford, Carson Vayhinger, Jack Dextraze, and Avery McMillen
 * @since 2019-10-09
 */

package edu.wofford.wocoin;
import gherkin.lexer.Fi;

import java.io.File;
import java.sql.*;
import java.sql.PreparedStatement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class Database {

    private File file;

    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        file = new File(filename);
    }

    public boolean fileExist(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            return true;
        }
        return false;
    }
    public void addUser(String id) {
        int salt = Utilities.generateSalt();
        String hash = Utilities.applySha256(id + salt);
        String url = "jdbc:sqlite:" + file;
        if (file.exists()) {
            if (!checkUser(id)) {
                try (Connection conn = DriverManager.getConnection(url)) {
                    Statement stmt = conn.createStatement();
                    String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
                    PreparedStatement prepStmt = conn.prepareStatement(sqls);
                    prepStmt = conn.prepareStatement(sqls);
                    prepStmt.setString(1, id);
                    prepStmt.setInt(2, salt);
                    //System.out.println("" + id + " " + salt + " " + hash);
                    prepStmt.setString(3, hash);
                    prepStmt.executeUpdate();
                    System.out.println(id + " was added.");
                    prepStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(id + " already exists.");
            }
        } else {
            Utilities.createNewDatabase(url);
        }
    }

    public boolean checkUser(String id1) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id1 + "';");
            rs.next();
            String result = rs.getString(1);
            if (result.equals(id1)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteUser(String user) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("DELETE FROM users WHERE id = '" + user + "';");
            System.out.println("" + user + " was removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
