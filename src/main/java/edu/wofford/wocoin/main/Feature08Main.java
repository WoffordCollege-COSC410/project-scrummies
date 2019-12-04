package edu.wofford.wocoin.main;

import edu.wofford.wocoin.Database;
import edu.wofford.wocoin.Wallet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;


public class Feature08Main {
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
                    String password;
                    String id;
                    if (Pass.equals("adminpwd")){
                        password_Correct = true;
                        while (password_Correct) {
                            System.out.println("1: back\n2: add user\n3: remove user\n4: transfer WoCoins\n");
                            String next_step = input.nextLine();
                            if (next_step .equals("1")) {
                                password_Correct = false;
                            }else if (next_step.equals("2")) {
                                System.out.println("Enter ID");
                                id = input.nextLine();
                                System.out.println("Enter a password:");
                                password = input.nextLine();
                                Database d = new Database(args[0]);
                                d.addUser(id, password);
                            }else if (next_step.equals("3")){
                                System.out.println("Enter ID");
                                String response = input.nextLine();
                                Database d = new Database(args[0]);
                                d.deleteUser(response);
                            } else if (next_step.equals("4")) {
                                Database db = new Database(args[0]);
                                System.out.println("Enter users username");
                                String user = input.nextLine();
                                if (db.checkUser(user)) {
                                    if (db.checkWallet(user)) {
                                        //wallet does exist
                                        //TODO transfer WoCoins
                                        String key = "0x" + db.walletPublicKey(user);
                                        System.out.println("Enter an amount:");
                                        int amount = input.nextInt();
                                        input.nextLine();
                                        try {
                                            Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
                                            System.out.println("1");
                                            Credentials credentials = WalletUtils.loadCredentials("adminpwd", "ethereum\\node0\\keystore\\UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json");
                                            System.out.println("2");
                                            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                                                    web3, credentials, key,
                                                    BigDecimal.valueOf(amount), Convert.Unit.ETHER)
                                                    .send();
                                            System.out.println("Transfer complete.");
                                        } catch (IOException | CipherException | InterruptedException | TransactionException ex) {
                                            System.out.println(ex);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        System.out.println("User has no wallet.");
                                    }
                                }else {
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
                                    "3: add product\n4: remove product\n5: display products");
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
                                String menu = d.productOfUsers(user);
                                if (d.checkUser(user)) {
                                    if (d.checkWallet(user)){
                                        System.out.println(menu);
                                        int response = input.nextInt();
                                        if (response == 1) {
                                            System.out.println("Action canceled.");
                                        } else {
                                            d.removeProduct(user,response);
                                        }
                                    } else {
                                        System.out.println("User has no wallet.");
                                    }
                                } else {
                                    System.out.println("No such user.");
                                }
                            } else if (next_answer.equals("5")) {
                                String productString = d.turnProductToString(user);
                                System.out.println(productString);
                            }
                        }
                    }
                }

            }
        }
    }
}
