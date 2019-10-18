package edu.wofford.wocoin;
import static org.junit.Assert.*;

import edu.wofford.wocoin.main.Feature01Main;
import org.junit.Test;
import org.junit.Before;

import java.sql.*;

public class UsersTest {

    @Test
    public void Feature01MainTest(){
        //first test should be to determine if database exists
        //String databaseName = Feature01Main;
        Feature01Main tester = new Feature01Main();

    }
    /*
    @Test
    public void InputTest() {
        String[] Salt_Hash = Users.SaltPassword("test");
        int int_salt = Integer.parseInt(Salt_Hash[0]);
        String hash = Salt_Hash[1];
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
            PreparedStatement prepStmt = conn.prepareStatement(sqls);
            prepStmt.setString(1, id);
            prepStmt.setInt(2, int_salt);
            System.out.println("" + id + " " + int_salt + " " + hash);
            prepStmt.setString(3, hash);
            prepStmt.executeUpdate();

            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    */
}

