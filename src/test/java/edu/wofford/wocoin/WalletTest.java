package edu.wofford.wocoin;

import static org.junit.Assert.*;
import org.junit.Test;


public class WalletTest {
    @Test
    public void walletExists() {
    }

    @Test
    public void correctCredentials() {
        String dir = "C:\\Users\\sethl\\project-scrummies\\Test_Wallet";
        String key = Wallet.createWallet(dir,"Drew","HopeItWorks");
        Database d = new Database("testForWallet.db");
        d.addWallet("Drew",key);
        assertEquals(true,d.checkWallet("Drew"));
        assertEquals(false,d.checkWallet("Seth"));
    }
}
