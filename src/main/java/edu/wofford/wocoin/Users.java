package edu.wofford.wocoin;

import edu.wofford.wocoin.Utilities;
import org.web3j.abi.datatypes.Int;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;
import java.lang.String;


public class Users {

    //feature 1 allows administrators to add users to the database
    public static void users(String filename) {
        String username = "";
        String password = "";
    }

    public static String[] SaltPassword(String password) {
        String[] result = new String[2];
        String salted_password = "";
        int saltedNum = Utilities.generateSalt();
        salted_password = password + (saltedNum);
        String HashPassword = Utilities.applySha256(salted_password);
        result[0] = Integer.toString(saltedNum);
        result[1] = HashPassword;
        return result;
    }

}
