package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Wallet;

import java.util.Scanner;

public class Feature03Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean still_Running = true;
        boolean password_Correct;
        boolean userPasswordCorrect;
        if (args.length > 0) {
            while (still_Running) {
                System.out.println("1: exit\n2: Admin\n3: User\n");
                String answer = input.nextLine();

                if (answer.equals("1")) {
                    still_Running = false;
                } else if (answer.equals("2")) {
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
                                System.out.println("Enter a password:");
                                String password = input.nextLine();

                                d.addUser(response, password);
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

                } else {
                    Database d = new Database(args[0]);

                    System.out.println("Enter User");
                    String user = input.nextLine();

                    System.out.println("Enter users password");
                    String password = input.nextLine();

                    if (d.checkUserPassword(user, password)) {
                        userPasswordCorrect = true;

                        while (userPasswordCorrect) {
                            System.out.println("1: back\n2: create wallet\n");
                            String next_answer = input.nextLine();
                            ;
                            if (next_answer.equals("1")) {
                                userPasswordCorrect = false;
                            } else if (next_answer.equals("2")) {
                                //need to create wallet
                                System.out.println("Enter a directory: ");
                                String dir = input.nextLine();
                                String publicKey = Wallet.createWallet(dir, user, password);
                                d.addWallet(user, publicKey);
                            }
                        }
                    }
                }

            }
        }
    }
}
