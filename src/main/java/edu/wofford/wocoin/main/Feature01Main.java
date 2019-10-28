package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;

import java.util.Scanner;

public class Feature01Main {
    //main file for Feature01
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean Still_running = true;
        if (args.length > 0) {
            while (Still_running) {
                System.out.println("1: exit\n2: Admin\n");
                String answer = input.nextLine();

                if (answer.equals("1")) {
                    Still_running = false;
                } else {
                    System.out.println("Enter Password");
                    String Pass = input.nextLine();

                    if (Pass.equals("adminpwd")){
                        System.out.println("1: back\n2: add user\n");
                        String next_step = input.nextLine();
                        if (next_step.equals("2")) {
                            System.out.println("Enter ID");
                            String response = input.nextLine();
                            Database d = new Database(args[0]);
                            d.addUser(response);
                        }
                    } else {
                        System.out.println("Incorrect administrator password.");
                    }

                }

            }
        }
    }
}
