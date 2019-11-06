package edu.wofford.wocoin;

import java.io.File;
import java.io.IOException;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

public class Wallet {

    public void createWallet() {
        File idStoreWallet = new File("idStore");
        String passwordWallet = "walletpwd";
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, idStoreWallet, false);
            System.out.println("File name ethereum wallet: " + walletFile);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
