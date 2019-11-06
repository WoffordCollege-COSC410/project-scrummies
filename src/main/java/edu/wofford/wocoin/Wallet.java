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

public class Wallet {

    public void Create() {
        File keystoreWallet = new File("idStore");
        String passwordWallet = "walletpwd";
        try {
            String walletFile = WalletUtils.generateNewWalletFile(passwordWallet, keystoreWallet, false);
            System.out.println("File name ethereum wallet: " + walletFile);
        } catch (Exception ex) {
            System.out.println(ex);
        }


    }
}
