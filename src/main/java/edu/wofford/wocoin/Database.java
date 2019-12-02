package edu.wofford.wocoin;

import java.io.File;
import java.sql.*;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.text.Collator;
import java.util.*;
import java.util.Comparator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

/**
 * The Database program implements a process for Feature01 and Feature02, specifically it runs the
 * menu for administrators to add a user in Feature01 while Feature02 allows an administrator
 * to both add and remove a user
 *
 *The menu in Feature01 gives you two initial options: 1. exit 2. Admin, if Admin is chosen
 * then the correct password must be entered in order to add a new user
 * with a unique username id and password
 * The menu in Feature02 implements the same above menu, but has a third "remove user" option
 * when the admin password is properly entered. To remove a user, an existing user id name
 * must be entered
 *
 * @author Seth Ledford, Carson Vayhinger, Jack Dextraze, and Avery McMillen
 * @since 2019-10-09
 */


public class Database {

    private File file;

    /**
     * Creates a new database and sets it to name, as well as creates file
     *
     * @param filename refers to name that database is set to
     */
    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        file = new File(filename);
    }


    /**
     * This method adds a new user after the admin password has been properly entered.
     * It then takes the user id and salts and hashes it and stores the hashed password.
     * If a new user is added, the user is told the id was added, if the id already exists,
     * an "already exists" comment is thrown.
     *
     * @param id entered user id
     */
    public void addUser(String id, String password) {
        int salt = Utilities.generateSalt();
        String hash = Utilities.applySha256(password + salt);
        String url = "jdbc:sqlite:" + file;
        if (file.exists()) {
            if (!checkUser(id)) {
                try (Connection conn = DriverManager.getConnection(url)) {
                    Statement stmt = conn.createStatement();
                    String sqls = "INSERT INTO users (id, salt, hash) VALUES (?, ?, ?)";
                    PreparedStatement prepStmt = conn.prepareStatement(sqls);
                    prepStmt = conn.prepareStatement(sqls);
                    prepStmt.setString(1, id);
                    prepStmt.setInt(2, salt);
                    prepStmt.setString(3, hash);
                    prepStmt.executeUpdate();
                    System.out.println(id + " was added.");
                    prepStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(id + " already exists.");
            }
        } else {
            Utilities.createNewDatabase(url);
        }
    }

    /**
     * This method sees if the given user id already exists and returns the results to be used in "addUser"
     *
     * @param id1 entered user id
     * @return true if id already exists, otherwise returns false
     */
    public boolean checkUser(String id1) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + id1 + "';");
            rs.next();
            String result = rs.getString(1);
            if (result.equals(id1)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method removes an id if the admin properly enters the admin password and gives an id that
     * is being stored. If a user id that is given does exist, it is removed and the user is told that,
     * if the given id does not exist, a "does not exist" comment is thrown.
     *
     * @param user the referred to user id to be removed
     */
    public void deleteUser(String user) {
        String url = "jdbc:sqlite:" + file;
        if (checkUser(user)) {
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM users WHERE id = '" + user + "';");
                System.out.println(user + " was removed.");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(user + " does not exist.");
        }
    }

    /**
     * Checks a given user id to see if in database, then checks the given password to
     * see if it matches the existing use id and therefore exists or not
     *
     * @param user     Given user id
     * @param password Given corresponding password
     * @return if the USer id and password match and exists, return true,
     * else return "No such User" message
     */
    public boolean checkUserPassword(String user, String password) {
        String url = "jdbc:sqlite:" + file;
        if (checkUser(user)) {
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                ResultSet users_hash = stmt.executeQuery("SELECT hash FROM users WHERE id = '" + user + "';");
                users_hash.next();
                String users_hashString = users_hash.getString(1);

                ResultSet users_salt = stmt.executeQuery("SELECT salt FROM users WHERE id = '" + user + "';");
                users_salt.next();
                String users_saltSring = users_salt.getString(1);
                users_saltSring = password + users_saltSring;

                String salt_Input_Password = Utilities.applySha256(users_saltSring);

                if (users_hashString.equals(salt_Input_Password)) {
                    return true;
                } else {
                    System.out.println("Password is wrong.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No such user.");
        }
        return false;
    }

    /**
     * deleteWallet deletes a User's wallet after being prompted if they want their wallet deleted,
     * if the user doesn't exist the user is given the appropriate "does not exist" comment
     *
     * @param user the input id for the user who wants to delete their already existing wallet
     */

    public void deleteWallet(String user) {
        String url = "jdbc:sqlite:" + file;
        if (checkUser(user)) {
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM wallets WHERE id = '" + user + "';");
                System.out.println(user + " was removed.");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(user + " does not exist.");
        }
    }

    /**
     * addWallet creates a wallet and adds it to a user if one does not already exist for them
     *
     * @param id        the given user id
     * @param publickey the publickey is the set string coding the wallet
     */

    public void addWallet(String id, String publickey) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            String sqls = "INSERT INTO wallets (id, publickey) VALUES (?, ?);";
            PreparedStatement prepStmt = conn.prepareStatement(sqls);
            prepStmt = conn.prepareStatement(sqls);
            prepStmt.setString(1, id);
            prepStmt.setString(2, publickey);
            prepStmt.executeUpdate();
            System.out.println("Wallet added.");
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * addProduct is an option for an existing user, they have to give what the product is,
     * a description of the product, and the cost of the product, when the product is successfully added,
     * the user is prompted as such
     *
     * @param name        the product being entered
     * @param description a description of the product entered
     * @param price       the price of the product entered
     */
    public void addProduct(String seller, String name, String description, int price) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stm = conn.createStatement();
            ResultSet id = stm.executeQuery("SELECT publickey FROM wallets WHERE id = '" + seller + "';");
            id.next();
            String user = id.getString(1);

            String sqls = "INSERT INTO products (seller, price, name, description) VALUES (?, ?, ?, ?);";
            PreparedStatement prepStmt = conn.prepareStatement(sqls);
            prepStmt.setString(1, user);
            prepStmt.setInt(2, price);
            prepStmt.setString(3, name);
            prepStmt.setString(4, description);
            prepStmt.executeUpdate();
            System.out.println("Product added.");
            prepStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method creates a returnable string listing all the products, their decriptions, and prices,
     * ordered numerically by price, if the price is the same for multiple products, then those are
     * sorted alphabetically. If the user looking at the products added one of the products themselves,
     * the item is preempted by a ">>>"
     * @param id the user id accessing the product list
     * @return a string based display of the products
     */
    public String turnProductToString (String id){
        String url = "jdbc:sqlite:" + file;
        String product = "";
        int index = 1;

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet products = stmt.executeQuery(" SELECT * FROM products ORDER BY price, name COLLATE NOCASE;");
            ResultSetMetaData rsmd = products.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (products.next()) {
                for(int i = 1 ; i <= columnsNumber; i = i + 5) {
                    String price = products.getString(i % 5 + 2);
                    String publicKey = products.getString(i % 5 + 1);
                    String index1 = products.getString(i % 5);
                    String name1 = products.getString(i % 5 + 3);
                    String description1 = products.getString(i % 5 + 4);
                    String price1 = products.getString(i % 5 + 2);
                    if (publicKey.equals(walletPublicKey(id))) {
                        if (price1.equals("1")) {
                            product = product + index + ":" + " >>> " + " " + name1 + ":" + " " + description1 + "  " + "[" + price1 + " WoCoin]" + "\n";
                            index++;
                        } else {
                            product = product + index + ":" + " >>> " + " " + name1 + ":" + " " + description1 + "  " + "[" + price1 + " WoCoins]" + "\n";
                            index++;
                        }
                    }else {
                        if (price1.equals("1")) {
                            product = product + index + ":" + " " + name1 + ":" + " " + description1 + "  " + "[" + price1 + " WoCoin]" + "\n";
                            index++;
                        } else {
                            product = product + index + ":"+ " " + name1 + ":" + " " + description1 + "  " + "[" + price1 + " WoCoins]" + "\n";
                            index++;
                        }
                    }
                }
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return product;
    }

        /**
         * checkWallet sees if a user already has a wallet
         * @param username the user id being checked
         * @return false if the wallet does not already exist
         */
    public boolean checkWallet (String username){
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT id FROM wallets WHERE id = '" + username + "';");
            id.next();
            String user = id.getString(1);
            if (user.equals(username)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        /**
         * The public key is the id assigned to the wallet of any given user
         * @param user the id of whoever is accessing their wallet
         * @return ""
         */
    public String walletPublicKey (String user){
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT publickey FROM wallets WHERE id = '" + user + "';");
            id.next();
            String key = id.getString(1);
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String productOfUsers(String user) {
        String seller = walletPublicKey(user);
        String products = "1: cancel\n";
        int index = 2;
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT * FROM products WHERE seller = '" + seller + "' ORDER BY name COLLATE NOCASE;");
            ResultSetMetaData rsmd = id.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (id.next()) {
                for(int i = 1 ; i <= columnsNumber; i = i + 5) {
                    String name = id.getString(i % 5 + 3);
                    String description = id.getString(i % 5 + 4);
                    String price = id.getString(i % 5 + 2);
                    if (price.equals("1")) {
                        products = products + index + ":" + " " + name + ":" + " " + description + "  " + "[" + price + " WoCoin]" + "\n";
                        index++;
                    } else {
                        products = products + index + ":" + " " + name + ":" + " " + description + "  " + "[" + price + " WoCoins]" + "\n";
                        index++;
                    }
                }
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public String productsNotOfUser(String user) {
        String seller = walletPublicKey(user);
        String products = "1: cancel\n";
        int index = 2;
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT * FROM products WHERE seller != '" + seller + "' ORDER BY name COLLATE NOCASE;");
            ResultSetMetaData rsmd = id.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (id.next()) {
                for(int i = 1 ; i <= columnsNumber; i = i + 5) {
                    String name = id.getString(i % 5 + 3);
                    String description = id.getString(i % 5 + 4);
                    String price = id.getString(i % 5 + 2);
                    if (price.equals("1")) {
                        products = products + index + ":" + " " + name + ":" + " " + description + "  " + "[" + price + " WoCoin]" + "\n";
                        index++;
                    } else {
                        products = products + index + ":" + " " + name + ":" + " " + description + "  " + "[" + price + " WoCoins]" + "\n";
                        index++;
                    }
                }
            }
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void removeProduct(String user, int number) {
        int counter = countUsersProducts(user)  + 1;
        if (number <= counter) {
            String url = "jdbc:sqlite:" + file;
            String products = productOfUsers(user);
            String[] parseProducts = products.split("\\n");
            System.out.println(parseProducts[0]);
            String row = parseProducts[number - 1];
            String[] parseRow = row.split(": ");
            String name = parseRow[1];
            System.out.println(name);
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM products WHERE name = '" + name + "';");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Product removed.");
        } else {
            System.out.println("Invalid value. Enter a value between 1 and " + counter + ".");
        }
    }

    public boolean checkProduct(String user){
        String url = "jdbc:sqlite:" + file;
        String userPublickey = walletPublicKey(user);
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT publickey FROM wallets WHERE publickey = '" + userPublickey + "';");
            id.next();
            String realPublicKey = id.getString(1);
            if (realPublicKey.equals(userPublickey)) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countUsersProducts(String user) {
        int counter = 0;
        String url = "jdbc:sqlite:" + file;
        String userPublickey = walletPublicKey(user);
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT COUNT(*) FROM products WHERE seller = '" + userPublickey + "';");
            id.next();
            counter = id.getInt(1);
            return counter;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public int countProductsUserDoesNotOwn(String user) {
        int counter = 0;
        String url = "jdbc:sqlite:" + file;
        String userPublickey = walletPublicKey(user);
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT COUNT(*) FROM products WHERE seller != '" + userPublickey + "';");
            id.next();
            counter = id.getInt(1);
            return counter;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public String findProduct(String product) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT seller FROM products WHERE name = '" + product + "';");
            id.next();
            String seller = id.getString(1);
            return seller;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String findProductFromId(String productId) {
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT name FROM products WHERE id = '" + productId + "';");
            id.next();
            String product = id.getString(1);
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void sendMessage(String sender,int row, String message) {
        int counter = countProductsUserDoesNotOwn(sender);
        String senderId = walletPublicKey(sender);
        if (row <= counter) {
            String url = "jdbc:sqlite:" + file;
            String products = productsNotOfUser(sender);
            String[] parseProducts = products.split("\\n");
            System.out.println(parseProducts[0]);
            String rowOfMenu = parseProducts[row - 1];
            String[] parseRow = rowOfMenu.split(": ");
            String name = parseRow[1];
            String receiverProductkey = findProduct(name);
            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stm = conn.createStatement();
                ResultSet id = stm.executeQuery("SELECT id FROM products WHERE name = '" + name + "';");
                id.next();
                String productId = id.getString(1);

                String sqls = "INSERT INTO messages (sender, recipient, productid, message) VALUES (?, ?, ?, ?);";
                PreparedStatement prepStmt = conn.prepareStatement(sqls);
                prepStmt.setString(1, senderId);
                prepStmt.setString(2, receiverProductkey);
                prepStmt.setString(3, productId);
                prepStmt.setString(4, message);
                System.out.println("Message sent.");
                prepStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public String receiveMessage(String user) {
        String recipient = walletPublicKey(user);
        String messages = "1: cancel\n";
        int index = 2;
        String url = "jdbc:sqlite:" + file;
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet id = stmt.executeQuery("SELECT * FROM messages WHERE recipient != '" + recipient + "' ORDER BY DATETIME();");
            ResultSetMetaData rsmd = id.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (id.next()) {
                for(int i = 1 ; i <= columnsNumber; i = i + 6) {
                    String message = id.getString(i % 6 + 4);
                    String product = findProductFromId(id.getString(i % 3 ));

                    String time = id.getString(i % 6 + 5);
                    messages = messages + index + ":" + " " + message +  " " + "[" + product + "]" + " " + time + "\n";
                    index++;
                }
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }





}


