package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Wallet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;

public class Feature09Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean still_Running = true;
        boolean password_Correct;
        boolean user_Password_Correct;
        boolean nameCorrect;
        boolean descriptionCorrect;
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
                                System.out.println("Enter a password:");
                                String password = input.nextLine();
                                Database d = new Database(args[0]);
                                d.addUser(response, password);
                            }else if (next_step.equals("3")){
                                System.out.println("Enter ID");
                                String response = input.nextLine();
                                Database d = new Database(args[0]);
                                d.deleteUser(response);
                            } else if (next_step.equals("4")) {
                                Database db = new Database(args[0]);
                                System.out.println("Enter users username");
                                String response = input.nextLine();
                                if (db.checkUser(response)) {
                                    if (db.checkWallet(response)) {
                                        //wallet does exist
                                        //TODO transfer WoCoins
                                    } else {
                                        System.out.println("User has no wallet.");
                                    }
                                } else {
                                    System.out.println("No such user.");
                                }
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
                        user_Password_Correct = true;

                        while (user_Password_Correct) {
                            System.out.println("1: back\n2: create wallet\n" +
                                    "3: add product\n4: remove product\n5: display products\n" +
                                    "6: send message\n7: check messages\n8: check balance");
                            String next_answer = input.nextLine();
                            if (next_answer.equals("1")) {
                                user_Password_Correct = false;
                            } else if (next_answer.equals("2")){
                                if (d.checkWallet(user)) {
                                    System.out.println("Enter Y or N");
                                    String yesOrNo = input.nextLine();
                                    if (yesOrNo.equals("Y") || yesOrNo.equals("y")) {
                                        System.out.println("Enter a directory: ");
                                        String dir = input.nextLine();
                                        if (new File(dir + File.separator + user).exists()) {
                                            try {
                                                FileUtils.deleteDirectory(new File(dir + File.separator + user));
                                                d.deleteWallet(user);
                                                String publicKey = Wallet.createWallet(dir, user, password);
                                                d.addWallet(user, publicKey);
                                            } catch (IOException ex) {
                                                System.out.println("IOException");
                                            }
                                        } else {
                                            String publicKey = Wallet.createWallet(dir, user, password);
                                            d.addWallet(user, publicKey);
                                        }
                                    } else {
                                        System.out.println("Action canceled.");
                                    }
                                } else {
                                    System.out.println("Enter a directory: ");
                                    String dir = input.nextLine();
                                    String publicKey = Wallet.createWallet(dir, user, password);
                                    d.addWallet(user, publicKey);
                                }
                            } else if (next_answer.equals("3")){
                                nameCorrect = true;
                                descriptionCorrect = true;
                                if (d.checkWallet(user)) {
                                    while (nameCorrect) {
                                        System.out.println("Enter a name: ");
                                        String name = input.nextLine();
                                        if (name.equals("")) {
                                            System.out.println("Invalid value.");
                                            System.out.println("Expected a string with at least 1 character.");
                                        } else {
                                            while (descriptionCorrect) {
                                                System.out.println("Enter a description: ");
                                                String description = input.nextLine();
                                                if (description.equals("")) {
                                                    System.out.println("Invalid value.");
                                                    System.out.println("Expected a string with at least 1 character.");
                                                } else {
                                                    System.out.println("Enter a price: ");
                                                    int price = input.nextInt();
                                                    input.nextLine();
                                                    if (price == 0) {
                                                        System.out.println("Invalid value.");
                                                        System.out.println("Expected an integer value greater than or equal to 1.");
                                                    } else {
                                                        d.addProduct(user, name, description,price);
                                                        descriptionCorrect = false;
                                                        nameCorrect = false;
                                                    }
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    System.out.println("User has no wallet.");
                                }

                            } else if (next_answer.equals("4")) {
                                //TODO Feature05 -> Allow users to remove products that they have added to the database

                            } else if (next_answer.equals("5")) {
                                String productString = d.turnProductToString(user);
                                System.out.println(productString);
                            }
                            else if (next_answer.equals("6")) {
                                //TODO Feature07 -> send messages to other users
                            }
                            else if (next_answer.equals("7")) {
                                //TODO Feature07 -> check messages from other users
                            }
                            else if (next_answer.equals("8")) {
                                //TODO check if user has wallet
                                if (d.checkWallet(user)) {
                                    //TODO return WoCoin balance if wallet exists
                                        //return "User has X WoCoins"
                                }
                                else{
                                    System.out.println("User has no wallet.");
                                }

                            }
                        }
                    }
                }

            }
        }
    }
}
