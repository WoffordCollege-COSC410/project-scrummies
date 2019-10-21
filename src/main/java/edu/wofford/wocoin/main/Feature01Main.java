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
            while (Still_running) {
                Scanner input = new Scanner(System.in);
                System.out.println("1: exit\n2: Admin\n");
                String answer = input.nextLine();

                if (answer.equals("1")) {
                    Still_running = false;
                } else {
                    System.out.println("Enter Password");
                    String Pass = input.nextLine();

                    if (Pass.equals("adminpwd")){

                    } else {
                        System.out.println("Incorrect administrator password.");
                    }

                }

            }
        }
    }
}
