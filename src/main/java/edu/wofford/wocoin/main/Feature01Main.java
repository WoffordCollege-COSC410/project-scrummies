package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Users;
import edu.wofford.wocoin.Utilities;
import org.web3j.abi.datatypes.Int;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;
import java.sql.PreparedStatement;

public class Feature01Main {
    //main file for Feature01
    public static void main(String[] args) {
        boolean Still_running = true;
        if (args.length > 0) {
            String url = "jdbc:sqlite:" + args[0];

            File f = new File(args[0]);

            Scanner input = new Scanner(System.in);
            System.out.println("1: exit\n2: Admin\n");
            String answer = input.nextLine();
            if (answer.equals("1")) {
                // this exits out of the program
            } else if (answer.equals("2")) {
                System.out.println("1: back\n2: add user\n");
                String next_answer = input.nextLine();
                if (next_answer.equals("1")) {

                } else if (next_answer.equals("2")) {
                    System.out.println("Enter a user");
                    String user = input.nextLine();
                    String password = input.nextLine();
                    Users.SaltPassword(user, password);
                    int int_salt = Utilities.generateSalt();
                    String salt = Integer.toString(int_salt);
                    String hash = "" + Utilities.applySha256(password) + salt;



                    if (f.exists()) {
                        try (Connection conn = DriverManager.getConnection(url)) {
                            String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
                            prepStmt = conn.prepareStatement(sqls);
                            prepStmt.setString(1, "user");
                            prepStmt.setString(2, "salt");
                            prepStmt.setString(3, "hash");
                            stmt.executeUpdate(sqls);
                            stmt.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utilities.createNewDatabase(args[0]);
                    }
                } else {
                    System.out.println("You must specify the database filepath as a command-line argument.");
                }
            }


        }
    }
}
