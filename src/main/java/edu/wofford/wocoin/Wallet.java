package edu.wofford.wocoin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.FileReader;

//import jdk.nashorn.internal.parser.JSONParser;

public class Wallet {


    /**
     * This function creates a User's wallet for future transactions
     * @param dir the directory of the wallet
     * @param userID the given user id
     * @param userPassword the user's password
     * @return THe wallet's address after it has been created
     */
    public static String createWallet(String dir, String userID, String userPassword) {
        File newFile = new File(dir + File.separator + userID);
        newFile.mkdir();
        String passwordWallet = userPassword;
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, newFile, false);
            System.out.println("File name ethereum wallet: " + walletFile);

            FileReader x = new FileReader(dir + File.separator + userID + File.separator + walletFile);
            Object obj = new JSONParser().parse(x);
            JSONObject jo = (JSONObject) obj;
            String address = (String) jo.get("address");
            System.out.println(address);
            return address;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return "";
        }

}
