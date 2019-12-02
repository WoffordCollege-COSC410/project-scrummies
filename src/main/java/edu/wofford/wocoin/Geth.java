package edu.wofford.wocoin;

import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class Geth {

    public void getBalance(String user) {
        EthGetBalance balanceWei = web3.ethGetBalance("0xF0f15Cedc719B5A55470877B0710d5c7816916b1", DefaultBlockParameterName.LATEST).send();
        System.out.println("balance in wei: " + balanceWei);

        BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Convert.Unit.ETHER);
        System.out.println("balance in ether: " + balanceInEther);
    }

}
