package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Users;
import edu.wofford.wocoin.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;

public class Feature01Main {
    //main file for Feature01
    public static void main(String[] args) {
        boolean Still_running = true;
        if (args.length > 0) {
            while (Still_running == true) {
                String url = "jdbc:sqlite:" + args[0];

                File f = new File(args[0]);

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
                        //TODO
                        /* Go back to step 1 */
                    } else if (next_answer.equals("2")) {
                        System.out.println("Enter a user");
                        String user = input.nextLine();
                        String password = input.nextLine();
                        Users.AddUser(user, password);
                        String[] sqls = {"INSERT INTO users (id, salt, hash) VALUES (\"\", 13587, \"ebd3528832b124bb7886cd8e8d42871c99e06d5f3ad0c6ee883f6219b2b6a955\")"};
                        if (f.exists()) {
                            try (Connection conn = DriverManager.getConnection(url)) {
                                for (String sql : sqls) {
                                    Statement stmt = conn.createStatement();
                                    stmt.executeUpdate(sql);
                                    stmt.close();
                                    // Wait for one second so that timestamps are different.
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
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
