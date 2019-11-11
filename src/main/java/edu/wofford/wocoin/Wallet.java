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
        String dir = System.getProperty("user.dir");
        File idStoreWallet = new File(dir +  File.separator + "ethereum" +File.separator + "node0" + File.separator + "keystore");
        String passwordWallet = "walletpwd";
        //String walletPath = "UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json";
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, idStoreWallet, false);
            System.out.println("File name ethereum wallet: " + walletFile);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }



    public void getCredentials() {

        Credentials credentials;

        String passwordWallet = "Seth";

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
