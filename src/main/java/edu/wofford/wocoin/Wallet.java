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
            System.out.println("File name ethereum wallet: " + walletFile);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void getCredentials() {

        Credentials credentials;

        File walletFile = new File("C:\\Users\\sethl\\project-scrummies\\ethereum\\node0\\keystore\\UTC--2019-11-11T17-04-54.869000000Z--202d79c70190a105a7320c956f73ae3cbda2703c.json");

        String passwordWallet = "Seth";

        try {
            credentials = WalletUtils.loadCredentials(passwordWallet, walletFile);
            System.out.println(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }
}
