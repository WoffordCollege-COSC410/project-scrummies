package edu.wofford.wocoin;

public class Geth {

    public String getBalance(String user) {
        EthGetBalance balanceWei = web3.ethGetBalance("0xF0f15Cedc719B5A55470877B0710d5c7816916b1", DefaultBlockParameterName.LATEST).send();
        System.out.println("balance in wei: " + balanceWei);

        BigDecimal balanceInEther = Convert.fromWei(balanceWei.getBalance().toString(), Unit.ETHER);
        System.out.println("balance in ether: " + balanceInEther);
    }

}
