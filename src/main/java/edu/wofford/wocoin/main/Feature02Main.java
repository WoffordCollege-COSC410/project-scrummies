package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;

import java.util.Scanner;

public class Feature02Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean still_Running = true;
        boolean password_Correct = false;
        if (args.length > 0) {
            while (still_Running) {
                System.out.println("1: exit\n2: Admin\n");
                String answer = input.nextLine();

                if (answer.equals("1")) {
                    still_Running = false;
                } else {
                    System.out.println("Enter Password");
                    String Pass = input.nextLine();

                    if (Pass.equals("adminpwd")){
                        password_Correct = true;
                        while (password_Correct) {
                            System.out.println("1: back\n2: add user\n3: remove user\n");
                            String next_step = input.nextLine();
                            if (next_step .equals("1")) {
                                password_Correct = false;
                            }else if (next_step.equals("2")) {
                                System.out.println("Enter ID");
                                String response = input.nextLine();
                                Database d = new Database(args[0]);
                                d.addUser(response);
                            }else if (next_step.equals("3")){
                                System.out.println("Enter ID");
                                String response = input.nextLine();
                                Database d = new Database(args[0]);
                                d.deleteUser(response);
                            }
                        }

                    } else {
                        System.out.println("Incorrect administrator password.");
                    }

                }

            }
        }
    }
}
