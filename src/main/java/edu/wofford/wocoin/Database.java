package edu.wofford.wocoin;

import java.io.File;
import java.sql.*;
import java.sql.PreparedStatement;
import java.io.IOException;

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
     *Creates a new database and sets it to name, as well as creates file
     * @param filename refers to name that database is set to
     */
    public Database(String filename) {
        Utilities.createNewDatabase(filename);
        file = new File(filename);
    }


    /**
     *This method adds a new user after the admin password has been properly entered.
     * It then takes the user id and salts and hashes it and stores the hashed password.
     * If a new user is added, the user is told the id was added, if the id already exists,
     * an "already exists" comment is thrown.
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
     *This method sees if the given user id already exists and returns the results to be used in "addUser"
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
     *This method removes an id if the admin properly enters the admin password and gives an id that
     * is being stored. If a user id that is given does exist, it is removed and the user is told that,
     * if the given id does not exist, a "does not exist" comment is thrown.
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
        }else {
            System.out.println(user + " does not exist.");
        }
    }

    /**
     * Checks a given user id to see if in database, then checks the given password to
     * see if it matches the existing use id and therefore exists or not
     * @param user Given user id
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
                }else {
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
        }else {
            System.out.println(user + " does not exist.");
        }
    }

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
     *
     * @param name
     * @param description
     * @param price
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


    public String turnProductToString(String id) {
        String url = "jdbc:sqlite:" + file;
        String product = "";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            ResultSet products = stmt.executeQuery("SELECT * FROM products;");
            ResultSetMetaData rsmd = products.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (products.next()) {
                for(int i = 1 ; i <= columnsNumber; i = i + 5) {
                    String index = products.getString(i%5);
                    String publicKey = products.getString(i%5 + 1);
                    int price = products.getInt(i%5 + 2);
                    String name = products.getString(i%5 + 3);
                    String description = products.getString(i%5 + 4);

                    if (i > 5) {
                        int temp = (i%5 + 2) - 5;
                        int secondPrice = products.getInt(temp);
                        if (price < secondPrice) {
                            if (publicKey.equals(walletPublicKey(id))) {
                                product = index + ":" + " >>>" +  " " + name  + ":" + " " + description + " " + "[" + price + " WoCoins]" + "\n" + product;
                            } else {
                                product = index + ":" + " " + name  + ":" + " " + description + " " + "[" + price + " WoCoins]" + "\n" + product;
                            }
                        }
                    } else {
                        product = product + index + ":" + " " + name  + ":" + " " + description + " " + "[" + price + " WoCoins]" + "\n";
                    }

                }
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }



    public boolean checkWallet(String username) {
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

    public String walletPublicKey (String user) {
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
}
