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
            while (Still_running) {
                File f = new File(args[0]);
                PreparedStatement prepStmt = null;
                Scanner input = new Scanner(System.in);
                System.out.println("1: exit\n2: Admin\n");
                String answer = input.nextLine();
                if (answer.equals("1")) {
                    // this exits out of the program
                    Still_running = false;
                } else if (answer.equals("2")) {
                    System.out.println("1: back\n2: add user\n");
                    String next_answer = input.nextLine();
                    if (next_answer.equals("1")) {

                    } else if (next_answer.equals("2")) {
                        System.out.println("Enter a id");
                        String id = input.nextLine();
                        System.out.println("Enter a password");
                        String password = input.nextLine();
                        Users.SaltPassword(id, password);
                        int int_salt = Utilities.generateSalt();
                        String salt = Integer.toString(int_salt);
                        String hash = "" + Utilities.applySha256(password) + salt;
                        if (f.exists()) {
                            System.out.println(f);
                            try (Connection conn = DriverManager.getConnection(url)) {
                                System.out.println("Past the try");
                                Statement stmt = conn.createStatement();
                                String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
                                prepStmt = conn.prepareStatement(sqls);
                                prepStmt.setString(1, id);
                                prepStmt.setInt(2, int_salt);
                                System.out.println("" + id + " " + int_salt + " " + hash);
                                prepStmt.setString(3, hash);
                                prepStmt.executeUpdate();
                                prepStmt.close();
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
}
