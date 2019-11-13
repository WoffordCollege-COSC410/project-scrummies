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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testOpenExistingDatabase() {
        //make a private FileExist function so that the "client" wouldn't have to see it
        //Dr. Garrett asked us to do that^^
       Database db= new Database("src/test/resource/testdb.db");
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
        File dbfile = new File("src/test/resources/testdb.db");
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Database db = new Database("src/test/resources/testdb.db");

        db.addUser("Carson", "stinks");
        db.addUser("Carson", "stinks");
        assertEquals(true, db.checkUser("Carson"));

        assertEquals(false, db.checkUser("shouldbefalse"));
        db.addUser("shouldbefalse", "shouldbefalse");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/test/resources/testdb.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = \"jdoe\";");
            assertNotNull(rs.next());
            assertEquals("jdoe", rs.getString(1));
            System.out.println(rs.getString(1));

            Statement stmt2 = conn.createStatement();
            ResultSet user = stmt2.executeQuery(("SELECT id FROM users WHERE id = \"Seth\";"));
            assertNotNull(user.next());
            assertEquals("Seth", user.getString(1));
            System.out.println(user.getString(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteUser() {
        File dbfile = new File("src/test/resources/testdb.db");
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Database db = new Database("src/test/resources/testdb.db");
        db.addUser("Seth", "Seth");
        assertEquals(true, db.checkUser("Seth"));

        db.deleteUser("Seth");
        assertEquals(false, db.checkUser("Seth"));

        assertEquals(false, db.checkUser("Drew"));
        db.deleteUser("Drew");

    }
    @Test
    public void checkUserPassword() {
        File dbfile = new File("src/test/resources/testdb.db");
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Database db = new Database("src/test/resources/testdb.db");
        db.addUser("Seth", "Seth");
        assertEquals(true, db.checkUserPassword("Seth", "Seth"));
        db.checkUserPassword("Elise", "Elise");
    }
    @Test
    public void checkProducts() {
        File dbfile = new File("testForProducts.db");
        if (dbfile.exists()) {
            dbfile.delete();
        }
        Database db = new Database("testForProducts.db");
        db.addUser("Seth", "Seth");
        db.addWallet("Seth", Wallet.createWallet("C:\\Users\\sethl\\project-scrummies","Seth","Seth"));


        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/test/resources/testdb.db")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(("SELECT publickey FROM wallets WHERE id = \"Seth\";"));
            assertNotNull(rs.next());
            String publickey = rs.getString(1);
            assertEquals("a3021253e3589a0ba79afbd48567512acbfb1b9", publickey);
            db.addProduct(publickey,"NeverWinter","Game on PC",50);

            Statement stmt2 = conn.createStatement();
            ResultSet user = stmt2.executeQuery(("SELECT name FROM products WHERE name = \"NeverWinter\";"));
            assertNotNull(user.next());
            String name = user.getString(1);
            assertEquals("NeverWinter", name);
            System.out.println(user.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
