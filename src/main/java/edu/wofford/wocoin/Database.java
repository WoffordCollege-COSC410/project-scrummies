package edu.wofford.wocoin;
import gherkin.lexer.Fi;

import java.io.File;
import java.sql.*;
import java.sql.PreparedStatement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class Database {
   


    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        File file = new File(filename);
    }
    public boolean FileExist(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            return true;
        }
        return false;
    }
    public void AddUser(String filename, String id){
        Database db= new Database("filename");
        int salt = Utilities.generateSalt();

        String hash = Utilities.applySha256(id + salt);

        try (Connection conn = DriverManager.getConnection(filename)) {
            Statement stmt = conn.createStatement();
            String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
            PreparedStatement prepStmt = conn.prepareStatement(sqls);
            prepStmt = conn.prepareStatement(sqls);
            prepStmt.setString(1, id);
            prepStmt.setInt(2, salt);
            System.out.println("" + id + " " + salt + " " + hash);
            prepStmt.setString(3, hash);
            prepStmt.executeUpdate();

            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}