package edu.wofford.wocoin;

import java.io.File;
import java.io.IOException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class Wallet {

    public void createWallet() {
        File useridWallet = new File("keystore");
        String passwordWallet = "walletpwd";
        String walletPath = "UTC--2019-08-07T17-24-10.532680697Z--0fce4741f3f54fbffb97837b4ddaa8f769ba0f91.json";
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, useridWallet, false);
            System.out.println("File name ethereum wallet: " + walletFile);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void getCredentials(){

        Credentials credentials;

        File walletFile = new File ("keystore/your_wallet_file.json");

        String passwordWallet="walletpwd";

        try {
            credentials = WalletUtils.loadCredentials(passwordWallet, walletFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }
}
