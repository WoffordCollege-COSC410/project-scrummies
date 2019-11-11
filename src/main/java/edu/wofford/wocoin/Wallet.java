package edu.wofford.wocoin;

import java.io.File;
import java.io.IOException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class Wallet {

    private File file;

    public Wallet(String walletName) {
        file = new File(walletName);
    }

    public void createWallet() {
        String home = System.getProperty("user.home");
        file = new File(home + File.separator + "project-scrummies" + File.separator + "ethereum" + File.separator + "node0" + File.separator + "keystore");
        String passwordWallet = "walletpwd";
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, file, false);
            file = new File(file + walletFile);
            System.out.println("File name ethereum wallet: " + walletFile);
            System.out.println("File: " + file);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void toAString() {
        System.out.println(file);
    }

    public void getCredentials() {

        Credentials credentials;

        String passwordWallet = "walletpwd";

        try {
            credentials = WalletUtils.loadCredentials(passwordWallet, file);
            System.out.println(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }
}
