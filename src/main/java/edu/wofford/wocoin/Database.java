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

    /**
     *Creates a new database and sets it to name, as well as creates file
     * @param filename refers to name that database is set to
     */
    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        file = new File(filename);
    }

    /**
     *Checks if file exists or not
     * @param filename refers to name of database that is being checked
     * @return true if file does exist, false if it doesn't
     */
    public boolean fileExist(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            return true;
        }
        return false;
    }

    /**
     *This method adds a new user after the admin password has been properly entered.
     * It then takes the user id and salts and hashes it and stores the hashed password.
     * If a new user is added, the user is told the id was added, if the id already exists,
     * an "already exists" comment is thrown.
     * @param id entered user id
     */
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

    /**
     *This method sees if the given user id already exists and returns the results to be used in "addUser"
     * @param id1 entered user id
     * @return true if id already exists, otherwise returns false
     */
    public boolean checkUser(String id1) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id1 + "';");
            rs.next();
            String result = rs.getString(1);
            if (result.equals(id1)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *This method removes an id if the admin properly enters the admin password and gives an id that
     * is being stored. If a user id that is given does exist, it is removed and the user is told that,
     * if the given id does not exist, a "does not exist" comment is thrown.
     * @param user the referred to user id to be removed
     */
    public void deleteUser(String user) {
        String url = "jdbc:sqlite:" + file;
        if (checkUser(user)) {
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE id = '" + user + "';");
                System.out.println(user + " was removed.");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println(user + " does not exist.");
        }
    }

}
