package edu.wofford.wocoin;

import java.io.*;

//import jdk.nashorn.internal.parser.JSONParser;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.json.simple.JSONObject;
import java.io.FileReader;
import org.json.simple.parser.*;

public class Wallet {



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
