package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Users;
import edu.wofford.wocoin.Utilities;
import java.util.Scanner;

public class Feature01Main {
    //main file for Feature01
    public static void main(String[] args) {
        if (args.length > 0) {
            Utilities.createNewDatabase(args[0]);
            Scanner input = new Scanner(System.in);
            System.out.println("1: exit\n2: Admin\n");
            String answer = input.nextLine();
            if (answer.equals("1")) {
                // this exits out of the program
            } else if (answer.equals("2")) {
                System.out.println("1: back\n2: add user\n");
                String next_answer = input.nextLine();
                if (next_answer.equals("1")) {
                    //TODO
                    /* Go back to step 1 */
                } else if (next_answer.equals("2")) {
                    System.out.println("Enter a user");
                    String user = input.nextLine();
                    Users.AddUser(user);
                }
            }
        } else {
            System.out.println("You must specify the database filepath as a command-line argument.");
        }
    }
}
