package edu.wofford.wocoin;

import static org.junit.Assert.*;

import gherkin.lexer.Fi;
import org.junit.Test;
import java.io.File;
import java.sql.*;



public class DatabaseTest {
    @Test
    public void testCreateNewDatabase() {
        File dbfile = new File("junk.db");
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Database d = new Database("junk.db");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:junk.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';");
            assertNotNull(rs.next());
            assertEquals("users", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("wallets", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("products", rs.getString(2));
            assertNotNull(rs.next());
            assertEquals("messages", rs.getString(2));
            //assertNull(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testOpenExistingDatabase() {
        //make a private FileExist function so that the "client" wouldn't have to see it
        //Dr. Garrett asked us to do that^^
       Database db= new Database("src/test/resource/testdb.db");
       assertEquals(true,db.FileExist("testdb.db"));
       assertEquals(false,db.FileExist("false.db"));
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/test/resources/testdb.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = \"jdoe\";");
            assertNotNull(rs.next());
            assertEquals("jdoe", rs.getString(1));
            assertEquals("13587", rs.getString(2));
            assertEquals("ebd3528832b124bb7886cd8e8d42871c99e06d5f3ad0c6ee883f6219b2b6a955", rs.getString(3));
            //assertNull(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testAddExistingUser() {
        Database db = new Database("src/test/resources/testdb.db");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/test/resources/testdb.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = \"jdoe\";");
            assertNotNull(rs.next());
            assertEquals("jdoe", rs.getString(1));
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
