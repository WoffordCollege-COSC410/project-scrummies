package edu.wofford.wocoin;

import static org.junit.Assert.*;
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

    public void testOpenExistingDatabase() {
        Database d = new Database("test/resources/test.db");
    }
}
